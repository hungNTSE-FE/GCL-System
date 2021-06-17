package com.gcl.crm.repository;

import com.gcl.crm.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository2 extends JpaRepository<AppUser, Long> {
    AppUser findAppUserByUserName(String userName);
    List<AppUser> findAllByEnabled(boolean enabled);
    Optional<AppUser> findByUserIdAndAndEnabled(Long id, boolean enabled);
}
