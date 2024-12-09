package org.example.firstlabis.controller.domain;


import lombok.RequiredArgsConstructor;
import org.example.firstlabis.service.history.HumanBeingFileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file/human-being")
@RequiredArgsConstructor
public class HumanBeingFileController {

    private final HumanBeingFileService humanBeingFileService;

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> importFileWithHumans(@RequestPart("file_humans") MultipartFile file) {
        humanBeingFileService.importFile(file);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
