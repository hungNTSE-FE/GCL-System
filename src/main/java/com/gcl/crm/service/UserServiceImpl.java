package com.gcl.crm.service;

import com.gcl.crm.entity.Action;
import com.gcl.crm.entity.AppUser;
import com.gcl.crm.enums.Status;
import com.gcl.crm.repository.UserRepository2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository2 userRepository2;

    @Override
    public List<AppUser> getAppUsersByIdList(List<Long> aidList) {
        if (aidList == null){
            return null;
        }
        List<AppUser> appUsers = new ArrayList<>();
        for (int i = 0; i < aidList.size(); i++) {
            Optional<AppUser> action = userRepository2.findByUserIdAndAndEnabled(aidList.get(i), true);
            if (action.isPresent()){
                appUsers.add(action.get());
            }
        }
        return appUsers;
    }
}
