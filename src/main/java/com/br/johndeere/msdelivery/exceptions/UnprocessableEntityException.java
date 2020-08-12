package com.br.johndeere.msdelivery.exceptions;


public class UnprocessableEntityException extends RuntimeException {

    private ErrorMessage errorMessage;

    public UnprocessableEntityException() {

    }

    public UnprocessableEntityException(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }


}
