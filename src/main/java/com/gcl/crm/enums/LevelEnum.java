package com.gcl.crm.enums;

import java.util.Arrays;

public enum LevelEnum {
    LEVEL_0(0),
    LEVEL_1(1),
    LEVEL_2(2),
    LEVEL_3(3),
    LEVEL_4(4),
    LEVEL_5(5),
    LEVEL_6(6),
    LEVEL_7(7);

    private Integer value;

    LevelEnum(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return this.value;
    }

    public static LevelEnum findByOption(Integer option){
        return Arrays
                .asList(values())
                .stream()
                .filter(level -> level.getValue().equals(option))
                .findFirst()
                .orElse(LEVEL_1); // Default level
    }
}

