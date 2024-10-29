package org.example.firstlabis.controller.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.authentication.request.UserDto;
import org.example.firstlabis.service.security.jwt.AuthenticationService;
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

    @Operation(summary = "Approve admin registration request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully approved registration request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/registration-requests/{userId}")
    public ResponseEntity<Void> approveAdminRegistrationRequest(
            @Parameter(description = "ID of the user to approve") @PathVariable Long userId){
        authenticationService.approveAdminRegistrationRequest(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reject admin registration request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully rejected registration request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/registration-requests/{userId}")
    public ResponseEntity<Void> rejectAdminRegistrationRequest(
            @Parameter(description = "ID of the user to reject") @PathVariable Long userId){
        authenticationService.rejectAdminRegistrationRequest(userId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get pending registration requests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved pending requests"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/registration-requests")
    public ResponseEntity<Page<UserDto>> getPendingRegistrationRequest(
            @Parameter(description = "Pagination information") @PageableDefault Pageable pageable){
        return ResponseEntity.ok(authenticationService.getPendingRegistrationRequest(pageable));
    };
}
