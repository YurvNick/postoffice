package com.example.postoffice.service.impl;

import com.example.postoffice.entity.Department;
import com.example.postoffice.repository.DepartmentRepository;
import com.example.postoffice.service.BaseService;
import com.example.postoffice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service("DepartmentServiceImpl")
public class DepartmentServiceImpl
        extends BaseService<Department, DepartmentRepository>
        implements DepartmentService {

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository repository) {
        super(repository);
    }

    public Department findByIndex(Integer indexDepartment) {
        Optional<Department> department = super.repository.findDepartmentByIndex(indexDepartment);
        if (department.isPresent()) {
            return department.get();
        }

        throw new EntityNotFoundException("Department not found");
    }
}
