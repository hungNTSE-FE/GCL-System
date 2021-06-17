package com.gcl.crm.controller;

import com.gcl.crm.entity.ActionType;
import com.gcl.crm.entity.Department;
import com.gcl.crm.entity.Permission;
import com.gcl.crm.service.ActionTypeService;
import com.gcl.crm.service.DepartmentService;
import com.gcl.crm.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @Autowired
    ActionTypeService actionTypeService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping({"/home"})
    public String home(Model model) {
        List<Permission> permissions = permissionService.findAllPermissions();
        model.addAttribute("permissions", permissions);
        return "permission/home-permission-page-v2";
    }

    @GetMapping({"/create"})
    public String goCreatePage(Model model){
        Permission permission = new Permission();
        List<ActionType> actionTypes = actionTypeService.findAllActionTypes();
        model.addAttribute("permission", permission);
        model.addAttribute("actionTypes", actionTypes);
        return "permission/create-permission-page-v2";
    }

    @PostMapping({"/create"})
    public String create(Model model, @Nullable @ModelAttribute("permission") Permission permission,
                         @Nullable @RequestParam("action-id") List<Long> aidList,
                         RedirectAttributes redirectAttributes
                         ){
        if (permission == null || permission.getId() == null){
            model.addAttribute("error", "Không tìm thấy thông tin phòng ban");
        }
        permissionService.createPermission(permission, aidList);
        redirectAttributes.addFlashAttribute("flag","showAlert");
        return "redirect:/permission/create";
    }

    @GetMapping({"/edit"})
    public String goEditPage(Model model, @Nullable @ModelAttribute("pid") String id){
        Permission permission = permissionService.findPermissionById(id);
        if (permission == null){
            model.addAttribute("error", "Không tìm thấy nhóm quyền thích hợp");
            return "redirect:/permission/home";
        }
        List<ActionType> actionTypes = actionTypeService.findAllActionTypes();
        model.addAttribute("actionTypes", actionTypes);
        model.addAttribute("permission", permission);
        return "permission/edit-permission-page-v2";
    }

    @PostMapping({"/edit"})
    public String edit(Model model, @Nullable @ModelAttribute("permission") Permission permission,
                       @Nullable @RequestParam("action-id") List<Long> aidList){
        if (permission == null){
            model.addAttribute("error", "Không tìm thấy nhóm quyền thích hợp");
            return "redirect:/permission/home";
        }
        boolean done = permissionService.updatePermission(permission, aidList);
        if (done){
            model.addAttribute("message", "Cập nhật nhóm quyền thành công!");
        } else {
            model.addAttribute("error", "Đã có lỗi xảy ra! Cập nhật thất bại.");
        }
        return "redirect:/permission/edit?pid=" + permission.getId();
    }

    @PostMapping({"/delete"})
    public String delete(Model model, @Nullable @RequestParam("pid") String pid){
        if (pid == null){
            return "redirect:/permission/home";
        }
        boolean done = permissionService.deletePermission(pid);
        if (done){
            model.addAttribute("message", "Xóa nhóm quyền thành công!");
        } else {
            model.addAttribute("error", "Đã có lỗi xảy ra! Xóa nhóm quyền thất bại.");
        }
        return "redirect:/permission/home";
    }

    @GetMapping({"/decentralize"})
    public String goDecentralizePage(Model model, @Nullable @RequestParam("pid") String pid){
        if (pid == null){
            return "redirect:/permission/home";
        }
        List<Department> departments = departmentService.findAllDepartments();
        Permission permission = permissionService.findPermissionById(pid);
        model.addAttribute("departments", departments);
        model.addAttribute("permission", permission);
        return "permission/decentralization-permission-page-v2";
    }

    @PostMapping({"/decentralize"})
    public String decentralize(Model model,  @Nullable @RequestParam("pid") String pid,
                               @Nullable @RequestParam("department-id") List<Long> didList){
        if (pid == null){
            return "redirect:/permission/home";
        }
        boolean done = permissionService.decentralizePermission(pid, didList);
        if (done){
            model.addAttribute("message", "Phân quyền cho phòng ban thành công!");
        } else {
            model.addAttribute("error", "Có lỗi xảy ra! Phân quyền thất bại.");
        }
        return "redirect:/permission/decentralize?pid=" + pid;
    }
}
