package com.ufps.gidisoft.entities.formats.products;

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
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "responsibles")
    private String responsibles;
    @Column(name = "date")
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;
    @ManyToOne
    @JoinColumn(name = "format_id")
    private Format format;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "files", length = 30000)
    private List<String> files;
}
