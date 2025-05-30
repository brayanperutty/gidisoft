package com.ufps.gidisoft.entities.formats.events;

import com.ufps.gidisoft.entities.formats.general.Format;
import com.ufps.gidisoft.entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Column(name = "compliance_percentage")
    private Integer compliancePercentage;

    @ManyToOne
    @JoinColumn(name = "format_id")
    private Format format;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "files", length = 30000)
    private List<String> files;
}

