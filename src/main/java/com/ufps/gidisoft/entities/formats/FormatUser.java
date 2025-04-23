package com.ufps.gidisoft.entities.formats;

import com.ufps.gidisoft.entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "format_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormatUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "format_id")
    private Format format;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public FormatUser(Format format, User user) {
        this.format = format;
        this.user = user;
    }
}
