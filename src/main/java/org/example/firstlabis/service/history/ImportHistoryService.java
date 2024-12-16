package org.example.firstlabis.service.history;

import lombok.RequiredArgsConstructor;
import org.example.firstlabis.dto.history.ImportLogDto;
import org.example.firstlabis.mapper.history.ImportLogMapper;
import org.example.firstlabis.model.history.ImportLog;
import org.example.firstlabis.model.security.User;
import org.example.firstlabis.repository.history.ImportLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@RequiredArgsConstructor
public abstract class ImportHistoryService<T extends ImportLog> {

    private final ImportLogRepository<T> importLogRepository;
    private final ImportLogMapper importLogMapper;
    private final Supplier<T> importLogConstructor;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public T createStartedVersionImportLog(){
        return importLogRepository.save(importLogConstructor.get());
    }

    @Transactional
    public void saveFinishedVersionImportLog(T importLog){
        importLogRepository.save(importLog);
    }

    @Transactional
    public Page<ImportLogDto> findAllByUser(User user, Pageable pageable){
        return importLogRepository.findAllByUser(user, pageable).map(importLogMapper::toDto);
    }

    @Transactional
    public Page<ImportLogDto> findAll(Pageable pageable){
        return importLogRepository.findAll(pageable).map(importLogMapper::toDto);
    }

}
