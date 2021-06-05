package com.gcl.crm.service;

import com.gcl.crm.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    List<Employee> getAllWorkingEmployees();
}
