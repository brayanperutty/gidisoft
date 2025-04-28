package com.ufps.gidisoft.requests.formats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormatRequest {

    private Long id;

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
}
