package com.br.johndeere.msdelivery.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ErrorMessage implements Serializable {

    private String code;
    private String message;
    private String detail;

}
