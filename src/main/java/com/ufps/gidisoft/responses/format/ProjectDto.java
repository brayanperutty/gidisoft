package com.ufps.gidisoft.responses.format;

import com.ufps.gidisoft.entities.formats.projects.Project;
import lombok.Data;

import java.util.List;

@Data
public class ProjectDto {

    private Long id;
    private String name;
    private String activities;
    private Integer compliancePercentage;
    private Long formatId;
    private Long createdBy;
    private String createdByName;
    private Long statusId;
    private List<String> editorsNames;

    public ProjectDto(Project project, List<String> editorsNames) {
        this.id = project.getId();
        this.name = project.getName();
        this.activities = project.getActivities();
        this.compliancePercentage = project.getCompliancePercentage();
        this.formatId = project.getFormat().getId();
        this.createdBy = project.getCreatedBy().getId();
        this.createdByName = project.getCreatedBy().getName();
        this.editorsNames = editorsNames;
    }
}

