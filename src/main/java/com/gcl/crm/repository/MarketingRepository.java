package com.gcl.crm.repository;

import com.gcl.crm.form.CustomerStatusForm;
import com.gcl.crm.form.CustomerStatusEvaluationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Repository
public class MarketingRepository {

    @Autowired
    EntityManager entityManager;

    public List<CustomerStatusForm> getCustomerStatusList(String startDate, String endDate) {
        String sql = "select emp.full_name employee_name\n" +
                            ", count(if(level_name = 'Level 0', 1, null)) level_0\n" +
                            ", count(if(level_name = 'Level 1', 1, null)) level_1\n" +
                            ", count(if(level_name = 'Level 2', 1, null)) level_2\n" +
                            ", count(if(level_name = 'Level 3', 1, null)) level_3\n" +
                            ", count(if(level_name = 'Level 4', 1, null)) level_4\n" +
                            ", count(if(level_name = 'Level 5', 1, null)) level_5\n" +
                            ", count(if(level_name = 'Level 6', 1, null)) level_6\n" +
                            ", count(if(level_name = 'Level 7', 1, null)) level_7\n" +
                    "from employee emp\n" +
                        "inner join customer_distribution cus_dis on emp.id = cus_dis.employee_id\n" +
                        "inner join customer cus on cus.customer_code = cus_dis.customer_code\n" +
                        "inner join level lv on cus.level_id = lv.level_id\n" +
                        "where cus_dis.date_distribution between " +
                                        "str_to_date(:start_date, '%Y-%m-%d') and str_to_date(:end_date, '%Y-%m-%d')\n" +
                        "group by full_name";
        Query query = entityManager.createNativeQuery(sql, "getCustomerStatusListMapping");
        query.setParameter("start_date", startDate);
        query.setParameter("end_date", endDate);
        List<CustomerStatusForm> customerStatusFormList = query.getResultList();
        return customerStatusFormList;
    }

    public List<CustomerStatusEvaluationForm> getCustomerStatusEvaluationList(String startDate, String endDate) {
        String sql = "select emp.full_name employee_name\n" +
                    ", count(if(level_name = 'Level 6', 1, null)) level_6\n" +
                    ", count(if(level_name = 'Level 7', 1, null)) level_7\n" +
                "from employee emp\n" +
                    "inner join customer_distribution cus_dis on emp.id = cus_dis.employee_id\n" +
                    "inner join customer cus on cus.customer_code = cus_dis.customer_code\n" +
                    "inner join level lv on cus.level_id = lv.level_id\n" +
                "where cus_dis.date_distribution between str_to_date(:start_date, '%Y-%m-%d') and  str_to_date(:end_date, '%Y-%m-%d')\n" +
                "group by full_name";
        Query query = entityManager.createNativeQuery(sql, "getCustomerStatusReportListMapping");
        query.setParameter("start_date", startDate);
        query.setParameter("end_date", endDate);
        List<CustomerStatusEvaluationForm> customerStatusReportFormList = query.getResultList();
        return customerStatusReportFormList;
    }

}
