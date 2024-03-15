package com.parameta.soaprestapi.util;

import com.parameta.soaprestapi.gen.Empleado;
import com.parameta.soaprestapi.model.EmpleadoModel;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class EmpleadoConverter {

    public EmpleadoModel convertEmpleadoToEmpleadoModel(Empleado empleado) {
        EmpleadoModel empleadoModel = new EmpleadoModel();

        empleadoModel.setId(empleado.getId());
        empleadoModel.setNombres(empleado.getNombres());
        empleadoModel.setApellidos(empleado.getApellidos());
        empleadoModel.setTipoDocumento(empleado.getTipoDocumento());
        empleadoModel.setNumeroDocumento(empleado.getNumeroDocumento());

        // Convert XMLGregorianCalendar to String
        empleadoModel.setFechaNacimiento(empleado.getFechaNacimiento().toString());
        empleadoModel.setFechaVinculacion(empleado.getFechaVinculacion().toString());
        empleadoModel.setCargo(empleado.getCargo());
        empleadoModel.setSalario(empleado.getSalario());

        return empleadoModel;
    }

    public Empleado convertEmpleadoModelToEmpleado(EmpleadoModel empleadoModel) {
        Empleado empleado = new Empleado();

        empleado.setId(empleadoModel.getId());
        empleado.setNombres(empleadoModel.getNombres());
        empleado.setApellidos(empleadoModel.getApellidos());
        empleado.setTipoDocumento(empleadoModel.getTipoDocumento());
        empleado.setNumeroDocumento(empleadoModel.getNumeroDocumento());
        empleado.setCargo(empleadoModel.getCargo());
        empleado.setSalario(empleadoModel.getSalario());

        try {
            empleado.setFechaNacimiento(convertStringToXMLGregorianCalendar(empleadoModel.getFechaNacimiento()));
            empleado.setFechaVinculacion(convertStringToXMLGregorianCalendar(empleadoModel.getFechaVinculacion()));
        } catch (ParseException | DatatypeConfigurationException | java.text.ParseException e) {
            // Handle the exception or log an error
            e.printStackTrace();
        }

        return empleado;
    }

    private XMLGregorianCalendar convertStringToXMLGregorianCalendar(String dateString)
            throws ParseException, DatatypeConfigurationException, java.text.ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the date format as needed
        Date date = dateFormat.parse(dateString);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toInstant().toString());
    }
}