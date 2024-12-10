package org.example.firstlabis.service.security.jwt;

import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.authentication.request.UserDto;
import org.example.firstlabis.dto.authentication.response.JwtResponseDTO;
import org.example.firstlabis.dto.authentication.request.LoginRequestDTO;
import org.example.firstlabis.dto.authentication.request.RegisterRequestDTO;
import org.example.firstlabis.model.security.Role;
import org.example.firstlabis.model.security.User;
import org.example.firstlabis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${application.security.unique-password-constraint}")
    private boolean uniquePasswordConstraint; // настройка уникальности паролей

    public JwtResponseDTO authenticate(LoginRequestDTO request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username " + request.username()));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password(),
                        user.getAuthorities()
                )
        );
        return generateJwt(user);
    }

    public JwtResponseDTO registerUser(RegisterRequestDTO request) {
        boolean enabled = true;
        return registerEnabled(request, Role.USER, enabled);
    }

    /**
     * Метод регистрации и активации пользователя(для role:admin в первых раз без активации, пока ее не подтвердят)
     */
    private JwtResponseDTO registerEnabled(RegisterRequestDTO request, Role role, Boolean enabled) {
        User user = createUser(request, role, enabled);
        return generateJwt(user);
    }

    /**
     * Принять запрос на регистрацию пользователя в системе
     */
    public JwtResponseDTO submitAdminRegistrationRequest(RegisterRequestDTO request) {
        checkFirstAdminRegistration();
        boolean enabled = false; // пока что пользователь не активирован
        return registerEnabled(request, Role.ADMIN, enabled);
    }

    /**
     * Метод активации пользователя, который отправлял запроса на статус админа
     */
    public void approveAdminRegistrationRequest(Long userId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        user.setEnabledStatus(true); // активируем пользователя
        userRepository.save(user);
    }

    /**
     * Метод отказа пользователю, который запросил админ права
     */
    public void rejectAdminRegistrationRequest(Long userId) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + userId));
        validateUserNotEnabled(user); // проверяем не был ли ранее активирован этот пользователь
        userRepository.delete(user);
    }

    /**
     * Метод создания юзера с определенной ролью и активацией в системе
     *
     * @param role    роль юзера
     * @param enabled статус активации в системе
     */
    private User createUser(RegisterRequestDTO request, Role role, boolean enabled) {
        validateRegisterRequest(request);
        User user = mapToUser(request, role, enabled);
        userRepository.save(user);
        return user;
    }

    /**
     * Метод валидации запроса на регистрацию
     */
    private void validateRegisterRequest(RegisterRequestDTO request) {
        validateUsername(request.username());
        validatePassword(request.password());
    }

    /**
     * Метод валидации имени пользователя
     */
    private void validateUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new AuthenticationServiceException("Username " + username + " is taken");
        }
    }

    /**
     * Метод валидации пароля пользователя
     */
    private void validatePassword(String password) {
        if (uniquePasswordConstraint) { // если настройка уникальности паролей включена
            String encodedPassword = passwordEncoder.encode(password);
            userRepository.findUserByPassword(encodedPassword).ifPresent(
                    (user) -> {
                        throw new AuthenticationServiceException("Password " + password
                                + " is already taken by someone");
                    });

        }
    }

    /**
     * Метод проверяет активирован ли данный пользователь
     * Проверка необходима на этапе авторизации
     */
    private void validateUserEnabled(User user) {
        if (!user.isEnabledStatus()) {
            throw new AuthenticationServiceException("User is xyz: " + user.getUsername());
        }
    }

    /**
     * Метод проверяет не активирован ли пользователь
     * Если активирован, то выкинет исключение
     * Необходим для проверок при попытках удалить действующих администраторов
     */
    private void validateUserNotEnabled(User user) {
        if (user.isEnabledStatus()) {
            throw new AuthenticationServiceException("Cannot delete an enabled user");
        }
    }

    /**
     * Проверка на наличие админа в системе
     */
    private void checkFirstAdminRegistration() {
        if (!hasRegisteredAdmin()) {
            throw new AuthenticationServiceException("В системе на данный момент нету не одного админа");
        }
    }

    /**
     * Метод, проверяющий есть в бд хотя бы один админ
     */
    public boolean hasRegisteredAdmin() {
        return userRepository.existsByRole(Role.ADMIN);
    }

    /**
     * Метод генерации Jwt токена
     */
    private JwtResponseDTO generateJwt(User user) {
        String jwt = jwtService.generateToken(user);
        return new JwtResponseDTO(jwt, user.getAuthorities().stream().findFirst()
                .map(GrantedAuthority::getAuthority).get());
    }

    private User mapToUser(RegisterRequestDTO request, Role role, Boolean enabled) {
        return User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(role)
                .enabledStatus(enabled) //todo проверить правда ли user активируется
                .build();
    }

    private UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    public Page<UserDto> getPendingRegistrationRequest(Pageable pageable) {
        return userRepository.findAllByEnabledStatusFalse(pageable).map(this::mapToUserDto);
    }

}