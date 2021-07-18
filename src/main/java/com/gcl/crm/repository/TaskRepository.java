package com.gcl.crm.repository;


import com.gcl.crm.entity.Department;
import com.gcl.crm.entity.Task;
import com.gcl.crm.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository  extends JpaRepository<Task,Integer> {
    List<Task> findAllByActive(Status status);


}
