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

import java.util.Date;
import java.text.SimpleDateFormat;
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

        // Get list campaign detail
        List<CampaignDetailForm> campaignDetailFormList = getCampaignDetailFormList();

        // Get map to compute group campaign
        List<CampaignForm> campaignForms = getCampaignForm(campaignDetailFormList);

        // Get list campaign report detail
        List<CampaignReportDetailForm> campaignReportDetailFormList = new ArrayList<>();

        // Set list campaign maketing
        campaignMaketingForm.setCampaignDetailFormList(campaignDetailFormList);
        campaignMaketingForm.setCampaignForm(campaignForms);
        campaignMaketingForm.setListCampaignReportDetailForm(campaignReportDetailFormList);
        return campaignMaketingForm;
    }

    public CampaignDetailForm getCampaignFormByPK(String id) {
        // Get Source Map
        List<Source> sourceList = sourceRepository.getAll();
        Map<Long, String> sourceMap = sourceList
                .stream()
                .collect(Collectors.toMap(Source::getSourceId, Source::getSourceName));
        Campaign campaign = campaignRepository.findObjectByPrimaryKey(Integer.parseInt(id));
        return convertToCampaginForm(campaign, sourceMap);
    }

    public boolean saveCampaignDetailForm(CampaignDetailForm form) {
        try {
            if (Objects.isNull(form.getHdnCampaignCode()) || form.getHdnCampaignCode().trim().isEmpty()) {
                Campaign campaign = convertFormToDTO(form);
                List<Source> sourceList = sourceRepository.getAll();
                campaign.setSourceId(sourceList.stream()
                                .filter(source -> source.getSourceName().equals(form.getSourceName()))
                                .map(Source::getSourceId).findFirst().orElse(1L));
                campaignRepository.save(campaign);
            } else {
                Campaign campaign = convertFormToDTO(form);
                campaign.setCampaignCode(Integer.parseInt(form.getHdnCampaignCode()));
                campaignRepository.update(campaign);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteCampaignDetailForm(Integer campaignCode) {
        return campaignRepository.delete(campaignCode);
    }

    private List<CampaignDetailForm> getCampaignDetailFormList() {
        // Get Source Map
        List<Source> sourceList = sourceRepository.getAll();
        Map<Long, String> sourceMap = sourceList
                .stream()
                .collect(Collectors.toMap(Source::getSourceId, Source::getSourceName));

        List<CampaignDetailForm> campaignDetailFormList =
                campaignRepository
                        .getAll()
                        .stream()
                        .map(campaign -> convertToCampaginForm(campaign, sourceMap))
                        .collect(Collectors.toList());
        return campaignDetailFormList;
    }

    private List<CampaignForm> getCampaignForm(List<CampaignDetailForm> campaignDetailFormList) {
        Map<String, List<CampaignDetailForm>> campaignDetailFormMap =  campaignDetailFormList
                                                                                    .stream()
                                                                                    .collect(Collectors.groupingBy(
                                                                                            CampaignDetailForm::getSourceName));

        List<CampaignForm> campaignForms = campaignDetailFormMap.entrySet().stream().map(entry -> {
            CampaignForm campaignForm = new CampaignForm();
            campaignForm.setSourceName(entry.getKey());
            campaignForm.setTotalResult(entry.getValue().stream().mapToInt(CampaignDetailForm::getResult).sum());
            campaignForm.setTotalBudget(entry.getValue().stream().mapToLong(CampaignDetailForm::getBudget).sum());
            campaignForm.setTotalActualExpense(entry.getValue().stream().mapToLong(CampaignDetailForm::getActualExpense).sum());
            campaignForm.setTotalAverageExpense(entry.getValue().stream().mapToDouble(CampaignDetailForm::getAverageExpense).sum());
            return campaignForm;
        }).collect(Collectors.toList());

        return campaignForms;
    }

    private CampaignDetailForm convertToCampaginForm(Campaign campaign, Map<Long, String> sourceMap){
        CampaignDetailForm campaignDetailForm = new CampaignDetailForm();
        campaignDetailForm.setHdnCampaignCode(campaign.getCampaignCode().toString());
        campaignDetailForm.setContent(campaign.getContent());
        campaignDetailForm.setStatus(campaign.getStatus());
        campaignDetailForm.setAssumptionResult(campaign.getAssumptionResult());
        campaignDetailForm.setResult(campaign.getResult());
        campaignDetailForm.setHdnSourceId(campaign.getSourceId());
        campaignDetailForm.setSourceName(sourceMap.get(campaign.getSourceId()));
        campaignDetailForm.setBudget(campaign.getBudget());
        campaignDetailForm.setActualExpense(campaign.getActualExpense());
        campaignDetailForm.setAverageExpense(campaign.getAverageExpense());
        campaignDetailForm.setStartDate(campaignDetailForm.formatDate(campaign.getStartDate()));
        campaignDetailForm.setEndDate(campaignDetailForm.formatDate(campaign.getEndDate()));
        campaignDetailForm.setCreateDate(campaign.getCreateDate());
        return campaignDetailForm;
    }

    private Campaign convertFormToDTO (CampaignDetailForm form) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        Campaign campaign = new Campaign();
        campaign.setContent(form.getContent());
        campaign.setStatus(form.getStatus());
        campaign.setAssumptionResult(form.getAssumptionResult());
        campaign.setSourceId(form.getHdnSourceId());
        campaign.setBudget(form.getBudget());
        campaign.setActualExpense(form.getActualExpense());
        campaign.setAverageExpense(form.getAverageExpense());
        campaign.setResult(form.getResult());
        campaign.setCreateDate(formatter.parse(formatter.format(date)));
        campaign.setStartDate(formatter.parse(form.getStartDate()));
        campaign.setEndDate(formatter.parse(form.getEndDate()));
        return campaign;
    }
}
