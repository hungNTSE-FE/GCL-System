package com.gcl.crm.service;

import com.gcl.crm.entity.Employee;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    List<Employee> getAllWorkingEmployees();

}
