package com.gcl.crm.form;

import com.gcl.crm.dto.SelectItem;

import java.util.List;

public class ComboboxForm {

    List<SelectItem> listBrokerName;
    List<SelectItem> listSource;

    public ComboboxForm() {
    }

    public ComboboxForm(List<SelectItem> listBrokerName, List<SelectItem> listSource) {
        this.listBrokerName = listBrokerName;
        this.listSource = listSource;
    }

    public List<SelectItem> getListBrokerName() {
        return listBrokerName;
    }

    public void setListBrokerName(List<SelectItem> listBrokerName) {
        this.listBrokerName = listBrokerName;
    }

    public List<SelectItem> getListSource() {
        return listSource;
    }

    public void setListSource(List<SelectItem> listSource) {
        this.listSource = listSource;
    }
}
