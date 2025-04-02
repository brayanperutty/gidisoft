package com.ufps.gidisoft.responses.users;

import com.ufps.gidisoft.entities.User;
import com.ufps.gidisoft.enums.users.UserStatusEnum;
import lombok.Data;

import java.util.Objects;

@Data
public class UsersDto {

    private String usercode;
    private String name;
    private String email;
    private String role;
    private String status;

    public UsersDto(User user) {
        this.usercode = user.getUsercode();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole().getName();
        this.status = Objects.requireNonNull(UserStatusEnum.getById(user.getUserStatus())).getStatus();
    }
}
