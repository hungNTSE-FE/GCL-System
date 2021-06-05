package com.gcl.crm.service;

import com.gcl.crm.entity.Company;
import com.gcl.crm.entity.Department;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {
    List<Department> findAllDepartments();
    Department findDepartmentById(String id);
    boolean createDepartment(Department department);
    boolean updateDepartment(Department department);
    boolean deleteDepartment(String id);
    List<Department> findDepartmentsByCompany(Company company);
    List<Department> findDepartmentsByIdList(List<Long> idList);
}
