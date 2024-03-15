package com.parameta.soaprestapi.client;

import com.parameta.soaprestapi.gen.SaveRequest;
import com.parameta.soaprestapi.gen.SaveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

@Component
public class SoapClient {
    private final String uri = "http://localhost:8080/ws/empleados.wsdl";

    private final WebServiceTemplate webServiceTemplate;

    @Autowired
    public SoapClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    // Metodo para guardar un empleado haciendo un request a mi propio servicio SOAP
    public SaveResponse saveEmpleado(SaveRequest request) {
        return (SaveResponse) webServiceTemplate.marshalSendAndReceive(uri, request);
    }
}
