package com.gcl.crm.controller;

import com.gcl.crm.form.CampaignMaketingForm;
import com.gcl.crm.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CampaignController {

    public static final String MAIN_PAGE = "campaign.html";
    public static final String CAMPAIGN_MAKETING_FORM = "CAMPAIGN_MAKETING_FORM";

    @Autowired
    CampaignService campaignService;

    @GetMapping(value = "/campaign")
    public String initScreen(Model model) {
        CampaignMaketingForm form = campaignService.init();
        model.addAttribute(CAMPAIGN_MAKETING_FORM, form);
        return MAIN_PAGE;
    }
}
