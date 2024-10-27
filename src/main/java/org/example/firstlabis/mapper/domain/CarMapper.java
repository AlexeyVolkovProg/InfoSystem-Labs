package org.example.firstlabis.mapper.domain;

import org.example.firstlabis.dto.domain.request.CarCreateDTO;
import org.example.firstlabis.dto.domain.request.CarUpdateDTO;
import org.example.firstlabis.dto.domain.response.CarResponseDTO;
import org.example.firstlabis.mapper.EntityMapper;
import org.example.firstlabis.model.domain.Car;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CarMapper extends EntityMapper<CarResponseDTO, Car> {

    Car toEntity(CarCreateDTO request);

    Car toEntity(CarUpdateDTO request);

    //todo удостовериться правильно ли работает маппинг
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CarUpdateDTO dto, @MappingTarget Car entity);

}
