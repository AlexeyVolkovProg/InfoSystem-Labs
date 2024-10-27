package org.example.firstlabis.controller.authentication;

import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.authentication.request.UserDto;
import org.example.firstlabis.service.AuthenticationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AuthenticationAdminController {

    private final AuthenticationService authenticationService;

    @PostMapping("/registration-requests/{userId}")
    public ResponseEntity<Void> approveAdminRegistrationRequest(@PathVariable Long userId){
        authenticationService.approveAdminRegistrationRequest(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/registration-requests/{userId}")
    public ResponseEntity<Void> rejectAdminRegistrationRequest(@PathVariable Long userId){
        authenticationService.rejectAdminRegistrationRequest(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/registration-requests")
    public ResponseEntity<Page<UserDto>> getPendingRegistrationRequest(@PageableDefault Pageable pageable){
        return ResponseEntity.ok(authenticationService.getPendingRegistrationRequest(pageable));
    };
}
