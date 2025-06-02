package com.ufps.gidisoft.enums.exceptions;

import lombok.Getter;

@Getter
public enum ExceptionCodeEnum {

    USER01("The user is not found", ExceptionEnum.NOT_FOUND_EXCEPTION.getValue()),
    USER02("The login credentials are not correct", ExceptionEnum.REQUEST_EXCEPTION.getValue()),

    ROLE01("The role is not found", ExceptionEnum.NOT_FOUND_EXCEPTION.getValue()),
    ACAPER01("The academic period is not found", ExceptionEnum.NOT_FOUND_EXCEPTION.getValue()),

    FORMAT01("Formato no encontrado", ExceptionEnum.NOT_FOUND_EXCEPTION.getValue()),
    FORMAT02("Ya existe un formato para ese período académico.", ExceptionEnum.REQUEST_EXCEPTION.getValue()),

    PROJSTS01("Estado del proyecto no encontrado", ExceptionEnum.NOT_FOUND_EXCEPTION.getValue()),
    PROJ01("Proyecto no encontrado", ExceptionEnum.NOT_FOUND_EXCEPTION.getValue()),
    PROJ02("No tienes permiso para editar este formato", ExceptionEnum.VALIDATION_EXCEPTION.getValue()),

    DIR01("Participación de dirección no encontrada", ExceptionEnum.NOT_FOUND_EXCEPTION.getValue()),

    GROUP01("The investigation group is not found", ExceptionEnum.NOT_FOUND_EXCEPTION.getValue()),
    FAC01("The faculty is not found", ExceptionEnum.NOT_FOUND_EXCEPTION.getValue()),

    EVENT01("The event is not found", ExceptionEnum.NOT_FOUND_EXCEPTION.getValue()),
    EVENT02("No tienes permisos para editar este evento", ExceptionEnum.VALIDATION_EXCEPTION.getValue()),

    OTHER01("The other activity is not found", ExceptionEnum.NOT_FOUND_EXCEPTION.getValue()),
    OTHER02("No tienes permisos para editar esta actividad.", ExceptionEnum.NOT_FOUND_EXCEPTION.getValue()),

    TOKEN01("The token has expiry", ExceptionEnum.NOT_FOUND_EXCEPTION.getValue()),
    TOKEN02("The token is invalid", ExceptionEnum.REQUEST_EXCEPTION.getValue()),

    PERM01("Usuario/os ya cuentan con permisos.", ExceptionEnum.REQUEST_EXCEPTION.getValue()),
    ;

    private final String code;
    private final String message;
    private final String description;

    ExceptionCodeEnum(String message, String description) {
        this.code = this.name();
        this.message = message;
        this.description = description;
    }

    public static String getMessageFromCode(String code) {
        for (ExceptionCodeEnum exc : values()) {
            if (exc.getCode().equalsIgnoreCase(code)) {
                return exc.getMessage();
            }
        }
        return "";
    }
}
