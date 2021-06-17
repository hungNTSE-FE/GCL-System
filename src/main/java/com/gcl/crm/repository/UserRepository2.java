package com.gcl.crm.repository;

import com.gcl.crm.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository2 extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByUserName(String userName);
}
