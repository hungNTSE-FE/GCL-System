package com.gcl.crm.entity;

import lombok.Data;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Data
@Table(name = "CAMPAIGN")
public class Campaign {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "CAMPAIGN_CODE")
    private Integer campaignCode;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "ASSUMPTION_RESULT")
    private String assumptionResult;

    @Column(name = "SOURCE_ID", nullable = false)
    private Long sourceId;

    @Column(name = "BUDGET")
    private Long budget;

    @Column(name = "ACTUAL_EXPENSE")
    private Long actualExpense;

    @Column(name = "AVERAGE_EXPENSE")
    private Double averageExpense;

    @Column(name = "START_DATE", nullable = false)
    private Date startDate;

    @Column(name = "END_DATE", nullable = false)
    private Date endDate;

    @Column(name = "CREATE_DATE", nullable = false)
    private Date createDate;

    @Column(name = "RESULT")
    private Integer result;

    public Integer getCampaignCode() {
        return campaignCode;
    }

    public void setCampaignCode(Integer campaignId) {
        this.campaignCode = campaignId;
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

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
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

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
