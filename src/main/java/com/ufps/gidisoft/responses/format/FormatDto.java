package com.ufps.gidisoft.responses.format;

import com.ufps.gidisoft.entities.formats.general.Format;
import com.ufps.gidisoft.entities.formats.general.ManagerUserFormat;
import com.ufps.gidisoft.requests.formats.ManagerUserFormatRequest;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FormatDto {

    private Long id;

    private String code;

    private String version;

    private LocalDate date;

    private ManagerUserFormatRequest managerUsers;

    private String group;

    private String unity;

    private Long directorId;

    private String department;

    private String faculty;

    private Long academicPeriod;

    public FormatDto(Format format, ManagerUserFormat managerUserFormat) {
        this.id = format.getId();
        this.code = format.getCode();
        this.version = format.getVersion();
        this.date = format.getDate();
        this.managerUsers = new ManagerUserFormatRequest(managerUserFormat.getCreatedBy(),
                managerUserFormat.getReviewBy(), managerUserFormat.getApproveBy());
        this.group = format.getGroup();
        this.unity = format.getUnity();
        this.directorId = format.getDirector().getId();
        this.department = format.getDepartment();
        this.faculty = format.getFaculty();
        this.academicPeriod = format.getAcademicPeriod().getId();
    }

}
