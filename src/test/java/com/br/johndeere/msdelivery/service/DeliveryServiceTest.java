package com.br.johndeere.msdelivery.service;


import com.br.johndeere.msdelivery.controller.response.DeliveryResponse;
import com.br.johndeere.msdelivery.exceptions.UnprocessableEntityException;
import com.br.johndeere.msdelivery.integration.CorreiosIntegration;
import com.br.johndeere.msdelivery.integration.response.CServicoResponse;
import com.br.johndeere.msdelivery.integration.response.CorreiosResponse;
import com.br.johndeere.msdelivery.integration.response.ServicosResponse;
import com.br.johndeere.msdelivery.utils.XmlParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({XmlParser.class})
@PowerMockRunnerDelegate(SpringRunner.class)
public class DeliveryServiceTest {

    @InjectMocks
    private DeliveryService deliveryService;
    @Mock
    private CorreiosIntegration correiosIntegration;

    private DeliveryResponse deliveryResponse;
    private Integer serviceCode;
    private String fromZipCode;
    private String toZipeCode;
    private String response;

    @Test
    public void retrieveDeliveryDeadLineWithSuccess() throws Exception {
        givenDeliveryDeadlineCorrectParams();
        whenCallCorreiosIntegrationSuccessfully();
        whenCallXmlMapperSuccessfully();
        whenCallRetrieveDeliveryDeadlineSuccessfully();
        thenExpectDeadlineReturnSuccess();
    }

    @Test(expected = UnprocessableEntityException.class)
    public void bussinesException() throws Exception {
        givenDeliveryDeadlineCorrectParams();
        whenCallCorreiosIntegrationWithError();
        whenCallXmlMapperWithError();
        whenCallRetrieveDeliveryDeadlineSuccessfully();

    }

    /**
     * GIVEN
     */
    private void givenDeliveryDeadlineCorrectParams() {
        serviceCode = 04014;
        fromZipCode = "13348758";
        toZipeCode = "13337300";
    }

    /**
     * WHEN
     */
    private void whenCallCorreiosIntegrationSuccessfully() {
        response = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<cResultado xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://tempuri.org/\">\n" +
                "    <Servicos>\n" +
                "        <cServico>\n" +
                "            <Codigo>4014</Codigo>\n" +
                "            <PrazoEntrega>3</PrazoEntrega>\n" +
                "            <EntregaDomiciliar>S</EntregaDomiciliar>\n" +
                "            <EntregaSabado>S</EntregaSabado>\n" +
                "            <Erro />\n" +
                "            <MsgErro />\n" +
                "            <obsFim />\n" +
                "            <DataMaxEntrega>17/08/2020</DataMaxEntrega>\n" +
                "        </cServico>\n" +
                "    </Servicos>\n" +
                "</cResultado>";
        when(correiosIntegration.getDeadline(serviceCode, fromZipCode, toZipeCode)).thenReturn(response);
    }

    private void whenCallCorreiosIntegrationWithError() {
        response = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<cResultado xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://tempuri.org/\">\n" +
                "    <Servicos>\n" +
                "        <cServico>\n" +
                "            <Codigo>4014</Codigo>\n" +
                "            <PrazoEntrega>3</PrazoEntrega>\n" +
                "            <EntregaDomiciliar>S</EntregaDomiciliar>\n" +
                "            <EntregaSabado>S</EntregaSabado>\n" +
                "            <Erro>001</Erro>\n" +
                "            <MsgErro>Código de serviço inválido</MsgErro>\n" +
                "            <obsFim />\n" +
                "            <DataMaxEntrega>17/08/2020</DataMaxEntrega>\n" +
                "        </cServico>\n" +
                "    </Servicos>\n" +
                "</cResultado>";
        when(correiosIntegration.getDeadline(serviceCode, fromZipCode, toZipeCode)).thenReturn(response);
    }

    private void whenCallXmlMapperWithError() {
        CServicoResponse cServicoResponse = CServicoResponse.builder()
                .erro("001")
                .msgErro("Código de serviço inválido")
                .build();

        ServicosResponse servicosResponse = ServicosResponse.builder()
                .cServico(cServicoResponse)
                .build();

        CorreiosResponse correiosResponse = CorreiosResponse.builder()
                .servicos(servicosResponse)
                .build();

        PowerMockito.mockStatic(XmlParser.class);
        when(XmlParser.xmlUnmarshall(response, CorreiosResponse.class)).thenReturn(Optional.of(correiosResponse));
    }

    private void whenCallXmlMapperSuccessfully() throws JsonProcessingException {
        CServicoResponse cServicoResponse = CServicoResponse.builder()
                .dataMaxEntrega("12/08/2020")
                .build();

        ServicosResponse servicosResponse = ServicosResponse.builder()
                .cServico(cServicoResponse)
                .build();

        CorreiosResponse correiosResponse = CorreiosResponse.builder()
                .servicos(servicosResponse)
                .build();

        PowerMockito.mockStatic(XmlParser.class);
        when(XmlParser.xmlUnmarshall(response, CorreiosResponse.class)).thenReturn(Optional.of(correiosResponse));
    }

    private void whenCallRetrieveDeliveryDeadlineSuccessfully() {
        deliveryResponse = deliveryService.retrieveDeliveryDeadline(serviceCode, fromZipCode, toZipeCode);
    }


    /**
     * THEN
     */
    private void thenExpectDeadlineReturnSuccess() {
        assertNotNull(deliveryResponse.getDeliveryDeadLine());
    }

}
