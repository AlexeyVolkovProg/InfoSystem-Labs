package org.example.firstlabis.service.security;

import org.example.firstlabis.model.security.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


/**
 * Сервис, проверяющий активацию пользователя
 * Необходим для проверок, активирован админ в системе или нет
 */
@Service
public class UserSecurityService {
    public boolean isEnabled() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        User user = (User) authentication.getPrincipal();
        return user.isEnabledStatus(); // Проверяем, активирован ли пользователь
    }
}
