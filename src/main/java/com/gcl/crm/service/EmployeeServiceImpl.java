package com.gcl.crm.service;

import com.gcl.crm.entity.AppUser;
import com.gcl.crm.entity.Department;
import com.gcl.crm.entity.Employee;
import com.gcl.crm.entity.Position;
import com.gcl.crm.enums.EmployeeStatus;
import com.gcl.crm.repository.EmployeeRepository;
import com.gcl.crm.repository.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    PositionService positionService;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    UserService userService;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getAllWorkingEmployees() {
        return employeeRepository.findAllByStatusNot(EmployeeStatus.OFF_WORKING);
    }

    @Override
    public boolean createEmployee(Employee employee, Long pid, Long did) {
        Position position = positionService.findPositionById(pid);
        Department department = null;
        if (did != null){
            department = departmentService.findDepartmentById(did.toString());
        }
        employee.setDepartment(department);
        employee.setPosition(position);

        AppUser appUser = employee.getAppUser();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        appUser.setEncrytedPassword(bCryptPasswordEncoder.encode(appUser.getEncrytedPassword()));
        appUser.setEnabled(true);

        employee.setIdentification(null);
        employee.setStatus(EmployeeStatus.WORKING);
        employee.setAppUser(null);
        employee = employeeRepository.save(employee);
        appUser.setEmployee(employee);
        userJpaRepository.save(appUser);
        return employee != null;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()){
            return employee.get();
        }
        return null;
    }

    @Override
    public boolean updateEmployee(Employee employee, Long pid, Long did, String userName, String password) {
        Employee employee2 = this.getEmployeeById(employee.getId());
        if (employee2 == null){
            return false;
        }
        AppUser appUser = userService.getUserByEmployeeId(employee.getId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        appUser.setEncrytedPassword(encoder.encode(password));
        appUser = userJpaRepository.save(appUser);

        Position position = positionService.findPositionById(pid);
        Department department = departmentService.findDepartmentById(did.toString());
        employee2.setPosition(position);
        employee2.setDepartment(department);
        employee2.setAddress(employee.getAddress());
        employee2.setBirthDate(employee.getBirthDate());
        employee2.setStartDate(employee.getStartDate());
        employee2.setName(employee.getName());
        employee2.setNote(employee.getNote());
        employee2.setPhone(employee.getPhone());
        employee2.setCompanyEmail(employee.getCompanyEmail());
        employee2.setAppUser(appUser);
        employeeRepository.save(employee2);
        return true;
    }

    @Override
    public boolean deleteEmployee(Long eid) {
        if (eid == null){
            return false;
        }
        Employee employee = this.getEmployeeById(eid);
        if (employee == null || employee.getStatus().equals(EmployeeStatus.OFF_WORKING)){
            return false;
        }
        employee.setStatus(EmployeeStatus.OFF_WORKING);
        userService.disableUserByEmployeeId(eid);
        employeeRepository.save(employee);
        return true;
    }

}
