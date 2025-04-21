package com.ufps.gidisoft.entities.format;

import com.ufps.gidisoft.entities.AcademicPeriods;
import com.ufps.gidisoft.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import io.hypersistence.utils.hibernate.type.json.JsonType;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "format")
public class Format {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "version", nullable = false)
    private String version;
    @Column(name = "date", nullable = false)
    private LocalDateTime date;
    @Column(name = "name", nullable = false)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_user_id", nullable = false)
    private User director;
    @ManyToOne
    @JoinColumn(name = "academic_period_id", nullable = false)
    private AcademicPeriods academicPeriod;
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb", length = 3000, name = "sections_values")
    private Map<String, Object> sectionsValues;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    @ManyToOne
    @JoinColumn(name = "review_by")
    private User reviewBy;
    @ManyToOne
    @JoinColumn(name = "approve_by")
    private User approveBy;
}
