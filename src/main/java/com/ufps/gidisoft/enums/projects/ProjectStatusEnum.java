package com.ufps.gidisoft.enums.projects;

import lombok.Getter;

@Getter
public enum ProjectStatusEnum {

    DRAFT(1L, "Borrador"),
    PUBLICATED(2L, "Publicado"),
    ;

    private final Long id;
    private final String status;

    ProjectStatusEnum(Long id, String status) {
        this.id = id;
        this.status = status;
    }
}
