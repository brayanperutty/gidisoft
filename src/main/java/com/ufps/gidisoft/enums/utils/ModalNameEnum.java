package com.ufps.gidisoft.enums.utils;

import lombok.Getter;

@Getter
public enum ModalNameEnum {

    PROJECTS(1L, "projects"),
    DIRECTIONS(2L, "directions"),
    EVENTS(3L, "events"),
    ACTIVITIES(4L, "other-activities"),
    ;

    private final Long id;
    private final String name;

    ModalNameEnum(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
