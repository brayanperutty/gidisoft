package com.ufps.gidisoft.entities.formats.others;

import com.ufps.gidisoft.entities.formats.general.Format;
import com.ufps.gidisoft.entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "other_activities")
@AllArgsConstructor
@NoArgsConstructor
public class OtherActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
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
