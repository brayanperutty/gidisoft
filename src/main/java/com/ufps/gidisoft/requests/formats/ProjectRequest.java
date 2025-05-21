package com.ufps.gidisoft.requests.formats;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NotNull
public class ProjectRequest {

    private String name;
    private String activities;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer compliancePercentage;
    private Long formatId;
    private List<MultipartFile> files;
}
