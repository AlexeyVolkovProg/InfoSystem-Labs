package org.example.firstlabis.service.history;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.domain.request.HumanBeingCreateDTO;
import org.example.firstlabis.dto.history.ImportLogDto;
import org.example.firstlabis.exceprion.UniqueConstraintException;
import org.example.firstlabis.mapper.domain.HumanBeingMapper;
import org.example.firstlabis.model.domain.HumanBeing;
import org.example.firstlabis.model.history.HumansImportLog;
import org.example.firstlabis.model.security.User;
import org.example.firstlabis.repository.HumanBeingRepository;
import org.example.firstlabis.service.storage.YandexCloudStorageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HumanBeingFileService {

    private final HumanBeingMapper humanBeingMapper;
    private final HumanBeingRepository humanBeingRepository;
    private final HumanImportHistoryService humanImportHistoryService;
    private final YandexCloudStorageService yandexCloudStorageService;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void importFile(MultipartFile file) {
        importHumansHistory(parseJsonFile(file), file);
    }

    @Transactional
    public ByteArrayResource getImportFileByImportId(Long importId) {
        return yandexCloudStorageService.getFileByImportId(importId);
    }

    /**
     * Метод считывания пришедшего Json файла
     */
    private List<HumanBeingCreateDTO> parseJsonFile(MultipartFile multipartFile) {
        try {
            return new ObjectMapper().readValue(multipartFile.getBytes(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid file format", e);
        } catch (IOException e) {
            throw new RuntimeException("Error parse file", e);
        }
    }

    /**
     * Создаем запись в бд , с статистикой import, импортирую все объекты
     */
    private void importHumansHistory(List<HumanBeingCreateDTO> humans, MultipartFile file) {
        HumansImportLog importLog = humanImportHistoryService.createStartedVersionImportLog();
        importHumans(humans);
        importLog.setSuccess(true);
        importLog.setObjectAdded(humans.size());
        yandexCloudStorageService.saveImportFile(file, importLog); // todo не забудь придумать придумать откат , в случае падения бд
        humanImportHistoryService.saveFinishedVersionImportLog(importLog);
    }

    /**
     * Метод импорта сущностей в бд
     */
    private void importHumans(List<HumanBeingCreateDTO> humanBeingCreateDTOS) {
        List<HumanBeing> humans = humanBeingCreateDTOS
                .stream()
                .map(humanBeingMapper::toEntity)
                .toList();
        validateUniqueHumanNameConstraint(humans);
        humanBeingRepository.saveAll(humans);
    }

    /**
     * Метод валидации сущностей на соблюдение ограничений
     */
    private void validateUniqueHumanNameConstraint(List<HumanBeing> humans) {
        Set<String> names = new HashSet<>();
        humans.forEach(human -> {
                    if (!names.add(human.getName())) {
                        throw new UniqueConstraintException(HumanBeing.class, "name", human.getName());
                    }
                }
        );
        humanBeingRepository.findFirstByNameIn(names).ifPresent((human) -> {
            throw new UniqueConstraintException(HumanBeing.class, "name", human.getName());
        });
    }


    public Page<ImportLogDto> findAllImports(Pageable pageable){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));

        Page<ImportLogDto> result;
        if (isAdmin) {
            result = humanImportHistoryService.findAll(pageable);
        } else {
            User user = (User) authentication.getPrincipal();
            result = humanImportHistoryService.findAllByUser(user, pageable);
        }
        return result;
    }
}
