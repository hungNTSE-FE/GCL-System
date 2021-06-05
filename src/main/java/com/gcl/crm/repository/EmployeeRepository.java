package com.gcl.crm.repository;

import com.gcl.crm.entity.Employee;
import com.gcl.crm.enums.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByStatusNot(EmployeeStatus status);
}
