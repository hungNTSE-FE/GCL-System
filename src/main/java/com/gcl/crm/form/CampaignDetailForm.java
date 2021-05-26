package com.gcl.crm.form;

import java.util.Date;

public class CampaignDetailForm {

    private String hdnCampaignCode;
    private String content;
    private String status;
    private String assumptionResult;
    private String sourceName;
    private Integer hdnSourceId;
    private Long budget;
    private Long actualExpense;
    private Double averageExpense;
    private Date startDate;
    private Date endDate;
    private Date createDate;

    public CampaignDetailForm(){};

    public String getHdnCampaignCode() {
        return hdnCampaignCode;
    }

    public void setHdnCampaignCode(String hdnCampaignCode) {
        this.hdnCampaignCode = hdnCampaignCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssumptionResult() {
        return assumptionResult;
    }

    public void setAssumptionResult(String assumptionResult) {
        this.assumptionResult = assumptionResult;
    }

    public Integer getHdnSourceId() {
        return hdnSourceId;
    }

    public void setHdnSourceId(Integer hdnSourceId) {
        this.hdnSourceId = hdnSourceId;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public Long getActualExpense() {
        return actualExpense;
    }

    public void setActualExpense(Long actualExpense) {
        this.actualExpense = actualExpense;
    }

    public Double getAverageExpense() {
        return averageExpense;
    }

    public void setAverageExpense(Double averageExpense) {
        this.averageExpense = averageExpense;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
}
