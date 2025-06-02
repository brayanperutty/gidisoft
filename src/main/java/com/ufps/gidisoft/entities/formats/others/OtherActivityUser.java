package com.ufps.gidisoft.entities.formats.others;

import com.ufps.gidisoft.entities.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "other_activity_user")
@AllArgsConstructor
@NoArgsConstructor
public class OtherActivityUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "other_activity_id")
    private OtherActivity otherActivity;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public OtherActivityUser(OtherActivity otherActivity, User user) {
        this.otherActivity = otherActivity;
        this.user = user;
    }
}
