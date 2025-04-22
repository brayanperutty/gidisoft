package com.ufps.gidisoft.entities.formats;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "manager_user_format")
@AllArgsConstructor
@NoArgsConstructor
public class ManagerUserFormat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "review_by")
    private String reviewBy;
    @Column(name = "approve_by")
    private String approveBy;
    @OneToOne
    @JoinColumn(name = "format_id")
    private Format format;
}
