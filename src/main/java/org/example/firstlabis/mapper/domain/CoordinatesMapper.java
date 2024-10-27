package org.example.firstlabis.mapper.domain;

import org.example.firstlabis.dto.domain.response.CoordinatesResponseDTO;
import org.example.firstlabis.mapper.EntityMapper;
import org.example.firstlabis.model.domain.Coordinates;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CoordinatesMapper extends EntityMapper<CoordinatesResponseDTO, Coordinates> {
}
