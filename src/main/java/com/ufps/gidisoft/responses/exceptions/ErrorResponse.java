package com.ufps.gidisoft.responses.exceptions;

import com.ufps.gidisoft.enums.exceptions.ExceptionCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private ErrorDetailResponse errors;

    public ErrorResponse(ExceptionCodeEnum exceptionCodeEnum) {
        this.errors = new ErrorDetailResponse(
                exceptionCodeEnum.getCode(),
                exceptionCodeEnum.getDescription(),
                Collections.singletonList(exceptionCodeEnum.getMessage())
        );
    }
}
