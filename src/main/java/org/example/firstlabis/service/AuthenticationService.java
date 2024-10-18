package org.example.firstlabis.service;

import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.authentication.AuthResponseDTO;
import org.example.firstlabis.dto.authentication.LoginRequestDTO;
import org.example.firstlabis.dto.authentication.RegisterRequestDTO;
import org.example.firstlabis.model.security.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDTO authenticate(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        User user = userService.getUserByUsername(request.username());
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDTO(jwtToken);
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        checkUserExistByUsername(request.username()); // проверка не существовал ли user раньше
        User user = mapToUser(request);
        user = userService.createUser(user);
        String jwtToken = jwtService.generateToken(Map.of("role", request.role()), user);
        return new AuthResponseDTO(jwtToken);
    }

    private User mapToUser(RegisterRequestDTO request) {
        return User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();
    }

    /**
     * Проверка на существование пользователя
     */
    private void checkUserExistByUsername(String username) {
        if (userService.isUserExist(username)) {
            if (userService.isUserExist(username)) {
                throw new IllegalStateException("User " + username + " is taken");
            }
        }
    }


}
