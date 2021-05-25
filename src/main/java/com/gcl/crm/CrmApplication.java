package com.gcl.crm;

import com.gcl.crm.utils.EncryptedPasswordUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableJpaRepositories("com.gcl.crm.entity")
public class CrmApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        String password = "123";
        String encryptedPassword = EncryptedPasswordUtils.encryptPassword(password);

        System.out.println("Encryted Password: " + encryptedPassword);
        SpringApplication.run(CrmApplication.class, args);
    }

}
