package com.gcl.crm.controller;

import com.gcl.crm.entity.Department;
import com.gcl.crm.entity.Employee;
import com.gcl.crm.entity.User;
import com.gcl.crm.service.DepartmentService;
import com.gcl.crm.service.EmployeeService;
import com.gcl.crm.service.UserService;
import com.gcl.crm.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import com.gcl.crm.constants.MyConstants;
import com.gcl.crm.service.UserService;
import org.springframework.lang.Nullable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    private static final String LOGIN_PAGE = "loginPage";
    private static final String FORGOT_PAGE = "forgot-password";
    private static final String PAGE_ERROR_403 = "error/error-403";
    private static final String DEPARTMENT_PAGE = "/department/home-department-page-V2";

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserService userService;


    @RequestMapping(value = {"/welcome", "/"}, method = RequestMethod.GET)
    public String welcomePage(Model model, Principal principal) {
        if (principal == null) {
            return "loginPage";
        }
        User currentUser = userService.getUserByUsername(principal.getName());
        Department departmentForm = new Department();
        List<Department> departments = departmentService.findAllDepartments();
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("departments", departments);
        model.addAttribute("departmentForm", departmentForm);
        System.out.println("department home");
        model.addAttribute("userName", principal.getName());
        model.addAttribute("userInfo", currentUser);
        return DEPARTMENT_PAGE;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String getTest() {
        return "test";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String getLoginPage(Model model) {
        return LOGIN_PAGE;
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public String getForgotPassword() {
        return FORGOT_PAGE;
    }

    @PostMapping("/forgot-password")
    public String sendNewPasswordEmail(Model model,@Nullable @RequestParam("email") String email){
        Employee employee = employeeService.getEmployeeByEmail(email);
        if (employee == null){
            model.addAttribute("email", email);
            model.addAttribute("notFound", "Không tìm thấy tài khoản được liên kết với email này");
            return FORGOT_PAGE;
        }
        String newPassword = WebUtils.generateRandomPassword(8);
        System.out.println(newPassword);
        if (userService.changePassword(employee.getUser(), newPassword)){
            StringBuilder content = new StringBuilder();
            content.append("Chào " + employee.getName() + ",\n\n");
            content.append("Đây là mật khẩu mới của bạn tại CRM-Gia Cát Lợi:\n\n");
            content.append("Tài khoản: " + employee.getUser().getUserName() + "\n\n");
            content.append("Mật khẩu: " + newPassword + "\n\n");
            content.append("LƯU Ý: Nếu bạn không phải người thực hiện thao tác này, hãy thông báo cho quản lý của bạn.");
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(MyConstants.MY_EMAIL);
            message.setTo(employee.getCompanyEmail());
            message.setSubject("Lấy lại mật khẩu tại CRM-Gia Cát Lợi");
            message.setText(content.toString());
            try {
                javaMailSender.send(message);
                model.addAttribute("message", "Mật khẩu mới đã được gửi đến " + email);
            } catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }
        return FORGOT_PAGE;
    }

    @GetMapping(value = {"/403"})
    public String accessDenied(){
        return PAGE_ERROR_403;
    }
}
