package com.ufps.gidisoft.entities.formats.directions;

import com.ufps.gidisoft.entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "direction_user")
@AllArgsConstructor
@NoArgsConstructor
public class DirectionUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "direction_id")
    private Direction direction;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public DirectionUser(Direction direction, User user) {
        this.direction = direction;
        this.user = user;
    }
}
