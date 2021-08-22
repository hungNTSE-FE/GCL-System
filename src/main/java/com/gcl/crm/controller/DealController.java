package com.gcl.crm.controller;

import com.gcl.crm.entity.Potential;
import com.gcl.crm.entity.Transaction;
import com.gcl.crm.entity.TransactionHistory;
import com.gcl.crm.entity.User;
import com.gcl.crm.service.PotentialService;
import com.gcl.crm.service.TransactionHistoryService;
import com.gcl.crm.service.UserService;
import com.gcl.crm.utils.ExcelReader;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/deal")
public class DealController {
    private static final String HOME_PAGE = "/deal/home-page";
    @Autowired
    UserService userService;
    @Autowired
    TransactionHistoryService transactionHistoryService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String goHomePage(Model model, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("userName", principal.getName());
        List<TransactionHistory> transactionHistoryList = transactionHistoryService.getAllTransactionHistory();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        model.addAttribute("formatter", dateFormat);
        model.addAttribute("transactionHistoryList",transactionHistoryList);
        model.addAttribute("userInfo", currentUser);
        return HOME_PAGE;
    }
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importExcelFile(RedirectAttributes redirectAttributes, @RequestParam("upload") MultipartFile file,
                                  Principal principal){
        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Tập tin trống");
            return "redirect:/deal/home";
        }
        ExcelReader excelReader = new ExcelReader();
        try {
          List<TransactionHistory>  transactionHistoryList = excelReader.getTransactionData(file.getInputStream(), file.getOriginalFilename());
          transactionHistoryService.importTransactionHistory(transactionHistoryList);


            redirectAttributes.addFlashAttribute("message", "Dữ liệu mới đã được lưu vào hệ thống");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Không thể mở tệp đã chọn");
        } catch (IllegalStateException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        } catch (NotOfficeXmlFileException e){
            redirectAttributes.addFlashAttribute("error", "Tập tin đã chọn không đúng định dạng");
        }
        return "redirect:/deal/home";
    }
}
