package com.gcl.crm.service;

import com.gcl.crm.form.CampaignDetailForm;
import com.gcl.crm.form.CampaignForm;
import com.gcl.crm.form.CampaignMaketingForm;
import com.gcl.crm.form.CampaignReportDetailForm;
import com.gcl.crm.entity.Campaign;
import com.gcl.crm.entity.Source;
import com.gcl.crm.repository.CampaignRepository;
import com.gcl.crm.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CampaignService {

    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    SourceRepository sourceRepository;

    public CampaignMaketingForm init() {
        CampaignMaketingForm campaignMaketingForm = new CampaignMaketingForm();

        List<CampaignForm> listCampaignForm = getCampaignFormList();
        List<CampaignReportDetailForm> campaignReportDetailFormList = new ArrayList<>();

        campaignMaketingForm.setListCampaignForm(listCampaignForm);
        campaignMaketingForm.setListCampaignReportDetailForm(campaignReportDetailFormList);
        return campaignMaketingForm;
    }

    public void saveCampaignDetail(CampaignDetailForm campaignDetailForm) {
        Campaign campaign = new Campaign();

        campaign.setContent(campaignDetailForm.getContent());
        campaign.setAssumptionResult(campaignDetailForm.getAssumptionResult());
        campaign.setStatus(campaignDetailForm.getStatus());
        campaign.setSourceId(campaignDetailForm.getHdnSourceId());
        campaign.setBudget(campaignDetailForm.getBudget());
        campaign.setActualExpense(campaignDetailForm.getActualExpense());
        campaign.setAverageExpense(campaignDetailForm.getAverageExpense());
        campaign.setStartDate(campaignDetailForm.getStartDate());
        campaign.setEndDate(campaignDetailForm.getEndDate());
        campaign.setCreateDate(new Date());
        campaignRepository.save(campaign);
    }

    private void updateCampaignDetail(List<CampaignForm> listCampaignForm) {
        Optional.ofNullable(listCampaignForm)
                .orElse(Collections.emptyList())
                .stream()
                .forEach(listCampaignDetail -> {
                    Optional.ofNullable(listCampaignDetail.getCampaignDetailFormList())
                            .orElse(Collections.emptyList())
                            .stream()
                            .forEach(campaignDetailForm -> {
                                Campaign campaign = new Campaign();

                                campaign.setContent(campaignDetailForm.getContent());
                                campaign.setAssumptionResult(campaignDetailForm.getAssumptionResult());
                                campaign.setStatus(campaignDetailForm.getStatus());
                                campaign.setSourceId(campaignDetailForm.getHdnSourceId());
                                campaign.setBudget(campaignDetailForm.getBudget());
                                campaign.setActualExpense(campaignDetailForm.getActualExpense());
                                campaign.setAverageExpense(campaignDetailForm.getAverageExpense());
                                campaign.setStartDate(campaignDetailForm.getStartDate());
                                campaign.setEndDate(campaignDetailForm.getEndDate());
                                campaign.setCreateDate(new Date());
                                campaignRepository.update(campaign);
                            });
                });
    }

    private List<CampaignForm> getCampaignFormList() {

        // Get Source Map
        List<Source> sourceList = sourceRepository.getAll();
        Map<Integer, String> sourceMap = sourceList
                .stream()
                .collect(Collectors.toMap(Source::getSourceId, Source::getSourceName));

        List<CampaignDetailForm> campaignDetailFormList =
                campaignRepository
                        .getAll()
                        .stream()
                        .map(campaign -> convertToCampaginForm(campaign, sourceMap))
                        .collect(Collectors.toList());

        Map<String, List<CampaignDetailForm>> campaignFormMap =  campaignDetailFormList
                                                                                    .stream()
                                                                                    .collect(Collectors.groupingBy(
                                                                                            CampaignDetailForm::getSourceName));

        List<CampaignForm> campaignFormList = campaignFormMap.entrySet().stream().map(entry -> {
            CampaignForm campaignForm = new CampaignForm();
            campaignForm.setSourceName(entry.getKey());
            campaignForm.setCampaignDetailFormList(entry.getValue());
            campaignForm.setTotalResult(1);
            campaignForm.setTotalBudget(entry.getValue().stream().mapToLong(CampaignDetailForm::getBudget).sum());
            campaignForm.setTotalActualExpense(entry.getValue().stream().mapToLong(CampaignDetailForm::getActualExpense).sum());
            campaignForm.setTotalAverageExpense(entry.getValue().stream().mapToDouble(CampaignDetailForm::getAverageExpense).sum());
            return campaignForm;
        }).collect(Collectors.toList());

        return campaignFormList;
    }

    private CampaignDetailForm convertToCampaginForm(Campaign campaign, Map<Integer, String> sourceMap){
        CampaignDetailForm campaignDetailForm = new CampaignDetailForm();
        campaignDetailForm.setHdnCampaignCode(campaign.getCampaignCode().toString());
        campaignDetailForm.setContent(campaign.getContent());
        campaignDetailForm.setStatus(campaign.getStatus());
        campaignDetailForm.setAssumptionResult(campaign.getAssumptionResult());
        campaignDetailForm.setHdnSourceId(campaign.getSourceId());
        campaignDetailForm.setSourceName(sourceMap.get(campaign.getSourceId()));
        campaignDetailForm.setBudget(campaign.getBudget());
        campaignDetailForm.setActualExpense(campaign.getActualExpense());
        campaignDetailForm.setAverageExpense(campaign.getAverageExpense());
        campaignDetailForm.setStartDate(campaign.getStartDate());
        campaignDetailForm.setEndDate(campaign.getEndDate());
        campaignDetailForm.setCreateDate(campaign.getCreateDate());
        return campaignDetailForm;
    }

}
