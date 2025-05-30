package com.ufps.gidisoft.responses.format;

import com.ufps.gidisoft.entities.formats.events.Event;
import com.ufps.gidisoft.enums.utils.DateFormatEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
public class EventDto {

    private Long id;
    private String name;
    private LocalDate createdAt;
    private String createdAtFormatted;
    private Integer compliancePercentage;
    private Long formatId;
    private Long createdBy;
    private String createdByName;
    private List<String> editorsNames;
    private List<String> files;

    public EventDto(Event event, List<String> editorsNames) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern(DateFormatEnum.DD_MM_YYYY.getValue());
        this.id = event.getId();
        this.name = event.getName();
        this.compliancePercentage = event.getCompliancePercentage();
        this.formatId = event.getFormat().getId();
        this.createdAt = event.getCreatedAt();
        this.createdAtFormatted = event.getCreatedAt().format(formatters);
        this.createdBy = event.getCreatedBy().getId();
        this.createdByName = event.getCreatedBy().getName();
        this.files = event.getFiles();
        this.editorsNames = editorsNames;
    }
}
