package com.ufps.gidisoft.responses.format;

import com.ufps.gidisoft.entities.formats.directions.Direction;
import lombok.Data;


@Data
public class DirectionDto {

    private Long id;
    private String name;
    private Long director;
    private Long codirector;
    private Integer compliancePercentage;
    private Long formatId;
    private Long createdBy;
    private String createdByName;

    public DirectionDto(Direction direction) {
        this.id = direction.getId();
        this.name = direction.getName();
        this.director = direction.getDirector().getId();
        this.codirector = direction.getCodirector().getId();
        this.compliancePercentage = direction.getCompliancePercentage();
        this.formatId = direction.getFormat().getId();
        this.createdBy = direction.getCreatedBy().getId();
        this.createdByName = direction.getCreatedBy().getName();
    }
}
