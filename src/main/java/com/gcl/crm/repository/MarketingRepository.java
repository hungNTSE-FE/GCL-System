package com.gcl.crm.repository;

import com.gcl.crm.entity.TMP_KPI_EMPLOYEE;
import com.gcl.crm.form.CustomerStatusForm;
import com.gcl.crm.form.CustomerStatusEvaluationForm;
import com.gcl.crm.form.KPIEmployeeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
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

    public List<TMP_KPI_EMPLOYEE> getKPIEmployeeReport(String fromDate, String toDate) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("PROC_KPI_EVALUATION");
        storedProcedureQuery.registerStoredProcedureParameter("START_DATE", String.class, ParameterMode.IN);
        storedProcedureQuery.registerStoredProcedureParameter("END_DATE", String.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("START_DATE", fromDate);
        storedProcedureQuery.setParameter("END_DATE", toDate);
        storedProcedureQuery.execute();

        String sql = "select\n" +
                    "EMPLOYEE_ID\n" +
                    ", EMPLOYEE_NAME\n" +
                    ", SUM_CUS_DATA\n" +
                    ", SUM_LOT\n" +
                    ", KPI\n" +
                    "FROM tmp_kpi_employee\n" +
                    "ORDER BY KPI desc";
        Query query = entityManager.createNativeQuery(sql, TMP_KPI_EMPLOYEE.class);
        return query.getResultList();
    }

}
