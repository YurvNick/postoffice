package com.example.postoffice.service;

import com.example.postoffice.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommonService<E extends BaseEntity> {

    Page<E> findAll(Pageable pageable);

    E findEntity(Long id);

    E create(E entity);

    E update(E entity, Long id);

    boolean delete(Long id);

}
