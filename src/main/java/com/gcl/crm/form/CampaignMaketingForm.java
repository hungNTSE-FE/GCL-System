package com.gcl.crm.form;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CampaignMaketingForm {

    private List<CampaignForm> campaignForm;
    private List<CampaignDetailForm> campaignDetailFormList;
    private List<CampaignReportDetailForm> listCampaignReportDetailForm;
    private Date campaignReportFromDate;
    private Date campaignReportToDate;
    private Date campaignFromDate;
    private Date campaignToDate;

    public List<CampaignForm> getCampaignForm() {
        return campaignForm;
    }

    public void setCampaignForm(List<CampaignForm> campaignForm) {
        this.campaignForm = campaignForm;
    }

    public List<CampaignReportDetailForm> getListCampaignReportDetailForm() {
        return Collections.unmodifiableList(listCampaignReportDetailForm);
    }

    public void setListCampaignReportDetailForm(List<CampaignReportDetailForm> listCampaignReportDetailForm) {
        this.listCampaignReportDetailForm = listCampaignReportDetailForm;
    }

    public Date getCampaignReportFromDate() {
        return campaignReportFromDate;
    }

    public void setCampaignReportFromDate(Date campaignReportFromDate) {
        this.campaignReportFromDate = campaignReportFromDate;
    }

    public Date getCampaignReportToDate() {
        return campaignReportToDate;
    }

    public void setCampaignReportToDate(Date campaignReportToDate) {
        this.campaignReportToDate = campaignReportToDate;
    }

    public Date getCampaignFromDate() {
        return campaignFromDate;
    }

    public void setCampaignFromDate(Date campaignFromDate) {
        this.campaignFromDate = campaignFromDate;
    }

    public Date getCampaignToDate() {
        return campaignToDate;
    }

    public void setCampaignToDate(Date campaignToDate) {
        this.campaignToDate = campaignToDate;
    }

    public List<CampaignDetailForm> getCampaignDetailFormList() {
        return Collections.unmodifiableList(campaignDetailFormList);
    }

    public void setCampaignDetailFormList(List<CampaignDetailForm> campaignDetailFormList) {
        this.campaignDetailFormList = campaignDetailFormList;
    }
}
