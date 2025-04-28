package com.ufps.gidisoft.enums.utils;

import lombok.Getter;

@Getter
public enum DateFormatEnum {
    DD_MM_YYYY("dd/MM/yyyy"),
    DD_MM_YYYY_HH_MM("dd/MM/yyyy - HH:mm"),
    DD_MM_YYYY_HH_MM_A("dd/MM/yyyy hh:mm a"),;

    private final String value;

    DateFormatEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
