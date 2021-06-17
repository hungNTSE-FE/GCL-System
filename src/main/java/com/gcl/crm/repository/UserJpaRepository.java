package com.gcl.crm.repository;

import com.gcl.crm.entity.AppUser;
import com.gcl.crm.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByEmployee(Employee employee);
    boolean existsAppUserByUserName(String userName);
}
