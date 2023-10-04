package com.example.postoffice.controller.rest;

import com.example.postoffice.dto.AbstractRequestDto;
import com.example.postoffice.dto.AbstractResponseDto;
import com.example.postoffice.entity.BaseEntity;
import com.example.postoffice.mapper.CommonMapper;
import com.example.postoffice.service.CommonService;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseController<E extends BaseEntity,
        Q extends AbstractRequestDto,
        S extends AbstractResponseDto,
        V extends CommonService<E>,
        M extends CommonMapper<E, Q, S>>
        implements CommonController<E, Q, S> {

    protected final V service;
    protected final M mapper;

    protected BaseController(V service, M mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    public Page<S> getAll(Integer offset, Integer limit, String sort) {
        Sort.Direction direction = Sort.Direction.valueOf(sort);
        Sort.Order order = Sort.Order.by("id").with(direction);
        Pageable pageable = PageRequest.of(offset, limit, Sort.by(order));

        List<S> list = service.findAll(pageable).stream()
                .map(mapper::toResponse).collect(Collectors.toList());
        return new PageImpl<>(list);
    }

    @Override
    public S getEntity(Long id) {
        return mapper.toResponse(service.findEntity(id));
    }

    @Override
    public S createEntity(Q requestEntity) {
        E entity = mapper.toEntity(requestEntity);
        return mapper.toResponse(service.create(entity));
    }

    @Override
    public S updateEntity(Q requestEntity, Long id) {
        E entity = mapper.toEntity(requestEntity);
        return mapper.toResponse(service.update(entity, id));
    }

    @Override
    public boolean deleteEntity(Long id) {
        return service.delete(id);
    }
}
