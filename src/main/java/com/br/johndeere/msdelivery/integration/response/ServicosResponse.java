package com.br.johndeere.msdelivery.integration.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ServicosResponse {

    private CServicoResponse cServico;

}
