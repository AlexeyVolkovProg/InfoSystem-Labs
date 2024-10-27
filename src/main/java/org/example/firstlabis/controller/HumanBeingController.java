package org.example.firstlabis.controller;

import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.domain.request.HumanBeingCreateDTO;
import org.example.firstlabis.dto.domain.request.HumanBeingUpdateDTO;
import org.example.firstlabis.dto.domain.response.HumanBeingResponseDTO;
import org.example.firstlabis.service.HumanBeingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/human-being")
@RequiredArgsConstructor
public class HumanBeingController {
    private final HumanBeingService humanBeingService;

    @PostMapping("/create")
    public ResponseEntity<HumanBeingResponseDTO> create(@RequestBody HumanBeingCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(humanBeingService.createHumanBeing(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HumanBeingResponseDTO> update(@PathVariable Long id,
                                                   @RequestBody HumanBeingUpdateDTO request) {
        return ResponseEntity.ok(humanBeingService.updateHumanBeing(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HumanBeingResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(humanBeingService.findHumanBeingById(id));
    }

    @GetMapping
    public ResponseEntity<Page<HumanBeingResponseDTO>> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String soundtrackName,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(humanBeingService.getAllWithFilters(name, soundtrackName,pageable));
    }

    @GetMapping("/name-containing")
    public ResponseEntity<Page<HumanBeingResponseDTO>> findAllByNameContaining(
            @RequestParam String substring,
            @PageableDefault Pageable pageable
    ) {
        return ResponseEntity.ok(humanBeingService.getAllByNameContaining(substring, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        humanBeingService.deleteHumanBeing(id);
        return ResponseEntity.ok().build();
    }
}
