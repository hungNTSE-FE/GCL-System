package com.gcl.crm.controller;

import com.gcl.crm.entity.User;
import com.gcl.crm.entity.TMP_KPI_EMPLOYEE;
import com.gcl.crm.entity.WKCustomer;
import com.gcl.crm.form.*;
import com.gcl.crm.service.MarketingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/marketing")
public class MarketingController {

    public static final String MAIN_PAGE = "marketing/marketing.html";
    public static final String CUSTOMER_REPORT_PAGE = "marketing/customerStatusReport.html";
    public static final String CUSTOMER_DISTRIBUTION_MNG_PAGE = "marketing/customerDistributionManagement.html";
    public static final String EMPLOYEE_KPI_EVALUATION_REPORT_PAGE = "marketing/employeeKPIReport.html";
    public static final String APP_USER = "users";
    public static final String LIST_WK_CUSTOMER = "wkCustomerList";


    @Autowired
    MarketingServices maketingServices;

    @RequestMapping(value = "/")
    public String initMarketingScreen(Model model) {
        maketingServices.initScreen();
        return MAIN_PAGE;
    }

    @RequestMapping(value = "/customerStatusReport")
    public String initScreenCustomerStatusReport() {
        return CUSTOMER_REPORT_PAGE;
    }

    @GetMapping(value = "/getListCustomerStatusReport")
    @ResponseBody
    public ResponseEntity<CustomerStatusReportForm> getCustomerStatusReport(@RequestParam String fromDate,
                                                                            @RequestParam String toDate) {
        CustomerStatusReportForm customerStatusReportForm = maketingServices.
                getCustomerStatusReport(fromDate, toDate);
        return new ResponseEntity<>(customerStatusReportForm, HttpStatus.OK);
    }

    @GetMapping(value = "/manageDataCustomer")
    public String initCustomerDistributionManagementScreen(Model model, @ModelAttribute CustomerForm customerForm) {
        List<User> users = maketingServices.getListUser();
        List<WKCustomer> wkCustomerList = maketingServices.getAllWkCustomer();
        ComboboxForm comboboxForm = maketingServices.initComboboxData();
        customerForm.setComboboxForm(comboboxForm);
        model.addAttribute(APP_USER, users);
        model.addAttribute(LIST_WK_CUSTOMER, wkCustomerList);
        return CUSTOMER_DISTRIBUTION_MNG_PAGE;
    }

    @GetMapping(value = "/employeeKPIEvaluation")
    public String initEmployeeKPIEvaluation(Model model, @ModelAttribute KPIEmployeeForm kpiEmployeeForm) {
        String startDate = kpiEmployeeForm.getStartDate();
        String endDate = kpiEmployeeForm.getEndDate();
        List<TMP_KPI_EMPLOYEE> tmp_kpi_employeeList = maketingServices.getKPIEmployeeReport(startDate, endDate);
        kpiEmployeeForm.setTmpKpiEmployeeList(tmp_kpi_employeeList);
        model.addAttribute("KPI_EMPLOYEE_EVALUATION" , kpiEmployeeForm);
        return EMPLOYEE_KPI_EVALUATION_REPORT_PAGE;
    }

    @PostMapping(value = "/distributionPotential")
    public String distributionPotential(@ModelAttribute CustomerDistributionForm customerDistributionForm) {
        maketingServices.distributeCustomerData(customerDistributionForm);
        return "redirect:/potential/home";
    }

}
