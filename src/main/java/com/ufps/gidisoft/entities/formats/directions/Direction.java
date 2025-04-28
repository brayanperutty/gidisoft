package com.ufps.gidisoft.entities.formats.directions;

import com.ufps.gidisoft.entities.formats.general.Format;
import com.ufps.gidisoft.entities.formats.projects.ProjectStatus;
import com.ufps.gidisoft.entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "directions")
@AllArgsConstructor
@NoArgsConstructor
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "director")
    private String director;

    @Column(name = "project_type")
    private String projectType;

    @Column(name = "academic_program")
    private String academicProgram;

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
