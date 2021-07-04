package com.gcl.crm.service;

import com.amazonaws.services.cloudfront.model.transform.AliasesStaxUnmarshaller;
import com.gcl.crm.entity.Task;
import com.gcl.crm.enums.Status;
import com.gcl.crm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TaskServiceImpl  implements  TaskService{

    @Autowired
    private TaskRepository taskRepository;
    @Override
    public List<Task> getAllTask() {
//        List<Task> allTask = taskRepository.findAll();
//        List<Task> result = new ArrayList<>();
//        for(int i = 0; i< allTask.size();i++){
//            System.out.println(allTask.get(i).getActive());
//            if(allTask.get(i).getActive().equals(Status.ACTIVE)){
//                result.add(allTask.get(i));
//            }
//        }
//return result ;
        return  taskRepository.findAllByActive(Status.ACTIVE);

    }

    @Override
    public void createTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void deleteTaskByID(int id) {
    Task task = this.findTaskByID(id);
    task.setActive(Status.INACTIVE);
    taskRepository.save(task);

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
