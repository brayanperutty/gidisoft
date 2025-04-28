package com.ufps.gidisoft.entities.formats.projects;

import com.ufps.gidisoft.entities.formats.general.Format;
import com.ufps.gidisoft.entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "projects")
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "activities")
    private String activities;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "compliance_percentage")
    private Integer compliancePercentage;

    @ManyToOne
    @JoinColumn(name = "format_id")
    private Format format;

    @ManyToOne
    @JoinColumn(name = "proyect_status_id")
    private ProjectStatus status;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
}
