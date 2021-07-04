package com.gcl.crm.controller;

import com.gcl.crm.entity.AppUser;
import com.gcl.crm.entity.WKCustomer;
import com.gcl.crm.form.ComboboxForm;
import com.gcl.crm.form.CustomerForm;
import com.gcl.crm.form.CustomerStatusReportForm;
import com.gcl.crm.repository.UserRepository2;
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
    public static final String APP_USER = "appUsers";
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
        List<AppUser> appUsers = maketingServices.getListUser();
        List<WKCustomer> wkCustomerList = maketingServices.getAllWkCustomer();
        ComboboxForm comboboxForm = maketingServices.initComboboxData();
        customerForm.setComboboxForm(comboboxForm);
        model.addAttribute(APP_USER, appUsers);
        model.addAttribute(LIST_WK_CUSTOMER, wkCustomerList);
        return CUSTOMER_DISTRIBUTION_MNG_PAGE;
    }
}
