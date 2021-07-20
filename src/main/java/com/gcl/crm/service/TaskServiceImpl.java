package com.gcl.crm.service;

import com.amazonaws.services.cloudfront.model.transform.AliasesStaxUnmarshaller;
import com.amazonaws.services.simpleworkflow.model.TaskList;
import com.gcl.crm.entity.Employee;
import com.gcl.crm.entity.Task;
import com.gcl.crm.entity.User;
import com.gcl.crm.enums.Status;
import com.gcl.crm.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TaskServiceImpl  implements  TaskService{

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EmployeeService employeeService;
    @Override
    public List<Task> getAllTask() {

        return  taskRepository.findAllByActive(Status.ACTIVE);

    }

    @Override
    public void createTask(Task task) {
        List<Employee> employeeList = new ArrayList<>();
        employeeList = task.getEmployees();

        for(int i = 0 ; i <employeeList.size();i++){
            employeeList.get(i).getTasks().add(task);
        }
      taskRepository.save(task);
    }

    @Override
    public void deleteTaskByID(long id) {
    Task task = this.findTaskByID(id);
    task.setActive(Status.INACTIVE);
    taskRepository.save(task);

    }

    @Override
    public Task findTaskByID(Long id) {
      Optional<Task> option = taskRepository.findById(id);
      Task task = null ;
      if(option.isPresent()){
          task = option.get();
      }else {
          throw new RuntimeException("Task not found for id  :"+id);
      }
        return task;

    }

    @Override
    public void submitFinishTask(Task task, User currentUser) {
        Employee employee = employeeService.getEmployeeById(currentUser.getEmployee().getId());
        task.setSubmitDate(Date.valueOf(LocalDate.now()));
        Date submitDate = (Date) task.getSubmitDate();
        Date endDate = task.getEndDate();
        if(submitDate.after(endDate)){
            task.setSubmitStatus("Trễ hạn");
        }else {
            task.setSubmitStatus("Đúng hạn");
        }
        task.setEmployeeID(employee.getId());
        taskRepository.save(task);
    }

    @Override
    public void checkFinishTask(Task task) {
        task.setStatus("Hoàn thành");
        taskRepository.save(task);
    }
}
