package com.gcl.crm.service;

import com.gcl.crm.entity.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAllPermissions();
    Permission findPermissionById(String id);
    boolean createPermission(Permission permission, List<Long> actionIds);
    boolean updatePermission(Permission permission, List<Long> actionIds);
    boolean deletePermission(String id);
    boolean decentralizePermission(String pid, List<Long> didList);
}
