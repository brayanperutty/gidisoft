package com.ufps.gidisoft.requests.formats;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NotNull
public class FormatRequest {

    private String code;

    private String version;

    private LocalDate date;

    private ManagerUserFormatRequest managerUsers;

    private String name;

    private String unity;

    private Long directorId;

    private String department;

    private String faculty;

    private Long academicPeriod;
}
