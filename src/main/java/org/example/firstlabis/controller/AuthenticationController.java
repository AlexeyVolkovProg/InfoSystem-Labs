package org.example.firstlabis.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.authentication.AuthResponseDTO;
import org.example.firstlabis.dto.authentication.LoginRequestDTO;
import org.example.firstlabis.dto.authentication.RegisterRequestDTO;
import org.example.firstlabis.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


}
