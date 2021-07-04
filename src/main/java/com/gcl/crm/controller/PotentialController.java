package com.gcl.crm.controller;

import com.gcl.crm.entity.Employee;
import com.gcl.crm.entity.Level;
import com.gcl.crm.entity.Potential;
import com.gcl.crm.entity.Source;
import com.gcl.crm.form.CustomerDistributionForm;
import com.gcl.crm.form.PotentialSearchForm;
import com.gcl.crm.repository.SourceRepository;
import com.gcl.crm.service.EmployeeService;
import com.gcl.crm.service.LevelService;
import com.gcl.crm.service.PotentialService;
import com.gcl.crm.utils.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/potential")
public class PotentialController {

    @Autowired
    PotentialService potentialService;

    @Autowired
    SourceRepository sourceRepository;

    @Autowired
    LevelService levelService;

    @Autowired
    EmployeeService employeeService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String goHomePage(Model model) {
        List<Source> sources = sourceRepository.getAll();
        List<Level> levels = levelService.getAll();
        List<Potential> potentials = potentialService.getAllPotentials();
        List<Employee> employees = employeeService.getAllWorkingEmployees();
        List<Potential> potentialsSharing = potentialService.getListPotentialToShare();
        PotentialSearchForm searchForm = new PotentialSearchForm();
        CustomerDistributionForm customerDistributionForm = new CustomerDistributionForm();
        model.addAttribute("sources", sources);
        model.addAttribute("levels", levels);
        model.addAttribute("potentials", potentials);
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("employees", employees);
        model.addAttribute("potentialsSharing", potentialsSharing);
        model.addAttribute("customerDistributionForm", customerDistributionForm);
        return "/potential/home-potential-hungNT-V2";
    }

    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    public String goPotentialDetail(Model model, @PathVariable("id") Long id) {
        return "/potential/details/detail-customer-overview-page-V2";
    }

    @RequestMapping(value = "/{id}/information", method = RequestMethod.GET)
    public String goDetailInformationCustomer(Model model) {
        return "/potential/details/detail-customer-information-page-V2";
    }

    @RequestMapping(value = "/email/id1", method = RequestMethod.GET)
    public String goDetailEmailCustomer(Model model) {
        return "/potential/details/detail-customer-email-page-V2";
    }

    @RequestMapping(value = "/action/id1", method = RequestMethod.GET)
    public String goDetailActionCustomer(Model model) {
        return "/potential/details/detail-customer-action-page-V2";
    }

    @GetMapping({"/search"})
    public String search(Model model, @Nullable @ModelAttribute("searchForm") PotentialSearchForm searchForm){
        if (searchForm == null){
            return "redirect:/potential/home";
        }
        List<Source> sources = sourceRepository.getAll();
        List<Level> levels = levelService.getAll();
        List<Potential> potentials = potentialService.search(searchForm);
        model.addAttribute("sources", sources);
        model.addAttribute("levels", levels);
        model.addAttribute("potentials", potentials);
        return "/potential/home-potential-hungNT-V2";
    }

    @PostMapping({"/import"})
    public String importExcelFile(Model model, @RequestParam("upload") MultipartFile file){
        ExcelReader excelReader = new ExcelReader();
        try {
            List<Potential> potentialData = excelReader.getPotentialData(file.getInputStream(), file.getOriginalFilename());
            potentialService.importPotential(potentialData);
            model.addAttribute("message", "Dữ liệu mới đã được lưu vào hệ thống");
        } catch (IOException e) {
            model.addAttribute("error", "Không thể mở tệp đã chọn");
        } catch (IllegalStateException e){
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/potential/home";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String goCreatePage(Model model) {
        List<Source> sources = sourceRepository.getAll();
        Potential potential = new Potential();
        model.addAttribute("potentialForm", potential);
        model.addAttribute("sources", sources);
        return "/potential/create-potential-hungNT-V2";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Model model, @ModelAttribute("potentialForm") Potential potential, RedirectAttributes redirectAttributes) {
        boolean error = false;
        if (potentialService.isPhoneExisted(potential.getPhoneNumber(), potential.getId())){
            model.addAttribute("duplicatePhone", "Số điện thoại này đã tồn tại");
            error = true;
        }
        if (potentialService.isEmailExisted(potential.getEmail(), potential.getId())){
            model.addAttribute("duplicateEmail", "Email này đã tồn tại");
            error = true;
        }
        if (error){
            List<Source> sources = sourceRepository.getAll();
            model.addAttribute("sources", sources);
            return "/potential/create-potential-hungNT-V2";
        }
        potential.setDate(getCurrentDate());
        boolean done = potentialService.createPotential(potential);
        redirectAttributes.addFlashAttribute("flag","showAlert");
        return "redirect:/potential/create";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String goEditPage(Model model, @Nullable @PathVariable("id") Long id) {
        if (id == null){
            return "redirect:/potential/home";
        }
        Potential potential = potentialService.getPotentialById(id);
        if (potential == null){
            return "redirect:/potential/home";
        }
        if (potential.getSource() == null){
            potential.setSource(new Source());
        }
        List<Source> sources = sourceRepository.getAll();
        model.addAttribute("potential", potential);
        model.addAttribute("sources", sources);
        return "/potential/edit-potential-hungNT-V2";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(Model model, RedirectAttributes redirectAttributes,
                       @Nullable @ModelAttribute("potential") Potential potential) {
        if (potential == null){
            return "redirect:/potential/home";
        }
        boolean error = false;
        if (potentialService.isPhoneExisted(potential.getPhoneNumber(), potential.getId())){
            model.addAttribute("duplicatePhone", "Số điện thoại này đã tồn tại");
            error = true;
        }
        if (potentialService.isEmailExisted(potential.getEmail(), potential.getId())){
            model.addAttribute("duplicateEmail", "Email này đã tồn tại");
            error = true;
        }
        if (error){
            List<Source> sources = sourceRepository.getAll();
            model.addAttribute("sources", sources);
            return "/potential/edit-potential-hungNT-V2";
        }
        boolean done = potentialService.editPotential(potential);
        if (!done){
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy đầu mối");
            return "redirect:/potential/home";
        }
        redirectAttributes.addFlashAttribute("flag","showAlert");
        return "redirect:/potential/" + potential.getId() + "/edit";
    }

    @RequestMapping(value = "/remove",  method = RequestMethod.POST)
    public String remove(Model model, RedirectAttributes redirectAttributes, @Nullable @RequestParam("pid") List<Long> idList) {
        if (idList == null){
            return "redirect:/potential/home";
        }
        potentialService.removePotentials(idList);
        redirectAttributes.addFlashAttribute("flag","showAlert");
        return "redirect:/potential/home";
    }

    private String getCurrentDate() {
        String newYorkDateTimePattern = "dd/MM/yyyy";
        DateTimeFormatter newYorkDateFormatter = DateTimeFormatter.ofPattern(newYorkDateTimePattern);
        String formattedDate = newYorkDateFormatter.format(LocalDate.now());
        return formattedDate;
    }
}
