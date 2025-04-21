package com.ufps.gidisoft.requests.formats;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@NotNull
public class FormatRequest {

    private String code;
    private String name;
    private Long directorId;
    private String department;
    private String faculty;
    private Long academicPeriod;
    private Map<String, Object> sectionsValues;
}
