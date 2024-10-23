package org.example.firstlabis.initializer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.firstlabis.config.properties.AdminProperties;
import org.example.firstlabis.model.security.Role;
import org.example.firstlabis.model.security.User;
import org.example.firstlabis.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Компонент, отвечающий за добавление первого администратора в начале работы приложения
 */
@Component
@RequiredArgsConstructor
public class AdminInitializer {
    private final UserRepository userRepository;
    private final AdminProperties adminProperties;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void initializeFirstAdmin(){
        if (userRepository.findByUsername(adminProperties.getUsername()).isEmpty()){
            User adminUser = new User();
            adminUser.setUsername(adminProperties.getUsername());
            adminUser.setPassword(passwordEncoder.encode(adminProperties.getPassword()));
            adminUser.setRole(Role.ADMIN);
            adminUser.setEnabled(true);
            userRepository.save(adminUser);
        }
    }
}
