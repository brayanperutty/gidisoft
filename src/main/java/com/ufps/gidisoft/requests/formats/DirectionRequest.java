package com.ufps.gidisoft.requests.formats;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NotNull
public class DirectionRequest {

    private String name;
    private String projectType;
    private Long director;
    private Long codirector;
    private String academicProgram;
    private Integer compliancePercentage;
    private Long formatId;
}
