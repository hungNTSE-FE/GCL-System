package com.gcl.crm.service;

import com.gcl.crm.entity.Task;
import com.gcl.crm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TaskServiceImpl  implements  TaskService{

    @Autowired
    private TaskRepository taskRepository;
    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public void createTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void deleteTaskByID(int id) {
    this.taskRepository.deleteById(id);

    }

    @Override
    public Task findTaskByID(int id) {
      Optional<Task> option = taskRepository.findById(id);
      Task task = null ;
      if(option.isPresent()){
          task = option.get();
      }else {
          throw new RuntimeException("Task not found for id  :"+id);
      }
        return task;

    }
}
