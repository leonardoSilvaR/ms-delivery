package com.br.johndeere.msdelivery.integration;

import com.br.johndeere.msdelivery.exceptions.UnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CorreiosIntegration {

    @Value("${integration.correios.uri}")
    private String correiosUri;
    private RestTemplate restTemplate;

    @Autowired
    public CorreiosIntegration(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getDeadline(final Integer serviceCode, final String fromZipCode, final String toZipCode) {
        final Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("nCdServico", serviceCode);
        queryParams.put("sCepOrigem", fromZipCode);
        queryParams.put("sCepDestino", toZipCode);

        String uriComponentsBuilder = UriComponentsBuilder.fromUriString(correiosUri)
                .query("nCdServico={nCdServico}")
                .query("sCepOrigem={sCepOrigem}")
                .query("sCepDestino={sCepDestino}")
                .buildAndExpand(queryParams)
                .toUriString();

        try {
            log.info("Calling correios service with params nCdServico = {}, sCepOrigem = {}, sCepDestino = {} ", serviceCode, fromZipCode, toZipCode);
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(uriComponentsBuilder, String.class);
            log.info("Correios service called with status = {}", responseEntity.getStatusCode());
            return responseEntity.getBody();
        } catch (RestClientException e) {
            throw new UnprocessableEntityException();
        }
    }

}
