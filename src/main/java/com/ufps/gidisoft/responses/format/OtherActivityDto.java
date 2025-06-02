package com.ufps.gidisoft.responses.format;

import com.ufps.gidisoft.entities.formats.others.OtherActivity;
import lombok.Data;

import java.util.List;

@Data
public class OtherActivityDto {

    private Long id;
    private String name;
    private String type;
    private Integer compliancePercentage;
    private Long formatId;
    private Long createdBy;
    private String createdByName;
    private List<String> editorsNames;
    private List<String> files;

    public OtherActivityDto(OtherActivity otherActivity, List<String> editorsNames) {
        this.id = otherActivity.getId();
        this.name = otherActivity.getName();
        this.type = otherActivity.getType();
        this.compliancePercentage = otherActivity.getCompliancePercentage();
        this.formatId = otherActivity.getFormat().getId();
        this.createdBy = otherActivity.getCreatedBy().getId();
        this.createdByName = otherActivity.getCreatedBy().getName();
        this.editorsNames = editorsNames;
        this.files = otherActivity.getFiles();
    }
}
