package org.example.firstlabis.service.storage;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.firstlabis.model.history.HumansImportLog;
import org.example.firstlabis.model.history.ImportLog;
import org.example.firstlabis.repository.history.HumanImportLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class YandexCloudStorageService {

    private static final String DIRECTORY_NAME = "humans-log-folder";

    @Value("${application.yandex.bucket.name}")
    private String bucketName;

    private final HumanImportLogRepository humanImportLogRepository;

    private final S3Client s3Client;

    public void save(String filename, MultipartFile file) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(filename)
                    .build();
            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("Exception reading file input file", e);
        }
    }

    public ByteArrayResource get(String filename) {
        try {
            ResponseInputStream<GetObjectResponse> response = s3Client.getObject(
                    request -> request.bucket(bucketName).key(filename));
            return new ByteArrayResource(response.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Exception reading file", e);
        }
    }

    /**
     * Метод сохранения файла в Object Yandex Storage
     * @param file
     * @param importLog
     */
    public void saveImportFile(MultipartFile file, HumansImportLog importLog) {
        String filename = filenameByImportId(importLog.getId());
        this.save(filename, file);
    }


    /**
     * Достаем файл из Object Yandex Storage
     * @param importId
     * @return
     */
    public ByteArrayResource getFileByImportId(Long importId) {
        ImportLog importLog = humanImportLogRepository.findById(importId)
                .orElseThrow(() -> new EntityNotFoundException("Log file with id " + importId + " not found"));
        if (!importLog.isSuccess()) {
            throw new IllegalArgumentException("Unsuccessful import");
        }
        String filename = filenameByImportId(importId);
        return this.get(filename);
    }

    private String filenameByImportId(Long importId) {
        return String.format("%s/%d", DIRECTORY_NAME, importId);
    }
}
