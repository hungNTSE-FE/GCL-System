package com.gcl.crm.service;

import com.gcl.crm.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface TaskService {
List<Task> getAllTask();
void createTask(Task task);
void deleteTaskByID(int id);
Task findTaskByID(int id);

}
