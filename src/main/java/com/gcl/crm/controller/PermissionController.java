package com.gcl.crm.controller;

import com.gcl.crm.entity.*;
import com.gcl.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    private static final String HOME_PAGE = "permission/home-permission-page-v2";
    private static final String CREATE_PAGE = "permission/create-permission-page-v2";
    private static final String EDIT_PAGE = "permission/edit-permission-page-v2";
    private static final String DECENENTRALIZE_PAGE = "permission/decentralization-permission-page-v2";

    @Autowired
    PrivilegeService privilegeService;

    @Autowired
    RoleService roleService;

    @Autowired
    ModuleService moduleService;

    @Autowired
    UserService userService;

    @Autowired
    EmployeeService employeeService;

    @GetMapping({"/home"})
    public String home(Model model, Principal principal) {
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("userName", principal.getName());
        return HOME_PAGE;
    }

    @GetMapping({"/create"})
    public String goCreatePage(Model model, Principal principal){
        Role role = new Role();
        List<Module> modules = this.getModules();
        model.addAttribute("role", role);
        model.addAttribute("modules", modules);
        model.addAttribute("userName", principal.getName());
        return CREATE_PAGE;
    }

    @GetMapping({"/edit"})
    public String goEditPage(Model model, @Nullable @RequestParam("rid") Long roleId, Principal principal){
        Role role = roleService.getRoleById(roleId);
        if (role == null){
            model.addAttribute("error", "Không tìm thấy nhóm quyền thích hợp");
            return "redirect:/permission/home";
        }
        List<Module> modules = this.getModules();
        model.addAttribute("modules", modules);
        model.addAttribute("role", role);
        model.addAttribute("userName", principal.getName());
        return EDIT_PAGE;
    }

    @GetMapping({"/decentralize"})
    public String goDecentralizePage(Model model, @Nullable @RequestParam("rid") Long roleId, Principal principal){
        if (roleId == null){
            return "redirect:/permission/home";
        }
        List<Employee> employees = employeeService.getAllWorkingEmployeesWithUserNotNull();
        Role role = roleService.getRoleById(roleId);
        List<Long> employeesOfRole = new ArrayList<>();
        for (UserRole userRole : role.getUserRoles()){
            employeesOfRole.add(userRole.getUser().getEmployee().getId());
        }
        model.addAttribute("employeesOfRole", employeesOfRole);
        model.addAttribute("employees", employees);
        model.addAttribute("role", role);
        model.addAttribute("userName", principal.getName());
        return DECENENTRALIZE_PAGE;
    }

    @PostMapping({"/edit"})
    public String edit(RedirectAttributes redirectAttributes, @Nullable @ModelAttribute("role") Role role,
                       @Nullable @RequestParam("privilege-id") List<Long> privilegeIdList,
                       Principal principal){
        if (role == null){
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy nhóm quyền thích hợp");
            return "redirect:/permission/home";
        }
        User currentUser = userService.getUserByUsername(principal.getName());
        boolean done = roleService.updateRole(role, privilegeIdList, currentUser);
        if (done){
            redirectAttributes.addFlashAttribute("message", "Cập nhật nhóm quyền thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Đã có lỗi xảy ra! Cập nhật thất bại.");
        }
        return "redirect:/permission/edit?rid=" + role.getId();
    }

    @PostMapping({"/create"})
    public String create( @Nullable @ModelAttribute("role") Role role,
                          @Nullable @RequestParam("privilege-id") List<Long> privilegeIdList,
                          RedirectAttributes redirectAttributes, Principal principal){
        if (role == null){
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy thông tin phòng ban");
            return "redirect:/permission/home";
        }
        User currentUser = userService.getUserByUsername(principal.getName());
        boolean done = roleService.createRole(role, privilegeIdList, currentUser);
        if (!done){
            return "redirect:/permission/home";
        }
        redirectAttributes.addFlashAttribute("flag","showAlert");
        return "redirect:/permission/create";
    }

    @PostMapping({"/delete"})
    public String delete(RedirectAttributes redirectAttributes, @Nullable @RequestParam("role-id") Long roleId, Principal principal){
        if (roleId == null){
            return "redirect:/permission/home";
        }
        User currentUser = userService.getUserByUsername(principal.getName());
        boolean done = roleService.deleteRole(roleId, currentUser);
        if (done){
            redirectAttributes.addFlashAttribute("message", "Xóa nhóm quyền thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Đã có lỗi xảy ra! Xóa nhóm quyền thất bại.");
        }
        return "redirect:/permission/home";
    }

    @PostMapping({"/decentralize"})
    public String decentralize(RedirectAttributes redirectAttributes,  @Nullable @RequestParam("role-id") Long roleId,
                               @Nullable @RequestParam("user-id") List<Long> userIdList,
                               Principal principal){
        if (roleId == null){
            return "redirect:/permission/home";
        }
        User currentUser = userService.getUserByUsername(principal.getName());
        boolean done = roleService.decentralizeRole(roleId, userIdList, currentUser);
        if (done){
            redirectAttributes.addFlashAttribute("message", "Phân quyền cho nhân viên thành công!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra! Phân quyền thất bại.");
        }
        return "redirect:/permission/decentralize?rid=" + roleId;
    }

    private List<Module> getModules(){
        List<Module> modules = moduleService.getAllModules();
        for (Module module : modules){
            module.setPrivileges(privilegeService.getPrivilegesByModule(module));
        }
        return modules;
    }
}
