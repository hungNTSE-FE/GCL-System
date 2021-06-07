package com.gcl.crm.controller;

import com.gcl.crm.entity.Task;
import com.gcl.crm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping({"/viewAllTask"})
    public  String viewTaskPage(Model model){
        model.addAttribute("listTasks",taskService.getAllTask());
        return "task/task-home";
    }


    @GetMapping({"/showCreateForm"})
    public String showTaskCreatePage(Model model){
        Task task = new Task();
        model.addAttribute("task",task);
        return "task/create-task-page-v2";
    }
    @PostMapping({"/saveTask"})
    public String saveTask(@ModelAttribute("task") Task task){
        taskService.createTask(task);
        return  "redirect:/task/viewAllTask";
    }
    @GetMapping({"/showUpdateTaskForm/{id}"})
    public String showTaskUpdateForm(@PathVariable(name = "id") int id, Model model){
        Task task = taskService.findTaskByID(id);
        model.addAttribute("task",task);
        return "task/update-task-page";
    }
    @PostMapping({"/updateTask"})
    public String updateTask(@ModelAttribute("task") Task task){
        taskService.createTask(task);
        return "redirect:/task/viewAllTask";
    }
    @GetMapping({"/deleteTask/{id}"})
    public String deleteTask(@PathVariable(value ="id") int id){
        this.taskService.deleteTaskByID(id);
        return "redirect:/task/viewAllTask";

    }
}
