package com.gcl.crm.form;

public class CampaignReportDetailForm {

    private Integer sourceId;
    private String sourceName;
    private Integer numOfCustomerRegistered;
    private Integer numOfCustomerCreatedAccount;
    private Integer numOfCustomerTopUp;

    public CampaignReportDetailForm() {
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Integer getNumOfCustomerRegistered() {
        return numOfCustomerRegistered;
    }

    public void setNumOfCustomerRegistered(Integer numOfCustomerRegistered) {
        this.numOfCustomerRegistered = numOfCustomerRegistered;
    }

    public Integer getNumOfCustomerCreatedAccount() {
        return numOfCustomerCreatedAccount;
    }

    public void setNumOfCustomerCreatedAccount(Integer numOfCustomerCreatedAccount) {
        this.numOfCustomerCreatedAccount = numOfCustomerCreatedAccount;
    }

    public Integer getNumOfCustomerTopUp() {
        return numOfCustomerTopUp;
    }

    public void setNumOfCustomerTopUp(Integer numOfCustomerTopUp) {
        this.numOfCustomerTopUp = numOfCustomerTopUp;
    }
}
