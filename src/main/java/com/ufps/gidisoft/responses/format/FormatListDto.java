package com.ufps.gidisoft.responses.format;

import com.ufps.gidisoft.entities.formats.Format;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormatListDto {

    private Long id;
    private String code;
    private String name;
    private String director;
    private String period;
    private String status;

    public FormatListDto(Format format) {
        this.id = format.getId();
        this.code = format.getCode();
        this.name = format.getName();
        this.director = format.getDirector().getName();
        this.period = format.getAcademicPeriod().getYear() + " - " + format.getAcademicPeriod().getPeriod();
        this.status = format.getStatus().getName();
    }
}
