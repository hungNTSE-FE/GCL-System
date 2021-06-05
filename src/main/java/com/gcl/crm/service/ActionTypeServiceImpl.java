package com.gcl.crm.service;

import com.gcl.crm.entity.Action;
import com.gcl.crm.entity.ActionType;
import com.gcl.crm.enums.Status;
import com.gcl.crm.repository.ActionRepository;
import com.gcl.crm.repository.ActionTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionTypeServiceImpl implements ActionTypeService {

    @Autowired
    ActionTypeRepository actionTypeRepository;

    @Autowired
    ActionRepository actionRepository;

    @Override
    public List<ActionType> findAllActionTypes() {
        List<ActionType> actionTypes = actionTypeRepository.findAll();
        for (ActionType actionType : actionTypes){
            List<Action> actions = actionRepository.findAllByStatusAndActionType(Status.ACTIVE, actionType);
            actionType.setActions(actions);
        }
        return actionTypes;
    }
}
