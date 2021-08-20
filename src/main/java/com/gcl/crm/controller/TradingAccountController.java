package com.gcl.crm.controller;

import com.gcl.crm.entity.*;
import com.gcl.crm.enums.Status;
import com.gcl.crm.exporter.TradingAccountExcelExporter;
import com.gcl.crm.form.CustomerSearchForm;
import com.gcl.crm.form.TradingAccountForm;
import com.gcl.crm.form.TradingAccountSearchForm;
import com.gcl.crm.repository.TradingAccountRepository2;
import com.gcl.crm.service.CustomerProcessService;
import com.gcl.crm.service.TradingAccountService;
import com.gcl.crm.service.UserService;
import com.gcl.crm.utils.ExcelReader;
import com.gcl.crm.utils.WebUtils;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/tradingAccount")
public class TradingAccountController {
    @Autowired
    CustomerProcessService customerProcessService;

    @Autowired
    TradingAccountService tradingAccountService;

    @Autowired
    TradingAccountRepository2 tradingAccountRepository2;
    @Autowired
    UserService userService ;
    @GetMapping("/exportData")
    public void exportTradingAccount(HttpServletResponse response, Principal principal,Model model) throws IOException, ParseException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        User currentUser = userService.getUserByUsername(principal.getName());

        model.addAttribute("userInfo", currentUser);
        tradingAccountService.exportDateInMonth(response);
//        String excelSheetName = "DSTK";
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDateTime nowTime = LocalDateTime.now();
//        String now = dtf.format(nowTime)+"";
//
//        String month ="Tháng "+now.charAt(5)+now.charAt(6)+"/"+now.charAt(0)+now.charAt(1)+now.charAt(2)+now.charAt(3);
//        String title ="DANH SÁCH TÀI KHOẢN";
//        String monthInput = now.charAt(5)+""+now.charAt(6)+"";
//        String yearInput = now.charAt(0)+""+now.charAt(1)+""+now.charAt(2)+""+now.charAt(3)+"";
//        String headerValue ="attachment;"+" filename="+"Tong ket tai khoan giao dich thang "+ monthInput +"/"+yearInput+".xlsx";
//
//        response.setHeader(headerKey,headerValue);
//        List<TradingAccount> tradingAccountList = tradingAccountService.findTradingAccountByMonthAndStatus(monthInput,"Active");
//        List<TradingAccount> noneTradingAccountList = tradingAccountService.findTradingAccountByMonthAndStatus(monthInput,"Inactive");
//        List<TradingAccount> blockTradingAccountList = tradingAccountService.findTradingAccountByMonthAndStatus(monthInput,"Block");
//        List<TradingAccount> stopTradingAccountList = tradingAccountService.findTradingAccountByMonthAndStatus(monthInput,"Stop");
//        TradingAccountExcelExporter excelExporter = new TradingAccountExcelExporter(title,month,excelSheetName,stopTradingAccountList,tradingAccountList,noneTradingAccountList,blockTradingAccountList);
//        excelExporter.export(response);

    }
    @GetMapping({"/manageTradingAccountInMonth"})
    public  String viewTradingAccountUpdateInMonth(Model model,Principal principal){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime nowTime = LocalDateTime.now();
        String now = dtf.format(nowTime)+"";


        String monthInput = now.charAt(5)+""+now.charAt(6)+"";
        User currentUser = userService.getUserByUsername(principal.getName());

        model.addAttribute("userInfo", currentUser);
        List<TradingAccount> allTradingAccountList = tradingAccountService.findTradingAccountByMonth(monthInput);
        List<TradingAccount> tradingAccountList = tradingAccountService.findTradingAccountByMonthAndStatus(monthInput,"Active");
        List<TradingAccount> noneTradingAccountList = tradingAccountService.findTradingAccountByMonthAndStatus(monthInput,"Inactive");
        List<TradingAccount> blockTradingAccountList = tradingAccountService.findTradingAccountByMonthAndStatus(monthInput,"Block");
        List<TradingAccount> stopTradingAccountList = tradingAccountService.findTradingAccountByMonthAndStatus(monthInput,"Stop");


        model.addAttribute("tradingAccountList",tradingAccountList);
        model.addAttribute("noneTradingAccountList",noneTradingAccountList);
        model.addAttribute("blockTradingAccountList",blockTradingAccountList);
        model.addAttribute("stopTradingAccountList",stopTradingAccountList);

        model.addAttribute("allTradingAccountList",allTradingAccountList);

        List<TradingAccountForm> tradingAccountForms = tradingAccountRepository2.getTradingAccountByMonth("2021-07-17");
        return "tradingAccount/view-tradingAccount-page";
    }
    @GetMapping({"/manageTradingAccount"})
    public  String viewTradingAccount(Model model, Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(30);
        numberFormat.setGroupingUsed(true);
        TradingAccountSearchForm searchForm = new TradingAccountSearchForm();
        model.addAttribute("numberFormat", numberFormat);
        model.addAttribute("tradingAccountList",tradingAccountService.findAll());
        model.addAttribute("searchForm",searchForm);
        model.addAttribute("tradingAccountStopList",tradingAccountService.findAccountStopDeal());
        model.addAttribute("userInfo", currentUser);
        return "tradingAccount/manage-account-page";
    }
    @RequestMapping(value = "/searchBalanceAccount", method = RequestMethod.GET)
    public String search(Model model, @Nullable @ModelAttribute("searchForm") TradingAccountSearchForm searchForm, Principal principal){
        User currentUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("userInfo", currentUser);

        double balance  = 0 ;
        if (searchForm == null){
            return "redirect:/tradingAccount/manageTradingAccount";
        }
        try{

            List<TradingAccount> tradingAccountList = tradingAccountService.findAllByNameNumberBalance(searchForm);
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(30);
            numberFormat.setGroupingUsed(true);
            model.addAttribute("numberFormat", numberFormat);
            model.addAttribute("tradingAccountList",tradingAccountService.findAllByNameNumberBalance(searchForm));
            model.addAttribute("tradingAccountStopList",tradingAccountService.findAccountStopDeal());
            return "tradingAccount/manage-account-page";

        }catch (Exception e){
            if(e.getMessage().contains("Number")){
                    model.addAttribute("balance","xin hay nhap so");
                return "redirect:/tradingAccount/manageTradingAccount";

            }
        }


        return "tradingAccount/manage-account-page";
    }
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importExcelFile(RedirectAttributes redirectAttributes, @RequestParam("upload") MultipartFile file,
                                  Principal principal){
        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Tập tin trống");
            return "redirect:/tradingAccount/manageTradingAccount";
        }
        ExcelReader excelReader = new ExcelReader();
        try {
            List<TradingAccount>  tradingAccounts = excelReader.getTradingAccountsBalance(file.getInputStream(), file.getOriginalFilename());
            for(TradingAccount tradingAccount: tradingAccounts){
                tradingAccountService.updateAccountBalance(tradingAccount.getAccountNumber(),tradingAccount.getBalance());
            }


            redirectAttributes.addFlashAttribute("message", "Dữ liệu mới đã được lưu vào hệ thống");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Không thể mở tệp đã chọn");
        } catch (IllegalStateException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (NotOfficeXmlFileException e){
            redirectAttributes.addFlashAttribute("error", "Tập tin đã chọn không đúng định dạng");
        }
        return "redirect:/tradingAccount/manageTradingAccount";
    }
    @GetMapping({"/showUpdateBalance/{id}"})
    public String showUpdateAccountBalance(@PathVariable(name="id") String id,Model model,Principal principal) throws ParseException {
        TradingAccount account = tradingAccountService.findTradingAccountByID(id);
        if(account == null){
            return "redirect:/tradingAccount/manageTradingAccount";
        }
        User currentUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("tradingAccount",account);
        model.addAttribute("userInfo", currentUser);
        return "tradingAccount/update-account-balance-page";
    }
    @PostMapping({"/updateAccountBalance"})
    public String updateAccountBalance(@ModelAttribute("tradingAccount") TradingAccount tradingAccount) throws ParseException {
        if(tradingAccount == null){
            return "redirect:/tradingAccount/manageTradingAccount";

        }else{
            tradingAccountService.updateAccountBalance(tradingAccount.getAccountNumber(),tradingAccount.getBalance());
            return "redirect:/tradingAccount/manageTradingAccount";

        }



    }
}
