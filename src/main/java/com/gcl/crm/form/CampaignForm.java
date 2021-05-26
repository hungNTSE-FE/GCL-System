package com.gcl.crm.form;

import java.util.Collections;
import java.util.List;

public class CampaignForm {
    private String sourceName;
    private Integer totalResult;
    private Long totalBudget;
    private Long totalActualExpense;
    private Double totalAverageExpense;
    private List<CampaignDetailForm> campaignDetailFormList;

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Integer getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(Integer totalResult) {
        this.totalResult = totalResult;
    }

    public Long getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(Long totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Long getTotalActualExpense() {
        return totalActualExpense;
    }

    public void setTotalActualExpense(Long totalActualExpense) {
        this.totalActualExpense = totalActualExpense;
    }

    public Double getTotalAverageExpense() {
        return totalAverageExpense;
    }

    public void setTotalAverageExpense(Double totalAverageExpense) {
        this.totalAverageExpense = totalAverageExpense;
    }

    public List<CampaignDetailForm> getCampaignDetailFormList() {
        return Collections.unmodifiableList(campaignDetailFormList);
    }

    public void setCampaignDetailFormList(List<CampaignDetailForm> campaignDetailFormList) {
        this.campaignDetailFormList = campaignDetailFormList;
    }
}
