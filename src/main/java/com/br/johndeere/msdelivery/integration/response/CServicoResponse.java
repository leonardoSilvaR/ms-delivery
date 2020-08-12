package com.br.johndeere.msdelivery.integration.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CServicoResponse {

    @JsonProperty("Codigo")
    private Integer codigo;
    @JsonProperty("Valor")
    private String valor;
    @JsonProperty("PrazoEntrega")
    private String prazoEntrega;
    @JsonProperty("ValorMaoPropria")
    private String valorMaoPropria;
    @JsonProperty("ValorAvisoRecebimento")
    private String valorAvisoRecebimento;
    @JsonProperty("ValorValorDeclarado")
    private String valorValorDeclarado;
    @JsonProperty("EntregaDomiciliar")
    private String entregaDomiciliar;
    @JsonProperty("EntregaSabado")
    private String entregaSabado;
    @JsonProperty("Erro")
    private String erro;
    @JsonProperty("MsgErro")
    private String msgErro;
    @JsonProperty("valorSemAdicionais")
    private String valorSemAdicionais;
    private String obsFim;
    @JsonProperty("DataMaxEntrega")
    private String dataMaxEntrega;
    @JsonProperty("HoraMaxEntrega")
    private String horaMaxEntrega;

}
