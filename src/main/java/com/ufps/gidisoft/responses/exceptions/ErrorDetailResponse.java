package com.ufps.gidisoft.responses.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailResponse {

    private String code;
    private String description;
    private List<String> fields;

    public ErrorDetailResponse(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
