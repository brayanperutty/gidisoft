package com.ufps.gidisoft.enums.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionEnum {

    REQUEST_EXCEPTION("C01", "Request exception"),
    VALIDATION_EXCEPTION("C03", "Validation Exception"),
    QUERY_EXCEPTION("C02", "Query Exception"),
    NOT_FOUND_EXCEPTION("N01", "NotFound Exception");

    private final String code;
    private final String value;

    ExceptionEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
