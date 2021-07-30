package com.gcl.crm.controller;

import com.gcl.crm.entity.Potential;
import com.gcl.crm.entity.User;
import com.gcl.crm.service.PotentialService;
import com.gcl.crm.service.UserService;
import io.micrometer.core.lang.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/junk")
public class JunkController {

    @Autowired
    PotentialService potentialService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String goHomePage(Model model, Principal principal) {
        List<Potential> potentials = potentialService.getAllDeletedPotentials();
        model.addAttribute("potentials", potentials);
        model.addAttribute("userName", principal.getName());
        return "/junk/data-junk-page-hungNT";
    }

    @RequestMapping(value = "/reset/{id}", method = RequestMethod.GET)
    public String resetPotential(Model model, @Nullable @PathVariable("id") Long id,
                                 RedirectAttributes redirectAttributes, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        boolean done = potentialService.resetPotential(id, currentUser);
        if (done) {
            redirectAttributes.addFlashAttribute("flag","showAlert");
        }
        return "redirect:/junk/home";
    }

    @RequestMapping(value = "/resetAll", method = RequestMethod.POST)
    public String resetAllPotential(@Nullable @RequestParam("checkedbox") List<Long> checkedPotential,
                                    RedirectAttributes redirectAttributes, Principal principal) {
        if (checkedPotential == null) {
            redirectAttributes.addFlashAttribute("flag","showAlertError");
            return "redirect:/junk/home";
        }
        User currentUser = userService.getUserByUsername(principal.getName());
        boolean done = potentialService.resetAllPotential(checkedPotential, currentUser);
        redirectAttributes.addFlashAttribute("flag","showAlert");
        return "redirect:/junk/home";
    }
}
