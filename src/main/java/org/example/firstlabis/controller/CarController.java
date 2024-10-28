package org.example.firstlabis.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.domain.request.CarCreateDTO;
import org.example.firstlabis.dto.domain.request.CarUpdateDTO;
import org.example.firstlabis.dto.domain.response.CarResponseDTO;
import org.example.firstlabis.service.CarService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @Operation(summary = "Create a new car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created car"),
            @ApiResponse(responseCode = "400", description = "Invalid car data")
    })
    @PostMapping("/create")
    public ResponseEntity<CarResponseDTO> create(
            @Parameter(description = "Car creation data") @RequestBody CarCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.create(request));
    }

    @Operation(summary = "Update an existing car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated car"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDTO> update(
            @Parameter(description = "ID of the car to update") @PathVariable Long id,
            @Parameter(description = "Car update data") @RequestBody CarUpdateDTO request) {
        return ResponseEntity.ok(carService.updateCar(id, request));
    }


    @Operation(summary = "Find a car by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved car"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDTO> findById(
            @Parameter(description = "ID of the car to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(carService.findCarById(id));
    }

    @Operation(summary = "Find all cars with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of cars")
    })
    @GetMapping
    public ResponseEntity<Page<CarResponseDTO>> findAll(
            @Parameter(description = "Name filter for cars") @RequestParam(required = false) String name,
            @Parameter(description = "Pagination information") @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(carService.getAllWithFilters(name, pageable));
    }

    @Operation(summary = "Find all cars by name containing a substring")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of cars"),
            @ApiResponse(responseCode = "400", description = "Invalid substring")
    })
    @GetMapping("/name-containing")
    public ResponseEntity<Page<CarResponseDTO>> findAllByNameContaining(
            @Parameter(description = "Substring to search for in car names") @RequestParam String substring,
            @Parameter(description = "Pagination information") @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(carService.getAllByNameContaining(substring, pageable));
    }

    @Operation(summary = "Delete a car by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted car"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID of the car to delete") @PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }
}
