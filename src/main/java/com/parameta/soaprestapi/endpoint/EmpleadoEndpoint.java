package com.parameta.soaprestapi.endpoint;

import com.parameta.soaprestapi.gen.*;
import com.parameta.soaprestapi.model.EmpleadoModel;
import com.parameta.soaprestapi.repository.EmpleadoRepository;
import com.parameta.soaprestapi.util.EmpleadoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EmpleadoEndpoint {
    private static final String NAMESPACE_URI = "http://www.parameta.com/soaprestapi/gen";

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoConverter empleadoConverter;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findByNumeroDocumentoRequest")
    @ResponsePayload
    public FindByNumeroDocumentoResponse findByNumeroDocumento(@RequestPayload FindByNumeroDocumentoRequest request) {
        FindByNumeroDocumentoResponse response = new FindByNumeroDocumentoResponse();
        EmpleadoModel empleadoModel = empleadoRepository.findByNumeroDocumento(request.getNumeroDocumento());
        Empleado empleado = empleadoConverter.convertEmpleadoModelToEmpleado(empleadoModel);
        response.setEmpleado(empleado);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveRequest")
    @ResponsePayload
    public SaveResponse save(@RequestPayload SaveRequest request) {
        SaveResponse response = new SaveResponse();
        EmpleadoModel empleadoModel = empleadoConverter.convertEmpleadoToEmpleadoModel(request.getEmpleado());
        empleadoRepository.save(empleadoModel);
        response.setEmpleado(request.getEmpleado());
        return response;
    }
}
