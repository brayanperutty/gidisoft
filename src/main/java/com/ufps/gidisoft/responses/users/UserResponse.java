package com.ufps.gidisoft.responses.users;

import com.ufps.gidisoft.entities.User;
import lombok.Data;

@Data
public class UserResponse {

    private String token;
    private User user;

    public UserResponse(String token, User user) {
        this.token = token;
        this.user = user;
    }

    public UserResponse(User user) {
        this.user = user;
    }
}
