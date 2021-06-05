package com.gcl.crm.service;

import com.gcl.crm.entity.Department;
import com.gcl.crm.entity.Permission;
import com.gcl.crm.enums.Status;
import com.gcl.crm.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    ActionService actionService;

    @Autowired
    DepartmentService departmentService;

    @Override
    public List<Permission> findAllPermissions() {
        return permissionRepository.findAllByStatus(Status.ACTIVE);
    }

    @Override
    public Permission findPermissionById(String id) {
        try {
            Long pk = Long.parseLong(id);
            return permissionRepository.findPermissionByIdAndStatus(pk, Status.ACTIVE);
        } catch (NumberFormatException ex){
            return null;
        }

    }

    @Override
    public boolean createPermission(Permission permission, List<Long> actionIds) {
        permission.setActions(actionService.getActionsByIdList(actionIds));
        permission.setStatus(Status.ACTIVE);
        permission = permissionRepository.save(permission);
        return permission != null;
    }

    @Override
    public boolean updatePermission(Permission permission, List<Long> actionIds) {
        Permission permission2 = permissionRepository.findPermissionByIdAndStatus(permission.getId(), Status.ACTIVE);
        if (permission2 == null){
            return false;
        }
        permission.setActions(actionService.getActionsByIdList(actionIds));
        permission.setStatus(Status.ACTIVE);
        permission = permissionRepository.save(permission);
        return permission != null;
    }


    @Override
    public boolean deletePermission(String id) {
        try {
            Long pk = Long.parseLong(id);
            Permission permission = permissionRepository.findPermissionByIdAndStatus(pk, Status.ACTIVE);
            if (permission == null){
                return false;
            }
            permission.setStatus(Status.INACTIVE);
            permission = permissionRepository.save(permission);
            if (permission != null){
                return true;
            }
        } catch (NumberFormatException ex){
            return false;
        }
        return false;
    }

    @Override
    public boolean decentralizePermission(String pid, List<Long> didList) {
        Permission permission = this.findPermissionById(pid);
        if (permission == null){
            return false;
        }
        List<Department> departments = departmentService.findDepartmentsByIdList(didList);
        permission.setDepartments(departments);
        permission = permissionRepository.save(permission);
        return permission != null;
    }

}
