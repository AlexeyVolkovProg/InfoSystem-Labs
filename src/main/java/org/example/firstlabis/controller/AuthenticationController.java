package org.example.firstlabis.controller;

import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.authentication.response.JwtResponseDTO;
import org.example.firstlabis.dto.authentication.request.LoginRequestDTO;
import org.example.firstlabis.dto.authentication.request.RegisterRequestDTO;
import org.example.firstlabis.service.AuthenticationService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<JwtResponseDTO> register(
            @RequestBody RegisterRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(
            @RequestBody LoginRequestDTO request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    /**
     * Принимаем запрос на регистрацию нового администратора
     */
    @PostMapping("/register-admin")
    public ResponseEntity<JwtResponseDTO> registerAdmin(@RequestBody RegisterRequestDTO request){
        if (authenticationService.hasRegisteredAdmin()){ // проверка на наличие админа в системе, который может обработать
            authenticationService.submitAdminRegistrationRequest(request);
            return ResponseEntity.accepted().build();
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


}
