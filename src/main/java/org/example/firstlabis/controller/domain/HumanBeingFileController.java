package org.example.firstlabis.controller.domain;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.history.ImportLogDto;
import org.example.firstlabis.service.history.HumanBeingFileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file/")
@RequiredArgsConstructor
public class HumanBeingFileController {

    private final HumanBeingFileService humanBeingFileService;


    @Operation(summary = "Import file with humans")
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> importFileWithHumans(
            @RequestPart("fileHumans") @Parameter(description = "File to be imported", required = true) MultipartFile file) {
        humanBeingFileService.importFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Retrieve all import logs")
    @GetMapping(value = "/import")
    public ResponseEntity<Page<ImportLogDto>> findAllImports(
            @PageableDefault @Parameter(description = "Pagination parameters") Pageable pageable) {
        return ResponseEntity.ok(humanBeingFileService.findAllImports(pageable));
    }
}