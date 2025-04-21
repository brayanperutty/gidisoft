package com.ufps.gidisoft.entities.formats;

import com.ufps.gidisoft.entities.academic_periods.AcademicPeriods;
import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.requests.formats.FormatRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "format")
public class Format {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "version")
    private String version;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "name")
    private String name;
    @Column(name = "unity")
    private String unity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_user_id")
    private User director;
    @Column(name = "department")
    private String department;
    @Column(name = "faculty")
    private String faculty;
    @ManyToOne
    @JoinColumn(name = "academic_period_id")
    private AcademicPeriods academicPeriod;

    public Format(FormatRequest formatRequest, User director, AcademicPeriods academicPeriods) {
        this.code = formatRequest.getCode();
        this.version = formatRequest.getVersion();
        this.date = formatRequest.getDate();
        this.name = formatRequest.getName();
        this.unity = formatRequest.getUnity();
        this.director = director;
        this.department = formatRequest.getDepartment();
        this.faculty = formatRequest.getFaculty();
        this.academicPeriod = academicPeriods;

    }
}
