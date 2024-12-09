package org.example.firstlabis.mapper.history;

import org.example.firstlabis.dto.history.ImportLogDto;
import org.example.firstlabis.mapper.security.UserMapper;
import org.example.firstlabis.model.history.ImportLog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ImportLogMapper {
    ImportLogDto toDto(ImportLog entity);
}
