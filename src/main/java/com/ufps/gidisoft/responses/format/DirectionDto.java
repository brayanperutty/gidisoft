package com.ufps.gidisoft.responses.format;

import com.ufps.gidisoft.entities.formats.directions.Direction;
import lombok.Data;

import java.util.List;

@Data
public class DirectionDto {

    private Long id;
    private String name;
    private Long director;
    private String directorName;
    private Long codirector;
    private String codirectorName;
    private Integer compliancePercentage;
    private Long formatId;
    private Long createdBy;
    private String createdByName;
    private List<String> editorsNames;
    private List<String> files;

    public DirectionDto(Direction direction, List<String> editorsNames) {
        this.id = direction.getId();
        this.name = direction.getName();
        this.director = direction.getDirector().getId();
        this.directorName = direction.getDirector().getName();
        this.codirector = direction.getCodirector().getId();
        this.codirectorName = direction.getCodirector().getName();
        this.compliancePercentage = direction.getCompliancePercentage();
        this.formatId = direction.getFormat().getId();
        this.createdBy = direction.getCreatedBy().getId();
        this.createdByName = direction.getCreatedBy().getName();
        this.files = direction.getFiles();
        this.editorsNames = editorsNames;
    }
}
