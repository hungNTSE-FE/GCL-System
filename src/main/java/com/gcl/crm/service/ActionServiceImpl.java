package com.gcl.crm.service;

import com.gcl.crm.entity.Action;
import com.gcl.crm.enums.Status;
import com.gcl.crm.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActionServiceImpl implements ActionService{

    @Autowired
    ActionRepository actionRepository;

    @Override
    public List<Action> getActionsByIdList(List<Long> aidList) {
        if (aidList == null){
            return null;
        }
        List<Action> actions = new ArrayList<>();
        for (int i = 0; i < aidList.size(); i++) {
            Optional<Action> action = actionRepository.findByIdAndStatus(aidList.get(i), Status.ACTIVE);
            if (action.isPresent()){
                actions.add(action.get());
            }
        }
        return actions;
    }
}
