package com.ufps.gidisoft.responses.format;

import com.ufps.gidisoft.entities.format.Format;
import lombok.Data;

@Data
public class FormatDto {

    private String code;
    private String name;
    private String director;
    private String deparment;
    private String faculty;
    private String year;
    private String period;

    public FormatDto(Format format) {
        this.code = format.getCode();
        this.name = format.getName();
        this.director = format.getDirector().getName();
        this.year = format.getAcademicPeriod().getYear().toString();
        this.period = format.getAcademicPeriod().getPeriod();
    }

}
