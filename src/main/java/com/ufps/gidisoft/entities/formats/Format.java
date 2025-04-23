package com.ufps.gidisoft.entities.formats;

import com.ufps.gidisoft.entities.academic_periods.AcademicPeriods;
import com.ufps.gidisoft.entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "formats")
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "group_name")
    private String group;

    @Column(name = "unity")
    private String unity;

    @ManyToOne
    @JoinColumn(name = "director_user_id")
    private User director;

    @Column(name = "department")
    private String department;

    @Column(name = "faculty")
    private String faculty;

    @ManyToOne
    @JoinColumn(name = "academic_period_id")
    private AcademicPeriods academicPeriod;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private ProjectStatus status;
}
