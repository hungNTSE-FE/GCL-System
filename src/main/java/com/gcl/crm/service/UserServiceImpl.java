package com.gcl.crm.service;

import com.gcl.crm.entity.Role;
import com.gcl.crm.entity.User;
import com.gcl.crm.entity.Employee;
import com.gcl.crm.entity.UserRole;
import com.gcl.crm.enums.Status;
import com.gcl.crm.repository.UserRepository;
import com.gcl.crm.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    EmployeeService employeeService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public List<User> getUsersByIdList(List<Long> userIdList) {
        if (userIdList == null){
            return null;
        }
        List<User> users = userRepository.findAllByEnabled(true);
        List<User> result = new ArrayList<>();
        for (User user : users){
            if (userIdList.contains(user.getUserId())){
                result.add(user);
            }
        }
        return result;
    }

    @Override
    public User getUserByUsername(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    @Override
    public User getUserByEmployeeId(Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        User user = userRepository.findUserByEmployee(employee);
        return user;
    }

    @Override
    public void disableUserByEmployeeId(Long employeeId) {
        Employee employee = employeeService.getEmployeeById(employeeId);
        User user = userRepository.findUserByEmployee(employee);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public List<User> getUserByEnabled() {
        return userRepository.findAllByEnabled(true);
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        List<UserRole> userRoles = userRoleRepository.findAllByRoleAndStatus(role, Status.ACTIVE);
        List<User> users = new ArrayList<>();
        for (   UserRole userRole : userRoles){
            if (userRole.getStatus().equals(Status.ACTIVE)){
                users.add(userRole.getUser());
            }
        }
        return users;
    }

    @Override
    public boolean checkUsername(String userName) {
        return userRepository.existsUserByUserName(userName);
    }
}
