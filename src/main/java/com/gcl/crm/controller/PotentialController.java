package com.gcl.crm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcl.crm.entity.*;
import com.gcl.crm.form.CustomerDistributionForm;
import com.gcl.crm.form.EmployeeSearchForm;
import com.gcl.crm.form.PotentialSearchForm;
import com.gcl.crm.repository.SourceRepository;
import com.gcl.crm.service.*;
import com.gcl.crm.utils.ExcelReader;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TreeMap;

@Controller
@RequestMapping("/potential")
public class PotentialController {
    private static final String DASHBOARD_PAGE = "/potential/home-potential-hungNT-V2";
    private static final String CREATE_PAGE = "/potential/create-potential-hungNT-V2";
    private static final String UPDATE_PAGE = "/potential/edit-potential-hungNT-V2";
    private static final String ERROR_400 = "/error/error-400";
    private static final String DETAIL_OVERVIEW_PAGE = "/potential/details/detail-potential-overview-page-V2";
    private static final String DETAIL_INFORMATION_PAGE = "/potential/details/detail-potential-information-page-V2";
    private static final String DETAIL_ACTION_PAGE = "/potential/details/detail-potential-action-page-V2";
    private static final String DETAIL_TAKECARE_PAGE = "/potential/details/detail-potential-takecare-page";
    private static final String DETAIL_TAKECARE_MKTPAGE = "/potential/details/marketing/detail-potential-takecare-MKTpage";
    private static final String DETAIL_DIARY_PAGE = "/potential/details/detail-potential-diary-page";

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

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String goHomePage(Model model, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        List<Source> sources = sourceRepository.getAll();
        List<Level> levels = levelService.getAll();
        List<Potential> potentials = potentialService.getAllPotentials();
        List<Department> departments = departmentService.findAllDepartments();
        List<Employee> employees = employeeService.getAllWorkingEmployees();
        PotentialSearchForm searchForm = new PotentialSearchForm();
        CustomerDistributionForm customerDistributionForm = new CustomerDistributionForm();
        model.addAttribute("departments", departments);
        model.addAttribute("sources", sources);
        model.addAttribute("levels", levels);
        model.addAttribute("potentials", potentials);
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("employees", employees);
        model.addAttribute("customerDistributionForm", customerDistributionForm);
        model.addAttribute("userName", principal.getName());
        model.addAttribute("userInfo", currentUser);
        return DASHBOARD_PAGE;
    }

    @RequestMapping(value = "/detail/overview/{id}", method = RequestMethod.GET)
    public String goPotentialDetail(Model model, @PathVariable("id") Long id) {
        Potential potential = potentialService.getPotentialById(id);
        if (potential == null) {
            return "redirect:/potential/home";
        }
        model.addAttribute("potentialDetail", potential);
        return DETAIL_OVERVIEW_PAGE;
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String goDetailInformationCustomer(Model model, @PathVariable("id") Long id, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        Potential potentialDetail = potentialService.getPotentialById(id);
        Potential potentialEntity = new Potential();
        if (potentialDetail == null) {
            return "redirect:/potential/home";
        }
        model.addAttribute("potentialDetail", potentialDetail);
        model.addAttribute("levels", levelService.getAll());
        model.addAttribute("selectedLevel", potentialDetail.getLevel());
        model.addAttribute("potentialEntity", potentialEntity);
        model.addAttribute("userName", principal.getName());
        model.addAttribute("userInfo", currentUser);
        return DETAIL_INFORMATION_PAGE;
    }

    @RequestMapping(value = "/detail/takecare/MKT/{id}", method = RequestMethod.GET)
    public String goDetailTakeCarePotentialOfMKT(Model model, @PathVariable("id") Long id, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        Potential potentialDetail = potentialService.getPotentialById(id);
        if (potentialDetail == null) {
            return "redirect:/potential/home";
        }
        model.addAttribute("levels", levelService.getAll());
        model.addAttribute("selectedLevel", potentialDetail.getLevel());
        model.addAttribute("potentialDetail", potentialDetail);
        model.addAttribute("userName", principal.getName());
        model.addAttribute("userInfo", currentUser);
        return DETAIL_TAKECARE_MKTPAGE;
    }

    @RequestMapping(value = "/detail/diary/{id}", method = RequestMethod.GET)
    public String goDetailDiary(Model model, @PathVariable("id") Long potentialId, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        Potential potential = potentialService.getPotentialById(potentialId);
        if (potential == null) {
            return "redirect:/potential/home";
        }
        TreeMap<Diary, Employee> diaries = new TreeMap<>(diaryService.getDiaryMap(potentialId));
        model.addAttribute("diaries", diaries);
        model.addAttribute("levels", levelService.getAll());
        model.addAttribute("selectedLevel", potential.getLevel());
        model.addAttribute("potentialDetail", potential);
        model.addAttribute("userName", principal.getName());
        model.addAttribute("userInfo", currentUser);
        return DETAIL_DIARY_PAGE;
    }

    @RequestMapping(value = "/action/id1", method = RequestMethod.GET)
    public String goDetailActionCustomer(Model model) {
        return DETAIL_ACTION_PAGE;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(Model model, @Nullable @ModelAttribute("searchForm") PotentialSearchForm searchForm){
        if (searchForm == null){
            return "redirect:/potential/home";
        }
        CustomerDistributionForm customerDistributionForm = new CustomerDistributionForm();
        List<Source> sources = sourceRepository.getAll();
        List<Level> levels = levelService.getAll();
        List<Potential> potentials = potentialService.search(searchForm);
        model.addAttribute("sources", sources);
        model.addAttribute("levels", levels);
        model.addAttribute("potentials", potentials);
        model.addAttribute("customerDistributionForm", customerDistributionForm);
        return DASHBOARD_PAGE;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String goCreatePage(Model model, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        List<Source> sources = sourceRepository.getAll();
        Potential potential = new Potential();
        model.addAttribute("potentialForm", potential);
        model.addAttribute("sources", sources);
        model.addAttribute("userName", principal.getName());
        model.addAttribute("userInfo", currentUser);
        return CREATE_PAGE;
    }

    @RequestMapping(value = "/detail/edit/{id}", method = RequestMethod.GET)
    public String goEditPage(Model model, Principal principal, @Nullable @PathVariable("id") Long id) {
        User currentUser = userService.getUserByUsername(principal.getName());
        if (principal == null) {
            return ERROR_400;
        }
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
        model.addAttribute("userName", principal.getName());
        model.addAttribute("userInfo", currentUser);
        return UPDATE_PAGE;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Model model, @ModelAttribute("potentialForm") Potential potential, RedirectAttributes redirectAttributes, Principal principal) {
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
            return CREATE_PAGE;
        }
        User currentUser = userService.getUserByUsername(principal.getName());
        boolean done = potentialService.createPotential(potential, currentUser);
        redirectAttributes.addFlashAttribute("flag","showAlert");
        return "redirect:/potential/create";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(Model model, RedirectAttributes redirectAttributes,
                       @Nullable @ModelAttribute("potential") Potential potential,
                       Principal principal
    ) {
        User user = userService.getUserByUsername(principal.getName());
        boolean error = false;

        if (potential == null){
            return "redirect:/potential/home";
        }
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
            return UPDATE_PAGE;
        }
        boolean done = potentialService.editPotential(potential, user);
        if (!done){
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy đầu mối");
            return "redirect:/potential/home";
        }
        redirectAttributes.addFlashAttribute("flag","showAlertUpdateSuccessful");
        return "redirect:/potential/detail/edit/" + potential.getId();
    }

    @RequestMapping(value = "/detail/edit/level/MKT/{id}", method = RequestMethod.POST)
    public String editLevel(@Nullable @RequestParam("level") int levelId,
                            @Nullable @PathVariable("id") Long pid,
                            RedirectAttributes redirectAttributes, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        boolean done = potentialService.editLevelPotential(pid, levelId, currentUser);
        redirectAttributes.addFlashAttribute("flag","showAlertUpdateLevelSuccessful");
        return "redirect:/potential/detail/takecare/MKT/" + pid;
    }

    @RequestMapping(value = "/remove",  method = RequestMethod.POST)
    public String remove(Model model, RedirectAttributes redirectAttributes,
                         @Nullable @RequestParam("potential-id") List<Long> idList,
                         Principal principal) {
        if (idList == null){
            redirectAttributes.addFlashAttribute("flag","showAlertError");
            return "redirect:/potential/home";
        }
        User currentUser = userService.getUserByUsername(principal.getName());
        potentialService.removePotentials(idList, currentUser);
        redirectAttributes.addFlashAttribute("flag","showAlert");
        return "redirect:/potential/home";
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importExcelFile(RedirectAttributes redirectAttributes, @RequestParam("upload") MultipartFile file,
                                  Principal principal){
        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Tập tin trống");
            return "redirect:/potential/home";
        }
        ExcelReader excelReader = new ExcelReader();
        try {
            List<Potential> potentialData = excelReader.getPotentialData(file.getInputStream(), file.getOriginalFilename());
            User currentUser = userService.getUserByUsername(principal.getName());
            potentialService.importPotential(potentialData, currentUser);
            redirectAttributes.addFlashAttribute("message", "Dữ liệu mới đã được lưu vào hệ thống");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Không thể mở tệp đã chọn");
        } catch (IllegalStateException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (NotOfficeXmlFileException e){
            redirectAttributes.addFlashAttribute("error", "Tập tin đã chọn không đúng định dạng");
        }
        return "redirect:/potential/home";
    }

    @PostMapping(value = {"/detail/take-care/MKT/{id}"})
    public String markTakeCareAsSeen(@PathVariable("id") Long id, Principal principal,
                                     @RequestParam("index") Integer index){
        Potential potential = potentialService.getPotentialById(id);
        User currentUser = userService.getUserByUsername(principal.getName());
        boolean done = potentialService.acceptTakeCareInfo(potential, currentUser, index);
        return "redirect:/potential/detail/takecare/MKT/" + id;
    }

    @PostMapping(value = {"/detail/take-care/{id}"})
    public String takeCare(@PathVariable("id") Long pid,
                           Principal principal,
                           @Nullable @RequestParam("description") String description,
                           @RequestParam("index") Integer index,
                           RedirectAttributes redirectAttributes){
        Potential potential = potentialService.getPotentialById(pid);
        if (potential == null){
            return "redirect:/potential/home";
        }
        if (index > 3){
            return "redirect:/potential/detail/takecare/" + pid;
        }
        User currentUser = userService.getUserByUsername(principal.getName());
        boolean done = potentialService.addTakeCarePotentialDetail(potential, currentUser, description);
        redirectAttributes.addFlashAttribute("flag","showAlert");
        return "redirect:/potential/detail/takecare/" + pid;
    }

    @PostMapping(value = "/getPotentialToShare")
    public ResponseEntity<CustomerDistributionForm> getPotentialToShare(@RequestBody String ids) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Long> listSelectedId = mapper.readValue(ids,
                mapper.getTypeFactory().constructCollectionType(List.class, Long.class));
        List<PotentialSearchForm> potentialsSharing = potentialService.getListPotentialToShare(listSelectedId);
        CustomerDistributionForm customerDistributionForm = new CustomerDistributionForm();
        customerDistributionForm.setPotentialSearchFormList(potentialsSharing);
        return new ResponseEntity<>(customerDistributionForm, HttpStatus.OK);
    }

    @PostMapping(value = "/getEmployeeByDepartmentId")
    public ResponseEntity<List<EmployeeSearchForm>> getEmployeeByDepartmentId(@RequestBody String id) throws JsonProcessingException {
        List<EmployeeSearchForm> employeeList = null;
        try {
            employeeList = potentialService.getEmployeeByDepartmentId(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    //   SALE
    @RequestMapping(value = "/detail/sale/{id}", method = RequestMethod.GET)
    public String goDetailInformationCustomerSale(Model model, @PathVariable("id") Long id, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        Potential potentialDetail = potentialService.getPotentialById(id);
        Potential potentialEntity = new Potential();
        if (potentialDetail == null) {
            return "redirect:/potential/home";
        }
        model.addAttribute("potentialDetail", potentialDetail);
        model.addAttribute("levels", levelService.getAll());
        model.addAttribute("selectedLevel", potentialDetail.getLevel());
        model.addAttribute("potentialEntity", potentialEntity);
        model.addAttribute("userName", principal.getName());
        model.addAttribute("userInfo", currentUser);
        return "/potential/sale/detail-potential-information-salesman";
    }

    @RequestMapping(value = "/detail/diary/sale/{id}", method = RequestMethod.GET)
    public String goDetailDiarySalesman(Model model, @PathVariable("id") Long potentialId, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        Potential potential = potentialService.getPotentialById(potentialId);
        if (potential == null) {
            return "redirect:/potential/home";
        }
        TreeMap<Diary, Employee> diaries = new TreeMap<>(diaryService.getDiaryMap(potentialId));
        model.addAttribute("diaries", diaries);
        model.addAttribute("levels", levelService.getAll());
        model.addAttribute("selectedLevel", potential.getLevel());
        model.addAttribute("potentialDetail", potential);
        model.addAttribute("userName", principal.getName());
        model.addAttribute("userInfo", currentUser);
        return "/potential/sale/detail-potential-diary-salesman";
    }

    @RequestMapping(value = "/detail/takecare/{id}", method = RequestMethod.GET)
    public String goDetailTakeCarePotential(Model model, @PathVariable("id") Long id, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        Potential potential = potentialService.getPotentialById(id);
        if (potential == null){
            return "redirect:/potential/home";
        }
        model.addAttribute("levels", levelService.getAll());
        model.addAttribute("selectedLevel", potential.getLevel());
        model.addAttribute("potentialDetail", potential);
        model.addAttribute("userName", principal.getName());
        model.addAttribute("userInfo", currentUser);
        return "/potential/sale/detail-potential-takecare-page";
    }
    //END SALE
}
