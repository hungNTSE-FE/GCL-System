package com.gcl.crm.service;

import com.gcl.crm.entity.Employee;
import com.gcl.crm.entity.Task;


import com.gcl.crm.entity.User;
import java.util.List;


public interface TaskService {
List<Task> getAllTask();
void createTask(Task task);
void deleteTaskByID(long id);
Task findTaskByID(Long id);
void submitFinishTask(Task task , User currentUser);
void checkFinishTask(Task task);

}
