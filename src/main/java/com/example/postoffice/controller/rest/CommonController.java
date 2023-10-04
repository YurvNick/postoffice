package com.example.postoffice.controller.rest;

import com.example.postoffice.dto.AbstractRequestDto;
import com.example.postoffice.dto.AbstractResponseDto;
import com.example.postoffice.entity.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

public interface CommonController<E extends BaseEntity,
        Q extends AbstractRequestDto,
        S extends AbstractResponseDto> {

    @GetMapping("/all")
    Page<S> getAll(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                   @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                   @RequestParam(value = "sort", defaultValue = "ASC") String sort);

    @GetMapping("/get")
    S getEntity(@RequestParam("id") Long id);

    @PostMapping("/create")
    S createEntity(@RequestBody Q requestEntity);

    @PutMapping("/update")
    S updateEntity(@RequestBody Q requestEntity, @RequestParam("id") Long id);

    @DeleteMapping("/delete")
    boolean deleteEntity(@RequestParam("id") Long id);
}
