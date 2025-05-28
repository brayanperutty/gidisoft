package com.ufps.gidisoft.responses.users;

import com.ufps.gidisoft.entities.users.User;
import com.ufps.gidisoft.enums.users.UserStatusEnum;
import lombok.Data;

import java.util.Objects;

@Data
public class UsersDto {

    private Long id;
    private String usercode;
    private String name;
    private String email;
    private String roleType;
    private String roleName;
    private Long roleId;
    private String phoneNumber;
    private String status;
    private Long statusId;

    public UsersDto(User user) {
        this.id = user.getId();
        this.usercode = user.getUsercode();
        this.name = user.getName();
        this.email = user.getEmail();
        this.roleType = user.getRole().getType();
        this.roleName = user.getRole().getName();
        this.roleId = user.getRole().getId();
        this.status = Objects.requireNonNull(UserStatusEnum.getById(user.getUserStatus())).getStatus();
        this.statusId = user.getUserStatus();
        this.phoneNumber = user.getPhoneNumber();
    }
}
