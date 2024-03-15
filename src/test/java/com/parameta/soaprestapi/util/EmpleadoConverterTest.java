package com.parameta.soaprestapi.util;

import com.parameta.soaprestapi.gen.Empleado;
import com.parameta.soaprestapi.model.EmpleadoModel;
import org.junit.jupiter.api.Test;
import org.springframework.expression.ParseException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmpleadoConverterTest {

    @Test
    public void testConvertEmpleadoToEmpleadoModel() throws ParseException, DatatypeConfigurationException, ParseException {
        // Prepare an Empleado object
        Empleado empleado = new Empleado();
        empleado.setId(1);
        empleado.setNombres("John");
        empleado.setApellidos("Doe");
        empleado.setTipoDocumento("CC");
        empleado.setNumeroDocumento("123456789");
        empleado.setCargo("Developer");
        empleado.setSalario(50000.0);

        // Mocking XMLGregorianCalendar
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        
		try {
			date = dateFormat.parse("1990-01-01");
		} catch (java.text.ParseException e) {
			System.out.println("Error parsing date");
		}
		
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toInstant().toString());
        empleado.setFechaNacimiento(xmlGregorianCalendar);
        empleado.setFechaVinculacion(xmlGregorianCalendar);

        // Convert Empleado to EmpleadoModel
        EmpleadoConverter empleadoConverter = new EmpleadoConverter();
        EmpleadoModel empleadoModel = empleadoConverter.convertEmpleadoToEmpleadoModel(empleado);

        // Assert
        assertEquals(empleado.getId(), empleadoModel.getId());
        assertEquals(empleado.getNombres(), empleadoModel.getNombres());
        assertEquals(empleado.getApellidos(), empleadoModel.getApellidos());
        assertEquals(empleado.getTipoDocumento(), empleadoModel.getTipoDocumento());
        assertEquals(empleado.getNumeroDocumento(), empleadoModel.getNumeroDocumento());
        assertEquals(empleado.getCargo(), empleadoModel.getCargo());
        assertEquals(empleado.getSalario(), empleadoModel.getSalario());
    }

    @Test
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
            if (empleadoModel.getFechaNacimiento() != null) {
                empleado.setFechaNacimiento(convertStringToXMLGregorianCalendar(empleadoModel.getFechaNacimiento().toString()));
            }
            if (empleadoModel.getFechaVinculacion() != null) {
                empleado.setFechaVinculacion(convertStringToXMLGregorianCalendar(empleadoModel.getFechaVinculacion().toString()));
            }
        } catch (ParseException | DatatypeConfigurationException e) {
            // Handle the exception or log an error
            e.printStackTrace();
        }

        return empleado;
    }
    
    private XMLGregorianCalendar convertStringToXMLGregorianCalendar(String dateString)
            throws ParseException, DatatypeConfigurationException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        try {
            Date date = dateFormat.parse(dateString);
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (java.text.ParseException e) {
            throw new ParseException(0, "Error al parsear la fecha: " + e.getMessage());
        }
}
}
