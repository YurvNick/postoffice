package com.example.postoffice.service;

import com.example.postoffice.entity.Department;

public interface DepartmentService extends CommonService<Department> {

    Department findByIndex(Integer indexDepartment);
}
