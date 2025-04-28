package com.ufps.gidisoft.responses.format;

import com.ufps.gidisoft.entities.formats.projects.Project;
import com.ufps.gidisoft.enums.utils.DateFormatEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class ProjectDto {

    private Long id;
    private String name;
    private String activities;
    private LocalDate startDate;
    private LocalDate endDate;
    private String startDateFormatter;
    private String endDateFormatter;
    private Integer compliancePercentage;
    private Long formatId;
    private Long createdBy;
    private String createdByName;
    private Long statusId;

    public ProjectDto(Project project) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern(DateFormatEnum.DD_MM_YYYY.getValue());
        this.id = project.getId();
        this.name = project.getName();
        this.activities = project.getActivities();
        this.startDate = project.getStartDate();
        this.startDateFormatter = project.getStartDate().format(formatters);
        this.endDateFormatter = project.getEndDate().format(formatters);
        this.endDate = project.getEndDate();
        this.compliancePercentage = project.getCompliancePercentage();
        this.formatId = project.getFormat().getId();
        this.createdBy = project.getCreatedBy().getId();
        this.createdByName = project.getCreatedBy().getName();
        this.statusId = project.getStatus().getId();
    }
}

