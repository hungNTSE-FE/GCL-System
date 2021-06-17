package com.gcl.crm.enums;

import java.util.Arrays;

public enum Gender {
    MALE("0")
    , FEMALE("1");

    private String value;

    Gender(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public static Gender findByOption(String option){
        return Arrays
                .asList(values())
                .stream()
                .filter(gender -> gender.getValue().equals(option))
                .findFirst()
                .orElse(MALE); // Default Male
    }
}
