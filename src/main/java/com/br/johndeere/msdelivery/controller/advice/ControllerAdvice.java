package com.br.johndeere.msdelivery.controller.advice;

import com.br.johndeere.msdelivery.componet.Dictionary;
import com.br.johndeere.msdelivery.exceptions.ErrorMessage;
import com.br.johndeere.msdelivery.exceptions.InternalServerError;
import com.br.johndeere.msdelivery.exceptions.UnprocessableEntityException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("400.001")
                .message(Dictionary.getMessage("400.001", ex.getParameterName()))
                .build();
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<Object> handleUnprocessableEntityException(final UnprocessableEntityException ex) {
        if(StringUtils.isEmpty(ex.getErrorMessage().getMessage())){
            ex.getErrorMessage().setMessage(Dictionary.getMessage(ex.getErrorMessage().getCode()));
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getErrorMessage());
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<Object> handleInternalServerError() {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("500.000")
                .message(Dictionary.getMessage("500.000"))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

}
