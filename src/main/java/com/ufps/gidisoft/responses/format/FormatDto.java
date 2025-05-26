package com.ufps.gidisoft.responses.format;

import com.ufps.gidisoft.entities.formats.general.Format;
import com.ufps.gidisoft.entities.formats.general.ManagerUserFormat;
import com.ufps.gidisoft.requests.formats.ManagerUserFormatRequest;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FormatDto {

    private Long formatId;

    private String code;

    private String version;

    private LocalDate date;

    private ManagerUserFormatRequest managerUsers;

    private Long group;

    private String unity;

    private Long directorId;

    private String department;

    private Long faculty;

    private Long academicPeriod;

    public FormatDto(Format format, ManagerUserFormat managerUserFormat) {
        this.formatId = format.getId();
        this.code = format.getCode();
        this.version = format.getVersion();
        this.date = format.getDate();
        this.managerUsers = new ManagerUserFormatRequest(managerUserFormat.getCreatedBy(),
                managerUserFormat.getReviewBy(), managerUserFormat.getApproveBy());
        this.group = format.getGroup().getId();
        this.unity = format.getUnity();
        this.directorId = format.getDirector().getId();
        this.department = format.getDepartment();
        this.faculty = format.getFaculty().getId();
        this.academicPeriod = format.getAcademicPeriod().getId();
    }

}
