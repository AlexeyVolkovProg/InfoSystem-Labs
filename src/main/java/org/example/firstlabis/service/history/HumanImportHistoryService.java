package org.example.firstlabis.service.history;

import org.example.firstlabis.mapper.history.ImportLogMapper;
import org.example.firstlabis.model.history.HumansImportLog;
import org.example.firstlabis.repository.history.HumanImportLogRepository;
import org.springframework.stereotype.Service;

@Service
public class HumanImportHistoryService extends ImportHistoryService<HumansImportLog> {
    public HumanImportHistoryService(HumanImportLogRepository importLogRepository,
                                     ImportLogMapper importLogMapper) {
        super(importLogRepository, importLogMapper, HumansImportLog::new);
    }
}
