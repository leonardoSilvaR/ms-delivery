package com.br.johndeere.msdelivery.integration.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ServicosResponse {

    @JsonProperty("cServico")
    private CServicoResponse cServico;

}
