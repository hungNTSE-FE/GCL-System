package com.gcl.crm.repository;

import com.gcl.crm.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository  extends JpaRepository<Task,Integer> {
}
