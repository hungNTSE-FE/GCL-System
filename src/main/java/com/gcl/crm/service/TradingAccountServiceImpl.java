package com.gcl.crm.service;

import com.gcl.crm.entity.TradingAccount;
import com.gcl.crm.exporter.TradingAccountExcelExporter;
import com.gcl.crm.form.TradingAccountSearchForm;
import com.gcl.crm.repository.TradingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TradingAccountServiceImpl implements TradingAccountService{

    @Autowired
    private TradingAccountRepository tradingAccountRepository;

    @Override
    public List<TradingAccount> findTradingAccountByMonth(String month) {
        List<TradingAccount> tradingAccountList = tradingAccountRepository.findAll();
        List<TradingAccount> result = new ArrayList<>();
        if(month.length()==1){
            month = "0"+month;
        }
        String createDate = "";
        for(int i = 0 ;i <tradingAccountList.size();i++){
            createDate =tradingAccountList.get(i).getCreateDate()+"";

            if(createDate.charAt(5)==month.charAt(0) &&
                    createDate.charAt(6)==month.charAt(1)){
                result.add(tradingAccountList.get(i));

            }

        }

        return result;
    }

    @Override
    public TradingAccount findTradingAccountByID(String id) {
        List<TradingAccount> tradingAccountList =tradingAccountRepository.findAll();
        for(int i = 0 ; i <tradingAccountList.size();i++){
            if(tradingAccountList.get(i).getAccountNumber().equals(id)){
                return tradingAccountList.get(i);
            }
        }
        return null ;
    }

    @Override
    public void exportTradingAccountByMonth(List<TradingAccount> tradingAccountList) {

    }

    @Override
    public List<TradingAccount> findAll() {
        return tradingAccountRepository.findAll();
    }

    @Override
    public List<TradingAccount> findAllByActive() {
        return tradingAccountRepository.findAllByActive("Block");
    }

    @Override
    public List<TradingAccount> findAccountStopDeal() {
        return tradingAccountRepository.findAllByBalance(5000000,-1);
    }

    @Override
    public TradingAccount activateAccount(TradingAccount tradingAccount) {
        tradingAccount.setStatus("Active");
        tradingAccount.setUpdateType("Active");
        tradingAccount.setUpdateDate(Date.valueOf(LocalDate.now()));
        tradingAccount.setActiveDate(Date.valueOf(LocalDate.now()));
        System.out.println(tradingAccount.getActiveDate());
       return tradingAccount;
    }

    @Override
    public void updateAccountBalance(String accountNumber, double balance) {
    Optional<TradingAccount> tradingAccount = tradingAccountRepository.findById(accountNumber);

    if(tradingAccount.isPresent()){
        tradingAccount.get().setBalance(balance);
        tradingAccountRepository.save(tradingAccount.get());
    }
    }


    @Override
    public List<TradingAccount> findTradingAccountByMonthAndStatus(String month,String status) {
        List<TradingAccount> result = new ArrayList<>();
        List<TradingAccount> tradingAccountList = tradingAccountRepository.findAll();

        if(month.length()==1){
            month = "0"+month;
        }
        String updateDate = "";
        for(int i = 0 ;i <tradingAccountList.size();i++){
            if(tradingAccountList.get(i).getUpdateDate() != null){
                updateDate =tradingAccountList.get(i).getUpdateDate()+"";

                if(updateDate.charAt(5)==month.charAt(0) &&
                        updateDate.charAt(6)==month.charAt(1)){
                    if(tradingAccountList.get(i).getStatus().equals(status)){
                        result.add(tradingAccountList.get(i));

                    }

                }
            }else{

            }


        }

        return result;
    }

    @Override
    public List<TradingAccount> findAllByNameNumberBalance(TradingAccountSearchForm tradingAccountSearchForm) {
        List<TradingAccount> result = new ArrayList<>();
        List<TradingAccount> tradingAccounts =  tradingAccountRepository.findAllByNameNumberBalance(tradingAccountSearchForm.getName().trim(),tradingAccountSearchForm.getNumber().trim());
        if(!tradingAccountSearchForm.getMin().equals("")  & !tradingAccountSearchForm.getMax().equals("")){
            double min = Double.parseDouble(tradingAccountSearchForm.getMin());
            double max = Double.parseDouble(tradingAccountSearchForm.getMax());
            for(TradingAccount tradingAccount : tradingAccounts){
                if(tradingAccount.getBalance() >= min && tradingAccount.getBalance()<= max){
                    result.add(tradingAccount);
                }
            }
            return result ;
        }else if(tradingAccountSearchForm.getMin().equals("") && tradingAccountSearchForm.getMax().equals("")){
            return tradingAccounts ;
        }else if(!tradingAccountSearchForm.getMin().equals("") && tradingAccountSearchForm.getMax().equals("") ){
            double min = Double.parseDouble(tradingAccountSearchForm.getMin());

            for(TradingAccount tradingAccount : tradingAccounts){
                if(tradingAccount.getBalance() >= min ){
                    result.add(tradingAccount);
                }
            }
            return result ;
        }else if(tradingAccountSearchForm.getMin().equals("") && !tradingAccountSearchForm.getMax().equals("") ){
            double max = Double.parseDouble(tradingAccountSearchForm.getMax());

            for(TradingAccount tradingAccount : tradingAccounts){
                if(tradingAccount.getBalance() <= max ){
                    result.add(tradingAccount);
                }
            }
            return result ;
        }

        return  result;

    }

    @Override
    public TradingAccount createTradingAccount(TradingAccount tradingAccount) {
        tradingAccount.setCreateDate(Date.valueOf(LocalDate.now()));
        tradingAccount.setUpdateDate(Date.valueOf(LocalDate.now()));
        tradingAccount.setUpdateType("Inactive");
        return tradingAccount ;
    }

    @Override
    public void exportDateInMonth(HttpServletResponse response) throws IOException {
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
        List<TradingAccount> tradingAccountList = this.findTradingAccountByMonthAndStatus(monthInput,"Active");
        List<TradingAccount> noneTradingAccountList = this.findTradingAccountByMonthAndStatus(monthInput,"Inactive");
        List<TradingAccount> blockTradingAccountList = this.findTradingAccountByMonthAndStatus(monthInput,"Block");
        List<TradingAccount> stopTradingAccountList = this.findTradingAccountByMonthAndStatus(monthInput,"Stop");
        TradingAccountExcelExporter excelExporter = new TradingAccountExcelExporter(title,month,excelSheetName,stopTradingAccountList,tradingAccountList,noneTradingAccountList,blockTradingAccountList);
        excelExporter.export(response);
    }

}
