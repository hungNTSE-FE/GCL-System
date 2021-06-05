package com.gcl.crm.repository;

import com.gcl.crm.entity.Permission;
import com.gcl.crm.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAllByStatus(Status status);
    Permission findPermissionByIdAndStatus(Long id, Status status);

}
