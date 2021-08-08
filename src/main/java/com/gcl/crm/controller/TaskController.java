package com.gcl.crm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gcl.crm.entity.Department;
import com.gcl.crm.entity.Employee;
import com.gcl.crm.entity.Task;
import com.gcl.crm.entity.User;
import com.gcl.crm.enums.Status;
import com.gcl.crm.form.EmployeeSearchForm;
import com.gcl.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PotentialService potentialService;
    private static final String DASHBOARD_PAGE = "/task/task-home";
    private static final String EMPLOYEE_TASK_PAGE = "/task/view-employee-task";
    private static final String CREATE_TASK_PAGE = "/task/create-task-page";
    private static final String UPDATE_TASK_PAGE = "/task/update-task-page";
    private static final String EMPLOYEE_DETAIL_TASK_PAGE = "/task/view-detail-employee-task";

    @GetMapping({"/home"})
    public  String viewTaskPage(Model model, Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());

        model.addAttribute("userInfo", currentUser);
        model.addAttribute("listTasks",taskService.getAllTask());
        return DASHBOARD_PAGE;
    }
    @GetMapping({"/viewEmployeeTask"})
    public  String viewEmployeeTaskPage(Model model, Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());

        Employee employee = employeeService.getEmployeeById(currentUser.getEmployee().getId());
        model.addAttribute("listTasks",employee.getTasks());
        model.addAttribute("userInfo", currentUser);

        return EMPLOYEE_TASK_PAGE;
    }


    @GetMapping({"/showCreateForm"})
    public String showTaskCreatePage(Model model, Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());
        Task task = new Task();
        model.addAttribute("task",task);
        List<Department> departments = departmentService.findAllDepartments();
        model.addAttribute("departments", departments);
        model.addAttribute("userInfo", currentUser);
        return CREATE_TASK_PAGE;
    }
    @PostMapping(value = "/getEmployeeByDepartmentId")
    public ResponseEntity<List<EmployeeSearchForm>> getEmployeeByDepartmentId(@RequestBody String id) throws JsonProcessingException {
        List<EmployeeSearchForm> employeeList = null;
        try {
            employeeList = potentialService.getEmployeeByDepartmentId(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @PostMapping({"/saveTask"})
    public String saveTask(@ModelAttribute("task") Task task, RedirectAttributes redirectAttributes) throws ParseException {
        if(task == null){
            return "redirect:/task/home";
        }
        task.setActive(Status.ACTIVE);
        task.setCreateDate(Date.valueOf(LocalDate.now()));
        task.setSubmitStatus("Đang tiến hành");
        task.setDepartmentName(task.getEmployees().get(0).getDepartment().getName());
        redirectAttributes.addFlashAttribute("flag","showAlert");
        taskService.createTask(task);
        return "redirect:/task/home";


    }
    @PostMapping({"/updateTask"})
    public String updateTask(@ModelAttribute("task") Task task){
        if(task == null){
            return "redirect:/task/viewAllTask";
        }else {
            Task tmp = taskService.findTaskByID(task.getTask_id());
            task.setActive(Status.ACTIVE);
            task.setCreateDate((Date) tmp.getCreateDate());
            task.setSubmitStatus(tmp.getSubmitStatus());
            task.setDepartmentName(task.getEmployees().get(0).getDepartment().getName());
            task.setUpdateDate(Date.valueOf(LocalDate.now()));
            taskService.createTask(task);
            return "redirect:/task/viewAllTask";

        }


    }



    @GetMapping({"/showUpdateTaskForm/{id}"})
    public String showTaskUpdateForm(@PathVariable(name = "id") String id, Model model,Principal principal){
        Task task = taskService.findTaskByID(Long.parseLong(id));
        if(task == null){
            return "redirect:/task/viewAllTask";

        }else {
            List<Employee> employeeList = new ArrayList<>();
            long departmentID = task.getEmployees().get(0).getDepartment().getId();
            List<Department> departments = departmentService.findDepartmentsByTask(departmentID,task.getEmployees());
            List<Employee> employees = departments.get(0).getEmployees();
            List<Employee> employees1 = task.getEmployees();
            task.setEmployees(employeeList);
            User currentUser = userService.getUserByUsername(principal.getName());

            model.addAttribute("userInfo", currentUser);
            model.addAttribute("employeeHaveTask",employees1);
            model.addAttribute("task",task);
            model.addAttribute("employees",employees);
            model.addAttribute("departments", departments);
            return UPDATE_TASK_PAGE;
        }

    }
    @GetMapping({"/submitTask/{id}"})
    public String submitFinishTask(@PathVariable(name = "id") String id,Principal principal){
        Task task = taskService.findTaskByID(Long.parseLong(id));
        User currentUser = userService.getUserByUsername(principal.getName());

        taskService.submitFinishTask(task,currentUser);

        return "redirect:/task/viewEmployeeTask";
    }
    @GetMapping({"/finishCheck/{id}"})
    public String checkFinishTask(@PathVariable(name = "id") String id,Principal principal){
        Task task = taskService.findTaskByID(Long.parseLong(id));
        User currentUser = userService.getUserByUsername(principal.getName());

        taskService.checkFinishTask(task);

        return "redirect:/task/viewAllTask";
    }

    @GetMapping({"/showTaskDetail/{id}"})
    public String showTaskDetail(@PathVariable(name = "id") String id, Model model,Principal principal){
        Task task = taskService.findTaskByID(Long.parseLong(id));
        if(task == null){
            return "redirect:/task/viewAllTask";

        }else{
            User currentUser = userService.getUserByUsername(principal.getName());
            List<Employee> employeeList = new ArrayList<>();
            long departmentID = task.getEmployees().get(0).getDepartment().getId();
            List<Department> departments = departmentService.findDepartmentsByTask(departmentID,task.getEmployees());
            List<Employee> employees = departments.get(0).getEmployees();
            List<Employee> employees1 = task.getEmployees();
            task.setEmployees(employeeList);
            model.addAttribute("employeeHaveTask",employees1);
            model.addAttribute("task",task);
            model.addAttribute("employees",employees);
            model.addAttribute("departments", departments);
            model.addAttribute("userInfo", currentUser);

            return EMPLOYEE_DETAIL_TASK_PAGE;
        }

    }

    @GetMapping({"/deleteTask/{id}"})
    public String deleteTask(@PathVariable(value ="id") int id){
        this.taskService.deleteTaskByID(id);
        return "redirect:/task/viewAllTask";

    }
}
