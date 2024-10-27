package org.example.firstlabis.controller;


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

    @PostMapping("/create")
    public ResponseEntity<CarResponseDTO> create(@RequestBody CarCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDTO> update(@PathVariable Long id,
                                                 @RequestBody CarUpdateDTO request) {
        return ResponseEntity.ok(carService.updateCar(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.findCarById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CarResponseDTO>> findAll(
            @RequestParam(required = false) String name,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(carService.getAllWithFilters(name, pageable));
    }

    @GetMapping("/name-containing")
    public ResponseEntity<Page<CarResponseDTO>> findAllByNameContaining(
            @RequestParam String substring,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(carService.getAllByNameContaining(substring, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }
}
