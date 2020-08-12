package com.br.johndeere.msdelivery.integration.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CorreiosResponse {

    @JsonProperty("Servicos")
    public ServicosResponse servicos;

}
