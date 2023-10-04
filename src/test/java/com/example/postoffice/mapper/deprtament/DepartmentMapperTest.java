package com.example.postoffice.mapper.deprtament;


import com.example.postoffice.dto.department.RequestDepartmentDto;
import com.example.postoffice.dto.department.ResponseDepartmentDto;
import com.example.postoffice.entity.Department;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class DepartmentMapperTest {

    @Autowired
    private DepartmentMapper departmentMapper;
    private Department department;
    private RequestDepartmentDto requestDepartmentDto;

    @BeforeEach
    public void setup() {
        department = Department.builder()
                .name("test")
                .departmentAddress("test")
                .index(123456)
                .build();

        requestDepartmentDto = RequestDepartmentDto.builder()
                .name("test")
                .departmentAddress("test")
                .index(123456)
                .build();

    }
    @Test
    public void departmentMapperToEntity() {
        Department entity = departmentMapper.toEntity(requestDepartmentDto);

        Assertions.assertEquals(entity.getName(),requestDepartmentDto.getName());
    }

    @Test
    public void departmentMapperToResponse() {
        ResponseDepartmentDto response = departmentMapper.toResponse(department);

        Assertions.assertEquals(response.getName(),department.getName());
    }

    @Test
    public void departmentMapperUpdate() {
        requestDepartmentDto.setName("ivan");

        Department departmentUpdate = departmentMapper.partialUpdate(requestDepartmentDto, department);

        Assertions.assertEquals(departmentUpdate.getName(),"ivan");
    }

}