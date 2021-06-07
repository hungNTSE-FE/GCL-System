package com.gcl.crm;

import com.gcl.crm.entity.AppUser;
import com.gcl.crm.entity.Department;
import com.gcl.crm.entity.Documentary;
import com.gcl.crm.entity.Employee;
import com.gcl.crm.service.EmployeeService;
import com.gcl.crm.service.EmployeeServiceImpl;
import com.gcl.crm.service.TaskService;
import com.gcl.crm.utils.EncryptedPasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootApplication

public class CrmApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        String password = "123";
        String encryptedPassword = EncryptedPasswordUtils.encryptPassword(password);

        System.out.println("Encryted Password: " + encryptedPassword);
        SpringApplication.run(CrmApplication.class, args);









    }

}
