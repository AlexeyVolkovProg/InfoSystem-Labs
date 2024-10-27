package org.example.firstlabis.mapper;

public interface EntityMapper <D, E>{
    E toEntity(D dto);

    D toResponseDto(E entity);
}
