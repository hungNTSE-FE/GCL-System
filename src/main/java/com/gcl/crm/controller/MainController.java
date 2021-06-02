package com.gcl.crm.controller;

import com.gcl.crm.entity.Department;
import com.gcl.crm.entity.Documentary;
import com.gcl.crm.entity.Task;
import com.gcl.crm.repository.DocumentaryRepository;
import com.gcl.crm.service.DepartmentService;
import com.gcl.crm.service.DocumentaryService;
import com.gcl.crm.service.TaskService;
import com.gcl.crm.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DocumentaryService documentaryService;

    @Autowired
    private DocumentaryRepository documentaryRepo;
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "/department/home-department-page";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String DefaultPage(Model model) {
        return "loginPage";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);
        return "adminPage";
    }

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String loginPage(Model model) {
//        return "loginPage";
//    }

    @RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "loginPage";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // Sau khi user login thanh cong se co principal
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "userInfoPage";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi" + principal.getName() + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);
        }
        return "403Page";
    }

    @RequestMapping(value = "/createDepartmentPage", method = RequestMethod.GET)
    public String linkCreateDepartmentPage(Model model) {
        return "/department/create-department-page";
    }

    @RequestMapping(value = "/homeDepartmentPage", method = RequestMethod.GET)
    public String linkHomeDepartmentPage(Model model) {
        return "/department/home-department-page";
    }

    @RequestMapping(value = "/editDepartmentPage", method = RequestMethod.GET)
    public String linkEditDepartmentPage(Model model) {
        return "/department/edit-department-page";
    }

    @RequestMapping(value = "/homePermissionPage", method = RequestMethod.GET)
    public String linkHomePermissionPage(Model model) {
        return "/permission/home-permission-page";
    }

    @RequestMapping(value = "/decentralizationPermissionPage", method= RequestMethod.GET)
    public String linkDecentralizationPermissionPage(Model model) {
        return "/permission/decentralization-permission-page";
    }

    @RequestMapping(value = "/createPermissionPage", method = RequestMethod.GET)
    public String linkCreatePermissionPage(Model model) {
        return "/permission/create-permission-page";
    }

//    @GetMapping("/task/viewAllTask")
//    public  String viewTaskPage(Model model){
//        model.addAttribute("listTasks",taskService.getAllTask());
//        return "task/view-task-page";
//    }
//    @GetMapping("/task/showCreateForm")
//    public String showTaskCreatePage(Model model){
//        Task task = new Task();
//        model.addAttribute("task",task);
//        return "task/create-task-page";
//    }
//    @PostMapping("/task/saveTask")
//    public String saveTask(@ModelAttribute("task") Task task){
//        taskService.createTask(task);
//        return "redirect:/task/viewAllTask";
//    }


}
