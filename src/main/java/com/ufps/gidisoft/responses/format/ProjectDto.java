package com.ufps.gidisoft.responses.format;

import com.ufps.gidisoft.entities.formats.Project;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectDto {

    private Long id;
    private String name;
    private String activities;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer compliancePercentage;
    private Long formatId;
    private Long createdBy;

    public ProjectDto(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.activities = project.getActivities();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.compliancePercentage = project.getCompliancePercentage();
        this.formatId = project.getFormat().getId();
        this.createdBy = project.getCreatedBy().getId();
    }
}

