package com.gcl.crm.controller;

import com.gcl.crm.entity.*;
import com.gcl.crm.enums.EmployeeStatus;
import com.gcl.crm.service.DepartmentService;
import com.gcl.crm.service.EmployeeService;
import com.gcl.crm.service.PositionService;
import com.gcl.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    PositionService positionService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String getHomePage(Model model) {
        List<Employee> employees = employeeService.getAllWorkingEmployees();
        List<Department> departments = departmentService.findAllDepartments();
        List<Position> positions = positionService.findAllPositions();
        model.addAttribute("employees", employees);
        model.addAttribute("departments", departments);
        model.addAttribute("positions", positions);
        return "employee/home-employee-page-V2";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String getInsertPage(Model model) {
        Employee employee = new Employee();
        employee.setAppUser(new AppUser());
        List<Department> departments = departmentService.findAllDepartments();
        List<Position> positions = positionService.findAllPositions();
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departments);
        model.addAttribute("positions", positions);
        return "employee/insert-employee-page-V2";
    }

    @PostMapping({"/create"})
    public String create(Model model, @Nullable @ModelAttribute("employee") Employee employee,
                         @Nullable @RequestParam("pid") Long pid,
                         @Nullable @RequestParam("did") Long did){
        if (employee == null){
            return "redirect:/employee/home";
        }
        boolean done = employeeService.createEmployee(employee, pid, did);
        if (done){
            model.addAttribute("message", "Tạo mới nhân viên thành công");
        } else {
            model.addAttribute("error", "Có lỗi xảy ra! Tạo mới thất bại");
        }
        return "redirect:/employee/create";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditPage(Model model, RedirectAttributes redirectAttributes,
                              @Nullable @RequestParam("eid") Long id) {
        if (id == null){
            redirectAttributes.addAttribute("error", "Không tìm thấy nhân viên được chọn");
            return "redirect:/employee/home";
        }
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null){
            redirectAttributes.addAttribute("error", "Không tìm thấy nhân viên được chọn");
            return "redirect:/employee/home";
        }
        List<Department> departments = departmentService.findAllDepartments();
        List<Position> positions = positionService.findAllPositions();
        model.addAttribute("departments", departments);
        model.addAttribute("positions", positions);
        model.addAttribute("employee", employee);
        return "employee/edit-employee-page-V2";
    }

    @PostMapping({"/edit"})
    public String edit(Model model, @Nullable @ModelAttribute("employee") Employee employee,
                       @Nullable @RequestParam("pid") Long pid,
                       @Nullable @RequestParam("did") Long did,
                       @Nullable @RequestParam("user-name") String username,
                       @Nullable @RequestParam("password") String password){
        if (employee == null){
            return "redirect:/employee/home";
        }
        boolean done = employeeService.updateEmployee(employee, pid, did, username, password);
        if (done){
            model.addAttribute("message", "Cập nhật nhân viên thành công");
        } else {
            model.addAttribute("error", "Đã có lỗi xảy ra! Cập nhật thất bại");
        }
        return "redirect:/employee/edit?eid=" + employee.getId();
    }

    @PostMapping({"/delete"})
    public String delete(Model model, @Nullable @RequestParam("eid") Long id){
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null || employee.getStatus().equals(EmployeeStatus.OFF_WORKING)){
            model.addAttribute("error", "Không tìm thấy nhân viên được chọn");
            return "redirect:/employee/home";
        }
        boolean done = employeeService.deleteEmployee(id);
        if (done){
            model.addAttribute("message", "Xóa nhân viên thành công");
        } else {
            model.addAttribute("error", "Đã có lỗi xảy ra! Xóa thất bại");
        }
        return "redirect:/employee/home";
    }
}
