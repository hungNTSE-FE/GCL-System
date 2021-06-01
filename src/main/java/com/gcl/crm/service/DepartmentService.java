package com.gcl.crm.service;

import com.gcl.crm.entity.Department;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {
    List<Department> findAllDepartments();
    Department findDepartmentById(Long id);
    boolean createDepartment(Department department);
    boolean updateDepartment(Department department);
    boolean deleteDepartment(Long id);

}
