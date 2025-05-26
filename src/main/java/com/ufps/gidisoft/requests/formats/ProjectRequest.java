package com.ufps.gidisoft.requests.formats;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {

    private Long id;
    private String name;
    private String activities;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer compliancePercentage;
    private Long formatId;
    private List<MultipartFile> files = new ArrayList<>();
}
