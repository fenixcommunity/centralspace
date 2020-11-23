package com.fenixcommunity.centralspace.app.rest.mapper;

import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.List;

public interface Mappable<SOURCE, DTO> {
    DTO mapToDto(SOURCE source);

    default List<DTO> mapToDtoList(List<SOURCE> sources) {
        return sources.stream().map(this::mapToDto).collect(toUnmodifiableList());
    }

    SOURCE mapFromDto(DTO dto);

    default List<SOURCE> mapFromDtoList(List<DTO> dtos) {
        return dtos.stream().map(this::mapFromDto).collect(toUnmodifiableList());
    }
}
