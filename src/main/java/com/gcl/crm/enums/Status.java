package com.gcl.crm.enums;

import java.util.Arrays;

public enum Status {
    ACTIVE("1")
    , INACTIVE("0");

    private String value;

    Status(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public static Status findByOption(String option){
        return Arrays
                .asList(values())
                .stream()
                .filter(status -> status.getValue().equals(option))
                .findFirst()
                .orElse(ACTIVE); // Default Active;
    }
}
