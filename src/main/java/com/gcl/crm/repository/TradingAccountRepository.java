package com.gcl.crm.repository;

import com.gcl.crm.entity.Documentary;
import com.gcl.crm.entity.TradingAccount;
import com.gcl.crm.enums.Status;
import com.gcl.crm.form.TradingAccountForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TradingAccountRepository  extends JpaRepository<TradingAccount,String> {
    TradingAccount getTradingAccountByAccountNumber(String accountNumber);

    @Query("SELECT new TradingAccount (t.accountNumber,t.balance,t.accountName) FROM TradingAccount t where t.accountName  like %?1% and t.accountNumber like %?2%  ORDER BY t.createDate DESC")
    List<TradingAccount> findAllByNameNumberBalance(String name,String number);

    @Query("SELECT new TradingAccount (t.accountNumber,t.balance,t.accountName) FROM TradingAccount t where not t.status = ?1   ORDER BY t.createDate DESC")
    List<TradingAccount> findAllByActive(String status);

    @Query("SELECT new TradingAccount (t.accountNumber,t.balance,t.accountName) FROM TradingAccount t where  t.balance < ?1 and t.balance > ?2   ORDER BY t.createDate DESC")
    List<TradingAccount> findAllByBalance(double high,double low);



}
