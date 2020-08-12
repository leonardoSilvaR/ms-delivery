package com.br.johndeere.msdelivery.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerError extends RuntimeException {

    private ErrorMessage errorMessage;

    public InternalServerError() {

    }

    public InternalServerError(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

}
