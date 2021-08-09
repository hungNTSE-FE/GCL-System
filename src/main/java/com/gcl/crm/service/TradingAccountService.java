package com.gcl.crm.service;

import com.gcl.crm.entity.TradingAccount;
import com.gcl.crm.form.TradingAccountSearchForm;

import java.util.List;

public interface TradingAccountService {
    List<TradingAccount> findTradingAccountByMonth(String month);
    TradingAccount findTradingAccountByID(String id);
    void exportTradingAccountByMonth(List<TradingAccount> tradingAccountList);
    List<TradingAccount> findAll();
    List<TradingAccount> findAllByActive();
    List<TradingAccount> findAccountStopDeal();
    void updateAccountBalance(String accountNumber,double balance);
    List<TradingAccount> findTradingAccountByMonthAndStatus(String month,String status);
    List<TradingAccount> findAllByNameNumberBalance(TradingAccountSearchForm tradingAccountSearchForm);
}
