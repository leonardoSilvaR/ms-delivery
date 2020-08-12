package com.br.johndeere.msdelivery.service;

import com.br.johndeere.msdelivery.controller.response.DeliveryResponse;
import com.br.johndeere.msdelivery.exceptions.ErrorMessage;
import com.br.johndeere.msdelivery.exceptions.UnprocessableEntityException;
import com.br.johndeere.msdelivery.integration.CorreiosIntegration;
import com.br.johndeere.msdelivery.integration.response.CorreiosResponse;
import com.br.johndeere.msdelivery.utils.XmlParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
public class DeliveryService {

    private CorreiosIntegration correiosIntegration;

    @Autowired
    public DeliveryService(CorreiosIntegration correiosIntegration) {
        this.correiosIntegration = correiosIntegration;
    }

    public DeliveryResponse retrieveDeliveryDeadline(final Integer serviceCode, final String fromZipCode, final String toZipCode) {
        log.info("Executing bussiness logic to retrieve delivery deadline");

        final String xmlRepsonse = correiosIntegration.getDeadline(serviceCode, fromZipCode, toZipCode);

        Optional<CorreiosResponse> correiosResponseOpt = XmlParser.xmlUnmarshall(xmlRepsonse, CorreiosResponse.class);

        DeliveryResponse deliveryResponse = new DeliveryResponse();
        if (correiosResponseOpt.isPresent()) {
            CorreiosResponse response = correiosResponseOpt.get();

            if (!StringUtils.isEmpty(response.getServicos().getCServico().getErro())) {
                throw new UnprocessableEntityException(ErrorMessage.builder()
                        .code("422.000")
                        .detail(response.getServicos().getCServico().getMsgErro())
                        .build());
            }

            if (!StringUtils.isEmpty(response.getServicos().getCServico().getDataMaxEntrega())) {
                deliveryResponse.setDeliveryDeadLine(LocalDate.parse(response.getServicos().getCServico().getDataMaxEntrega(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }

        }

        return deliveryResponse;
    }


}
