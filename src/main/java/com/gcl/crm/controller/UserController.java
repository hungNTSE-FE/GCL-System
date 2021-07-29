package com.gcl.crm.controller;

import com.gcl.crm.entity.User;
import com.gcl.crm.form.ChangePasswordForm;
import com.gcl.crm.service.UserService;
import com.gcl.crm.utils.EncryptedPasswordUtils;
import com.gcl.crm.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    private static final String CHANGE_PASS_PAGE = "/profile-user/change-password";
    private static final String ACCOUNT_PROFILE_PAGE = "/profile-user/account-profile";

    @Autowired
    UserService userService;

    @RequestMapping(value = "/change-password", method = RequestMethod.GET)
    public String goChangePass(Model model, Principal principal) {
        ChangePasswordForm changePasswordForm = new ChangePasswordForm();
        model.addAttribute("passwordForm", changePasswordForm);
        model.addAttribute("userName", principal.getName());
        return CHANGE_PASS_PAGE;
    }
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String goAccountProfile(Model model, Principal principal) {
        model.addAttribute("userName", principal.getName());
        return ACCOUNT_PROFILE_PAGE;
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    public String changePassword(RedirectAttributes redirectAttributes, Principal principal,
                                     @ModelAttribute("passwordForm") ChangePasswordForm passwordForm) {
        User currentUser = userService.getUserByUsername(principal.getName());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("notMatch", "Nhập lại mật khẩu không khớp");
            return "redirect:/user/change-password";
        }
        if (!encoder.matches(passwordForm.getOldPassword(), currentUser.getEncryptedPassword())){
            redirectAttributes.addFlashAttribute("wrongPass", "Mật khẩu cũ không chính xác");
            return "redirect:/user/change-password";
        }
        if (userService.changePassword(currentUser, passwordForm.getNewPassword())){
            redirectAttributes.addFlashAttribute("flag", "showAlert");
        }
        return "redirect:/user/change-password";
    }
}
