package com.example.postoffice.repository;

import com.example.postoffice.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends CommonRepository<Department> {
    Optional<Department> findDepartmentByIndex(Integer index);
}
