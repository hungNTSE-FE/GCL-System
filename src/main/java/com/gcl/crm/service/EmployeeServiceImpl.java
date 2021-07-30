package com.gcl.crm.service;

import com.gcl.crm.entity.*;
import com.gcl.crm.enums.EmployeeStatus;
import com.gcl.crm.enums.Status;
import com.gcl.crm.repository.EmployeeRepository;
import com.gcl.crm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    MarketingGroupService marketingGroupService;

    @Autowired
    PositionService positionService;

    @Autowired
    UserRepository userRepository;

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
    public List<Employee> getEmployeesByIdList(List<Long> aidList) {
        if (aidList == null) {
            return null;
        }
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < aidList.size(); i++) {
            Optional<Employee> employee = employeeRepository.findByIdAndStatusNot(aidList.get(i), EmployeeStatus.OFF_WORKING);
            if (employee.isPresent()) {
                employees.add(employee.get());
            }
        }
        return employees;
    }

    @Override
    public boolean createEmployee(Employee employee, Long pid, Long did) throws DuplicateKeyException {
        Position position = positionService.findPositionById(pid);
        Department department = null;
        if (did != null){
            department = departmentService.findDepartmentById(did.toString());
        }
        employee.setDepartment(department);
        employee.setPosition(position);

        User user = employee.getUser();
        if (userService.checkUsername(user.getUserName())){
            throw new DuplicateKeyException("Duplicate username");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getEncryptedPassword()));
        user.setEnabled(true);
        user.setEmployee(employee);
        employee.setIdentification(null);
        employee.setStatus(EmployeeStatus.WORKING);
        employee.setUser(user);
        employee = employeeRepository.save(employee);
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
        User user = userService.getUserByEmployeeId(employee.getId());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setEncryptedPassword(encoder.encode(password));
        user = userRepository.save(user);

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
        employee2.setUser(user);
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

    @Override
    public List<Employee> getAllNotGroupedEmployees() {
        List<Employee> employees = this.getAllWorkingEmployees();
        return employees.stream().filter(emp -> Objects.isNull(emp.getMarketingGroup())).collect(Collectors.toList());
    }

    @Override
    public List<Employee> getAllGroupedEmployees(String id) {
        Long empGroupId = Long.parseLong(id);
        return employeeRepository.findAllById(empGroupId);
    }

    @Override
    public List<Employee> getAllWorkingEmployeesWithUserNotNull() {
        List<Employee> employees = employeeRepository.findAllByStatusNot(EmployeeStatus.OFF_WORKING);
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees){
            if (employee.getUser() == null){
                continue;
            }
            result.add(employee);
        }
        return result;
    }

    @Override
    public Employee getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findEmployeeByCompanyEmail(email);
        if ( employee != null && employee.getStatus().equals(EmployeeStatus.WORKING)){
            return employee;
        }
        return null;
    }

    @Override
    public boolean isPhoneExisted(String phone, Long id) {
        Employee employee = (id != null) ? employeeRepository.findEmployeeByPhoneAndIdNot(phone, id)
                : employeeRepository.findEmployeeByPhone(phone);
        return employee != null;
    }

    @Override
    public boolean isEmailExisted(String email, Long id) {
        Employee employee = (id != null) ? employeeRepository.findEmployeeByCompanyEmailAndIdNot(email, id)
                : employeeRepository.findEmployeeByCompanyEmail(email);
        return employee != null;
    }

}
