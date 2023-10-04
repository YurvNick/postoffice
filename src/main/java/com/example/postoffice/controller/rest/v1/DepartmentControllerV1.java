package com.example.postoffice.controller.rest.v1;

import com.example.postoffice.controller.rest.BaseController;
import com.example.postoffice.controller.rest.CommonController;
import com.example.postoffice.dto.department.RequestDepartmentDto;
import com.example.postoffice.dto.department.ResponseDepartmentDto;
import com.example.postoffice.entity.Department;
import com.example.postoffice.mapper.deprtament.DepartmentMapper;
import com.example.postoffice.service.DepartmentService;
import com.example.postoffice.service.impl.DepartmentServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentControllerV1
        extends
        BaseController<Department,
                RequestDepartmentDto,
                ResponseDepartmentDto,
                DepartmentService,
                DepartmentMapper>
        implements
        CommonController<Department,
                RequestDepartmentDto,
                ResponseDepartmentDto> {


    public DepartmentControllerV1(DepartmentService service,
                                  DepartmentMapper mapper) {
        super(service, mapper);
    }
}
