package com.example.postoffice.mapper;

import com.example.postoffice.dto.AbstractRequestDto;
import com.example.postoffice.dto.AbstractResponseDto;
import com.example.postoffice.entity.BaseEntity;
import org.mapstruct.MappingTarget;


public interface CommonMapper
        <E extends BaseEntity,
                Q extends AbstractRequestDto,
                S extends AbstractResponseDto> {
    E toEntity(Q request);
    E partialUpdate(Q request, @MappingTarget E entity);
    S toResponse(E entity);
}
