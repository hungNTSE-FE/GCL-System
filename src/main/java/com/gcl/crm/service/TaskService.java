package com.gcl.crm.service;

import com.gcl.crm.entity.Task;


import java.util.List;


public interface TaskService {
List<Task> getAllTask();

void createTask(Task task);
void deleteTaskByID(int id);
Task findTaskByID(int id);

}
