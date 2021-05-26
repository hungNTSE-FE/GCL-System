package com.gcl.crm.form;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CampaignMaketingForm {

    private List<CampaignForm> listCampaignForm;
    private List<CampaignReportDetailForm> listCampaignReportDetailForm;
    private Date campaignReportFromDate;
    private Date campaignReportToDate;
    private Date campaignFromDate;
    private Date campaignToDate;

    public List<CampaignForm> getListCampaignForm() {
        return Collections.unmodifiableList(listCampaignForm);
    }

    public void setListCampaignForm(List<CampaignForm> listCampaignForm) {
        this.listCampaignForm = listCampaignForm;
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
}
