package com.gcl.crm.controller;

import com.gcl.crm.entity.User;
import com.gcl.crm.form.CampaignDetailForm;
import com.gcl.crm.form.CampaignMaketingForm;
import com.gcl.crm.service.CampaignService;
import com.gcl.crm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
public class CampaignController {

    public static final String MAIN_PAGE = "campaign/campaign.html";
    public static final String CAMPAIGN_MAKETING_FORM = "CAMPAIGN_MAKETING_FORM";

    @Autowired
    CampaignService campaignService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/campaign")
    public String initScreen(@ModelAttribute CampaignMaketingForm form, BindingResult errors, Model model, Principal principal) {
        User currentUser = userService.getUserByUsername(principal.getName());
        form = campaignService.init();
        form.setCampaignFromDate(new Date());
        form.setCampaignToDate(new Date());
        model.addAttribute(CAMPAIGN_MAKETING_FORM, form);
        model.addAttribute("userInfo", currentUser);
        return MAIN_PAGE;
    }

    @PostMapping(value = "/campaign/getCampaignMaketingForm")
    public ResponseEntity<CampaignMaketingForm> initScreen() {
        CampaignMaketingForm form = campaignService.init();
        return new ResponseEntity<>(form, HttpStatus.OK);
    }

    @PostMapping(value = "/campaign/getListCampaignModal")
    public ResponseEntity<CampaignDetailForm> getListCampaignModal(@RequestBody String id) {
        CampaignDetailForm form = campaignService.getCampaignFormByPK(id);
        return new ResponseEntity<>(form, HttpStatus.OK);
    }

    @PostMapping(value = "/campaign/saveCampaignDetail")
    public ResponseEntity saveCampaignDetail(CampaignDetailForm form){
        if (campaignService.saveCampaignDetailForm(form)) return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/campaign/deteleCampaignDetail")
    @ResponseBody
    public ResponseEntity deteleCampaignDetail(@RequestBody  String listSelectedId){
        List<String> selectedIdList = Arrays.asList(listSelectedId.split(","));
        for (String selectedId : selectedIdList) {
            if (!campaignService.deleteCampaignDetailForm(Integer.parseInt(selectedId)))
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("true", HttpStatus.OK);
    }
}
