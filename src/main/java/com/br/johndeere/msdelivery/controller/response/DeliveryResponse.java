package com.br.johndeere.msdelivery.controller.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponse {

    private LocalDate deliveryDeadLine;

}
