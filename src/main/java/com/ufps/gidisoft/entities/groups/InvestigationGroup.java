package com.ufps.gidisoft.entities.groups;

import com.ufps.gidisoft.entities.faculties.Faculty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "investigation_group")
@AllArgsConstructor
@NoArgsConstructor
public class InvestigationGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
}
