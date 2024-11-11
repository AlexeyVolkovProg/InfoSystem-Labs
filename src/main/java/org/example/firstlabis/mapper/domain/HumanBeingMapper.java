package org.example.firstlabis.mapper.domain;

import org.example.firstlabis.dto.domain.request.HumanBeingCreateDTO;
import org.example.firstlabis.dto.domain.request.HumanBeingUpdateDTO;
import org.example.firstlabis.dto.domain.response.HumanBeingResponseDTO;
import org.example.firstlabis.mapper.EntityMapper;
import org.example.firstlabis.model.domain.HumanBeing;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CoordinatesMapper.class, CarMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface HumanBeingMapper extends EntityMapper<HumanBeingResponseDTO, HumanBeing> {
    HumanBeing toEntity(HumanBeingCreateDTO request);
    HumanBeing toEntity(HumanBeingUpdateDTO request);

    HumanBeingCreateDTO toCreateDTO(HumanBeing request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(HumanBeingUpdateDTO dto, @MappingTarget HumanBeing entity);
}
