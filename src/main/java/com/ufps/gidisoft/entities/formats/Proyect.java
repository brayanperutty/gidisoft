package com.ufps.gidisoft.entities.formats;

import com.ufps.gidisoft.requests.formats.ProyectRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "proyects")
@AllArgsConstructor
@NoArgsConstructor
public class Proyect {

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
    private Long status;

    public Proyect(ProyectRequest proyectRequest, Format format) {
        this.name = proyectRequest.getName();
        this.activities = proyectRequest.getActivities();
        this.startDate = proyectRequest.getStartDate();
        this.endDate = proyectRequest.getEndDate();
        this.compliancePercentage = proyectRequest.getCompliancePercentage();
        this.format = format;
    }
}
