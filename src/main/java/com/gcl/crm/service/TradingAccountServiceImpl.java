package com.gcl.crm.service;

import com.gcl.crm.entity.TradingAccount;
import com.gcl.crm.repository.TradingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
            if(tradingAccountList.get(i).getAccountNumber().equals("id")){
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
}
