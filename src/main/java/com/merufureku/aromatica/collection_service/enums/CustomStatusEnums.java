package com.merufureku.aromatica.collection_service.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CustomStatusEnums {

    NO_USER_FOUND(4000, "No User Found",HttpStatus.NOT_FOUND),
    INVALID_TOKEN(4001, "Invalid Token", HttpStatus.UNAUTHORIZED),
    FRAGRANCE_NOT_FOUND(4002, "Perfume not found", HttpStatus.NOT_FOUND),
    FRAGRANCE_ALREADY_EXIST(4003, "Perfume already exist", HttpStatus.CONFLICT);

    private int statusCode;
    private String message;
    private HttpStatus httpStatus;

    CustomStatusEnums(int statusCode, String message, HttpStatus httpStatus) {
        this.statusCode = statusCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
