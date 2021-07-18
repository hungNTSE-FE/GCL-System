package com.gcl.crm.controller;

import com.gcl.crm.entity.Department;
import com.gcl.crm.entity.Task;
import com.gcl.crm.enums.Status;
import com.gcl.crm.service.DepartmentService;
import com.gcl.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private DepartmentService departmentService;
    @GetMapping({"/viewAllTask"})
    public  String viewTaskPage(Model model){
        model.addAttribute("listTasks",taskService.getAllTask());
        List<Department> departmentList = departmentService.findAllDepartments();
        System.out.println(departmentList.get(1).getTasks().size());
        return "task/task-home";
    }


    @GetMapping({"/showCreateForm"})
    public String showTaskCreatePage(Model model){
        Task task = new Task();
        model.addAttribute("task",task);
        return "task/create-task-page";
    }
    @PostMapping({"/saveTask"})
    public String saveTask(@ModelAttribute("task") Task task){
        task.setActive(Status.ACTIVE);
        List<Department> departmentList = departmentService.findAllDepartments();
        departmentList.get(1).getTasks().add(task);
        task.setDepartment(departmentList.get(1));




        taskService.createTask(task);
        return "redirect:/task/viewAllTask";

    }
    @GetMapping({"/showUpdateTaskForm/{id}"})
    public String showTaskUpdateForm(@PathVariable(name = "id") int id, Model model){
        Task task = taskService.findTaskByID(id);
        model.addAttribute("task",task);
        return "task/update-task-page";
    }
    @PostMapping({"/updateTask"})
    public String updateTask(@ModelAttribute("task") Task task){
        List<Department> departmentList = departmentService.findAllDepartments();
        departmentList.get(1).getTasks().add(task);
        task.setDepartment(departmentList.get(1));




        taskService.createTask(task);
        return "redirect:/task/viewAllTask";
    }
    @GetMapping({"/deleteTask/{id}"})
    public String deleteTask(@PathVariable(value ="id") int id){
        this.taskService.deleteTaskByID(id);
        return "redirect:/task/viewAllTask";

    }
}
