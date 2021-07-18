package com.gcl.crm.controller;

import com.gcl.crm.entity.TradingAccount;
import com.gcl.crm.exporter.TradingAccountExcelExporter;
import com.gcl.crm.service.CustomerProcessService;
import com.gcl.crm.service.TradingAccountService;
import com.gcl.crm.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
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

    @GetMapping("/exportData")
    public void exportTradingAccount(HttpServletResponse response) throws IOException, ParseException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";

        String excelSheetName = "DSTK";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime nowTime = LocalDateTime.now();
        String now = dtf.format(nowTime)+"";

        String month ="Tháng "+now.charAt(5)+now.charAt(6)+"/"+now.charAt(0)+now.charAt(1)+now.charAt(2)+now.charAt(3);
        String title ="DANH SÁCH TÀI KHOẢN";
        String monthInput = now.charAt(5)+""+now.charAt(6)+"";
        String yearInput = now.charAt(0)+""+now.charAt(1)+""+now.charAt(2)+""+now.charAt(3)+"";
        String headerValue ="attachment;"+" filename="+"Tong ket tai khoan giao dich thang "+ monthInput +"/"+yearInput+".xlsx";

        response.setHeader(headerKey,headerValue);
        List<TradingAccount> tradingAccountList = tradingAccountService.findTradingAccountByMonthAndStatus(monthInput,"Active");
        List<TradingAccount> noneTradingAccountList = tradingAccountService.findTradingAccountByMonthAndStatus(monthInput,"Inactive");
        List<TradingAccount> blockTradingAccountList = tradingAccountService.findTradingAccountByMonthAndStatus(monthInput,"Block");
        List<TradingAccount> stopTradingAccountList = tradingAccountService.findTradingAccountByMonthAndStatus(monthInput,"Stop");
        TradingAccountExcelExporter excelExporter = new TradingAccountExcelExporter(title,month,excelSheetName,stopTradingAccountList,tradingAccountList,noneTradingAccountList,blockTradingAccountList);
        excelExporter.export(response);

    }
    @GetMapping({"/manageTradingAccount"})
    public  String viewTradingAccount(Model model){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime nowTime = LocalDateTime.now();
        String now = dtf.format(nowTime)+"";


        String monthInput = now.charAt(5)+""+now.charAt(6)+"";

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
        return "tradingAccount/view-tradingAccount-page";
    }
}
