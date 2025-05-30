package com.ufps.gidisoft.entities.formats.events;

import com.ufps.gidisoft.entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "event_user")
@AllArgsConstructor
@NoArgsConstructor
public class EventUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public EventUser(Event event, User user) {
        this.event = event;
        this.user = user;
    }
}
