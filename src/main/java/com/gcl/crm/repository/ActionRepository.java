package com.gcl.crm.repository;

import com.gcl.crm.entity.Action;
import com.gcl.crm.entity.ActionType;
import com.gcl.crm.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
    List<Action> findAllByStatusAndActionType(Status status, ActionType actionType);
    Optional<Action> findByIdAndStatus(Long id, Status status);
}
