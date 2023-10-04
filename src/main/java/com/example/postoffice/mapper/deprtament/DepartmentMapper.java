package com.example.postoffice.mapper.deprtament;

import com.example.postoffice.dto.department.RequestDepartmentDto;
import com.example.postoffice.dto.department.ResponseDepartmentDto;
import com.example.postoffice.entity.Department;
import com.example.postoffice.mapper.CommonMapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DepartmentMapper extends CommonMapper<Department, RequestDepartmentDto, ResponseDepartmentDto> {
}
