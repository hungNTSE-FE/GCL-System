package com.gcl.crm.form;

import java.text.SimpleDateFormat;
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
    private String startDate;
    private String endDate;
    private Date createDate;
    private Integer result;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

    public String formatDate(Date inDate) {
        String outDate = "";
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            outDate = dateFormat.format(inDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outDate;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
