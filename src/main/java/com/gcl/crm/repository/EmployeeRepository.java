package com.gcl.crm.repository;

import com.gcl.crm.entity.Employee;
import com.gcl.crm.enums.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByStatusNot(EmployeeStatus status);
}
