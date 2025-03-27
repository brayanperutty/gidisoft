package com.ufps.gidisoft.enums.users;

import lombok.Getter;

@Getter
public enum UserStatusEnum {

    ACTIVE(1L, "Activo"),
    DRAFT(2L, "Borrador"),

    ;

    private final Long id;
    private final String status;

    UserStatusEnum(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
