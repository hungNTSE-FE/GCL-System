package com.gcl.crm.service;

import com.gcl.crm.entity.Action;

import java.util.List;

public interface ActionService {
    List<Action> getActionsByIdList(List<Long> aidList);
}
