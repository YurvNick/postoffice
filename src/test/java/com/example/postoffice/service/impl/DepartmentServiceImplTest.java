package com.example.postoffice.service.impl;

import com.example.postoffice.entity.Department;
import com.example.postoffice.repository.DepartmentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {
    @Mock
    private DepartmentRepository departmentRepository;
    @InjectMocks
    private DepartmentServiceImpl departmentServiceImpl;
    private Department department;

    @BeforeEach
    public void setup() {
        department = Department.builder()
                .name("test")
                .departmentAddress("test")
                .index(123456)
                .build();
    }

    @Test
    public void departmentServiceImplCreate() {
        when(departmentRepository.save(department)).thenReturn(department);

        Department departmentResponse = departmentServiceImpl.create(department);

        Assertions.assertThat(departmentResponse).isNotNull();
        Assertions.assertThat(departmentResponse).isEqualTo(department);

        verify(departmentRepository).save(department);
    }


    @Test
    public void departmentServiceImplUpdate() {
        long id = 1L;
        when(departmentRepository.findById(id)).thenReturn(Optional.ofNullable(department));
        when(departmentRepository.save(department)).thenReturn(department);

        Department departmentUpdate = departmentServiceImpl.update(department, id);

        Assertions.assertThat(departmentUpdate).isNotNull();

        verify(departmentRepository).save(department);
    }


    @Test
    public void departmentServiceImplDelete() {
        long id = 1L;
        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));

        departmentServiceImpl.delete(id);

        verify(departmentRepository).delete(department);
    }

    @Test
    public void departmentServiceImplFindById() {
        long id = 1L;
        when(departmentRepository.findById(id)).thenReturn(Optional.of(department));

        Department departmentResponse = departmentServiceImpl.findEntity(id);

        Assertions.assertThat(departmentResponse).isNotNull();

        verify(departmentRepository).findById(id);
    }

    @Test
    public void departmentServiceImplFindAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Department> departmentList = List.of(department, department);
        Page<Department> departments = new PageImpl<>(departmentList);

        when(departmentRepository.findAll(pageable)).thenReturn(departments);

        Page<Department> all = departmentServiceImpl.findAll(pageable);

        Assertions.assertThat(all).isNotNull();
    }


    @Test
    public void departmentServiceImplFindByIndex() {
        int index = 123456;
        when(departmentRepository.findDepartmentByIndex(index)).thenReturn(Optional.of(department));

        Department departmentResponse = departmentServiceImpl.findByIndex(index);

        Assertions.assertThat(departmentResponse).isNotNull();

        verify(departmentRepository).findDepartmentByIndex(index);
    }

    @Test
    public void entityNotFoundTest() {
        Long id = 1L;
        Mockito.when(departmentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> departmentServiceImpl.update(department, id));
    }
}