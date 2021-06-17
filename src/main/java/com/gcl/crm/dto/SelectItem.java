package com.gcl.crm.dto;

public class SelectItem {
    private String key;
    private String value;

    public SelectItem() {
    }

    public SelectItem(String name, String value) {
        this.key = name;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
