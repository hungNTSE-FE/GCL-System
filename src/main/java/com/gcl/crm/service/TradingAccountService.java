package com.gcl.crm.service;

import com.gcl.crm.entity.TradingAccount;

import java.util.List;

public interface TradingAccountService {
    List<TradingAccount> findTradingAccountByMonth(String month);
    TradingAccount findTradingAccountByID(String id);
    void exportTradingAccountByMonth(List<TradingAccount> tradingAccountList);
    List<TradingAccount> findAll();
    List<TradingAccount> findTradingAccountByMonthAndStatus(String month,String status);
}
