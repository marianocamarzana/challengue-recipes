package com.challenge.recipes.exception.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiErrorResponse {

    private String error;
    private String message;

    public ApiErrorResponse(String error, String message) {
        super();
        this.error = error;
        this.message = message;
    }

}