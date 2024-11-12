package org.example.firstlabis.controller.domain;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.domain.request.AssignCarToHumanDTO;
import org.example.firstlabis.dto.domain.request.HumanBeingCreateDTO;
import org.example.firstlabis.dto.domain.request.HumanBeingUpdateDTO;
import org.example.firstlabis.dto.domain.response.HumanBeingResponseDTO;
import org.example.firstlabis.service.domain.HumanBeingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/human-being")
@RequiredArgsConstructor
public class HumanBeingController {
    private final HumanBeingService humanBeingService;

    @Operation(summary = "Create a new human being")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created human being"),
            @ApiResponse(responseCode = "400", description = "Invalid human being data")
    })
    @PostMapping("/create")
    public ResponseEntity<HumanBeingResponseDTO> create(
            @Parameter(description = "Human being creation data") @RequestBody HumanBeingCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(humanBeingService.createHumanBeing(request));
    }

    @Operation(summary = "Update an existing human being")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated human being"),
            @ApiResponse(responseCode = "404", description = "Human being not found")
    })
    @PreAuthorize("@humanBeingSecurityService.hasEditRights(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<HumanBeingResponseDTO> update(
            @Parameter(description = "ID of the human being to update") @PathVariable Long id,
            @Parameter(description = "Human being update data") @RequestBody HumanBeingUpdateDTO request) {
        return ResponseEntity.ok(humanBeingService.updateHumanBeing(id, request));
    }

    @Operation(summary = "Find a human being by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved human being"),
            @ApiResponse(responseCode = "404", description = "Human being not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<HumanBeingResponseDTO> findById(
            @Parameter(description = "ID of the human being to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(humanBeingService.findHumanBeingById(id));
    }

    @Operation(summary = "Find all human beings with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of human beings")
    })
    @GetMapping
    public ResponseEntity<Page<HumanBeingResponseDTO>> findAll(
            @Parameter(description = "Name string filter for human beings") @RequestParam(required = false) String name,
            @Parameter(description = "Soundtrack name string filter for human beings") @RequestParam(required = false) String soundtrackName,
            @Parameter(description = "Pagination information") @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(humanBeingService.getAllWithFilters(name, soundtrackName, pageable));
    }

    @Operation(summary = "Find all human beings by name containing a substring")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of human beings"),
            @ApiResponse(responseCode = "400", description = "Invalid substring")
    })
    @GetMapping("/name-containing")
    public ResponseEntity<Page<HumanBeingResponseDTO>> findAllByNameContaining(
            @Parameter(description = "Substring to search for in human being names") @RequestParam String substring,
            @Parameter(description = "Pagination information") @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(humanBeingService.getAllByNameContaining(substring, pageable));
    }

    @Operation(summary = "Delete a human being by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted human being"),
            @ApiResponse(responseCode = "404", description = "Human being not found")
    })
    @PreAuthorize("@humanBeingSecurityService.isOwner(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the human being to delete") @PathVariable Long id) {
        humanBeingService.deleteHumanBeing(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Attach a car to a human being")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully assigned car to human being"),
            @ApiResponse(responseCode = "404", description = "Human being or car not found")
    })
    @PutMapping("/attach-car")
    public ResponseEntity<String> attachTheCarToHuman(
            @Parameter(description = "Request to assign a car to a human being") @RequestBody AssignCarToHumanDTO request) {
        humanBeingService.attachTheCar(request.humanId(), request.carId());
        return ResponseEntity.ok("Car assigned to Human successfully.");
    }

    @Operation(summary = "Enable Admin Edit Status",
            description = "Grants the ability to edit by an administrator for the specified car ID.")
    @PreAuthorize("@humanBeingSecurityService.isOwner(#id)")
    @PutMapping("/{id}/edit-status/allow")
    public ResponseEntity<Void> enableAdminEditStatus(
            @PathVariable Long id) {
        humanBeingService.enableAdminEdit(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Disable Admin Edit Status",
            description = "Revokes the ability to edit by an administrator for the specified car ID.")
    @PreAuthorize("@humanBeingSecurityService.isOwner(#id)")
    @PutMapping("/{id}/edit-status/delete")
    public ResponseEntity<Void> deleteAdminEditStatus(@PathVariable Long id) {
        humanBeingService.turnOffAdminEdit(id);
        return ResponseEntity.ok().build();
    }
}
