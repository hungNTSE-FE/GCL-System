package com.gcl.crm.controller;

import com.gcl.crm.entity.Company;
import com.gcl.crm.entity.Department;
import com.gcl.crm.entity.Employee;
import com.gcl.crm.enums.Status;
import com.gcl.crm.service.DepartmentService;
import com.gcl.crm.service.EmployeeService;
import com.gcl.crm.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    @RequestMapping(value ="/home", method = RequestMethod.GET)
    public String home(Model model, Principal principal){
        // Sau khi user login thanh cong se co principal
        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        Department departmentForm = new Department();
        List<Department> departments = departmentService.findAllDepartments();
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        model.addAttribute("departments", departments);
        model.addAttribute("departmentForm", departmentForm);
        return "department/home-department-page-v2";
    }

    @GetMapping({"/create"})
    public String goCreatePage(Model model){
        Department department = new Department();
        department.setCompany(new Company());
        model.addAttribute("department", department);
        return "department/create-department-page";
    }

    @RequestMapping(value = "/edit-v2", method = RequestMethod.GET)
    public String goEditV2Page(Model model, @Nullable @RequestParam("did") String id) {
        Department department = departmentService.findDepartmentById(id);
        if (department == null){
            return "redirect:/department/home";
        }
        model.addAttribute("department", department);
        return "department/edit-department-page-v2";
    }

    @PostMapping({"/edit-v2"})
    public String editV2(Model model, @Nullable @RequestParam("did") String id,
                         @Nullable @RequestParam("dname") String name,
                         @Nullable @RequestParam("dnote") String note){
        Department department = departmentService.findDepartmentById(id);
        if (department == null){
            model.addAttribute("error", "Không tìm thấy phòng ban đã chọn!");
            return "redirect:/department/home";
        }
        if (name == null){
            model.addAttribute("error", "Tên phòng ban không để trống.");
        }
        department.setName(name);
        department.setNote(note);
        boolean done = departmentService.updateDepartment(department);
        if (done){
            model.addAttribute("message", "Cập nhật thông tin phòng ban thành công.");
        } else {
            model.addAttribute("error", "Đã có lỗi xảy ra! Cập nhật thông tin thất bại.");
        }
        return "redirect:/department/edit-v2?did=" + department.getId();
    }

    @PostMapping({"/create"})
    public String create(Model model, @ModelAttribute("departmentForm") Department department, RedirectAttributes redirectAttributes){
        String message = "Phòng ban đã được tạo thành công!";
        department.setStatus(Status.ACTIVE);
        boolean done = departmentService.createDepartment(department);
        if (!done) {
            message = "Đã xảy ra lỗi! Tạo mới phòng ban thất bại.";
        }
        model.addAttribute("message", message);
        return "redirect:/department/home";
    }

    @GetMapping({"/edit"})
    public String goEditPage(Model model, @Nullable @RequestParam("did") String id){
        Department department = departmentService.findDepartmentById(id);
        if (department == null){
            System.out.println(id);
            return "redirect:/department/home";
        }
        model.addAttribute("department", department);
        return "department/edit-department-page";
    }

    @PostMapping({"/edit"})
    public String edit(Model model, @Nullable @ModelAttribute("department") Department department){
        if (department == null){
            return "redirect:/department/home";
        }
        boolean done = departmentService.updateDepartment(department);
        return "redirect:/department/edit?did=" + department.getId();
    }

    @PostMapping({"/delete"})
    public String delete(Model model, @Nullable @RequestParam("did") String id){
        if (id == null){
            return "redirect:/department/home";
        }
        boolean done = departmentService.deleteDepartment(id);
        return "redirect:/department/home";
    }
}
