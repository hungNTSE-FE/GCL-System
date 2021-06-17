package com.gcl.crm.service;

import com.gcl.crm.entity.AppUser;

import java.util.List;

public interface UserService {
    List<AppUser> getAppUsersByIdList(List<Long> aidList);
    AppUser getUserByEmployeeId(Long employeeId);
    void disableUserByEmployeeId(Long employeeId);
    boolean checkUsername(String userName);
}
