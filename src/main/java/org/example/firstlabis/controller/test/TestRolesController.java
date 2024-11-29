package org.example.firstlabis.controller.test;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Тестовый контроллер, был написан для более удобного локального тестирования
 */
@RestController
@EnableMethodSecurity
@RequestMapping("/api/test")
public class TestRolesController {

    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<String> userAccess() {
        return ResponseEntity.ok("Привет, пользователь! Доступ разрешен.");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> adminAccess() {
        return ResponseEntity.ok("Привет, админ! Доступ разрешен.");
    }

    @GetMapping("/all")
    public ResponseEntity<String> publicAccess() {
        return ResponseEntity.ok("Общий доступ для всех пользователей");
    }

}
