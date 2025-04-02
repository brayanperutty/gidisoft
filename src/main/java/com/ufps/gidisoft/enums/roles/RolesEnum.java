package com.ufps.gidisoft.enums.roles;

import lombok.Getter;

@Getter
public enum RolesEnum {

    ADMIN(1L, "admin"),
    TEACHER(2L, "teacher"),
    DIRECTOR(3L, "director"),
    ;

    private final Long id;
    private final String role;

    RolesEnum(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
