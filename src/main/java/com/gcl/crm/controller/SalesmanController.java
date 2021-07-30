package com.gcl.crm.controller;

import com.gcl.crm.entity.*;
import com.gcl.crm.form.CustomerDistributionForm;
import com.gcl.crm.form.PotentialSearchForm;
import com.gcl.crm.repository.SourceRepository;
import com.gcl.crm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/salesman")
public class SalesmanController {
    private static final String DASHBOARD_PAGE = "/potential/sale/home-potential-salesman";
    private static final String DETAIL_INFORMATION_PAGE = "/potential/sale/detail-potential-information-salesman";
    @Autowired
    PotentialService potentialService;

    @Autowired
    DiaryService diaryService;

    @Autowired
    SourceRepository sourceRepository;

    @Autowired
    LevelService levelService;

    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/potential/home", method = RequestMethod.GET)
    public String goHomePage(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        List<Source> sources = sourceRepository.getAll();
        List<Level> levels = levelService.getAll();
        List<Potential> potentials = potentialService.getAllPotentialsOfSale(user);
        PotentialSearchForm searchForm = new PotentialSearchForm();

        model.addAttribute("potentials", potentials);
        model.addAttribute("sources", sources);
        model.addAttribute("levels", levels);
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("userName", principal.getName());

        return DASHBOARD_PAGE;
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String goDetailInformationCustomer(Model model, @PathVariable("id") Long id) {
        Potential potentialDetail = potentialService.getPotentialById(id);
        Potential potentialEntity = new Potential();
        if (potentialDetail == null) {
            return "redirect:/potential/home";
        }
        model.addAttribute("potentialDetail", potentialDetail);
        model.addAttribute("levels", levelService.getAll());
        model.addAttribute("selectedLevel", potentialDetail.getLevel());
        model.addAttribute("potentialEntity", potentialEntity);
        return DETAIL_INFORMATION_PAGE;
    }
}
