package com.ufps.gidisoft.responses.format;

import com.ufps.gidisoft.entities.formats.directions.Direction;
import lombok.Data;


@Data
public class DirectionDto {

    private Long id;
    private String name;
    private String projectType;
    private String director;
    private String academicProgram;
    private Integer compliancePercentage;
    private Long formatId;
    private Long createdBy;
    private String createdByName;
    private Long statusId;

    public DirectionDto(Direction direction) {
        this.id = direction.getId();
        this.name = direction.getName();
        this.projectType = direction.getProjectType();
        this.director = direction.getDirector();
        this.academicProgram = direction.getAcademicProgram();
        this.compliancePercentage = direction.getCompliancePercentage();
        this.formatId = direction.getFormat().getId();
        this.createdBy = direction.getCreatedBy().getId();
        this.createdByName = direction.getCreatedBy().getName();
        this.statusId = direction.getStatus().getId();
    }
}
