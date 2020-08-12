package com.br.johndeere.msdelivery.controller;


import com.br.johndeere.msdelivery.controller.response.DeliveryResponse;
import com.br.johndeere.msdelivery.service.DeliveryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping(value = "/deadline", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeliveryResponse> getDeadline(@RequestParam("serviceCode") final Integer serviceCode,
                                                        @RequestParam("fromZipCode") final String fromZipCode,
                                                        @RequestParam("toZipCode") final String toZipCode) {
        log.info("Request received");
        return ResponseEntity.ok(deliveryService.retrieveDeliveryDeadline(serviceCode, fromZipCode, toZipCode));
    }
}
