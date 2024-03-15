package com.parameta.soaprestapi.controller;

import com.parameta.soaprestapi.client.SoapClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parameta.soaprestapi.gen.Empleado;
import com.parameta.soaprestapi.gen.SaveRequest;
import com.parameta.soaprestapi.gen.SaveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
    // Inyectar el servicio de empleado
    private final SoapClient soapClient;

    @Autowired
    public EmpleadoController(SoapClient soapClient) {
        this.soapClient = soapClient;
    }

    // Metodo REST para guardar un empleado
    @GetMapping("/registrar")
    public ResponseEntity<Object> registrarEmpleado(
            @RequestParam String nombres,
            @RequestParam String apellidos,
            @RequestParam String tipoDocumento,
            @RequestParam String numeroDocumento,
            @RequestParam String fechaNacimientoStr,
            @RequestParam String fechaVinculacionStr,
            @RequestParam String cargo,
            @RequestParam Double salario
    ) {
        try {
            SaveRequest request = new SaveRequest();

            // Validar que los parámetros no estén vacíos
            if (nombres.isEmpty() || apellidos.isEmpty() || tipoDocumento.isEmpty() || numeroDocumento.isEmpty() ||
                    fechaNacimientoStr.isEmpty() || fechaVinculacionStr.isEmpty() || cargo.isEmpty() || salario == null) {
                return ResponseEntity.badRequest().body("Todos los campos son obligatorios.");
            }

            // Validar que el salario sea mayor a 0
            if (salario <= 0) {
                return ResponseEntity.badRequest().body("El salario debe ser mayor a 0.");
            }

            // Validar que las fechas vengan en formato correcto
            try {
                LocalDate.parse(fechaNacimientoStr);
                LocalDate.parse(fechaVinculacionStr);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Las fechas deben tener el formato yyyy-MM-dd.");
            }

            // Convertir las fechas de texto a objetos LocalDate
            LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
            LocalDate fechaVinculacion = LocalDate.parse(fechaVinculacionStr);

            // Validar que el empleado sea mayor de edad
            LocalDate fechaActual = LocalDate.now();

            Period edad = Period.between(fechaNacimiento, fechaActual);

            if (edad.getYears() < 18) {
                return ResponseEntity.badRequest().body("El empleado debe ser mayor de edad.");
            }

            // Convertir las fechas a XMLGregorianCalendar
            XMLGregorianCalendar xmlFechaNacimiento = convertirAFechaXML(fechaNacimiento);
            XMLGregorianCalendar xmlFechaVinculacion = convertirAFechaXML(fechaVinculacion);

            // Crear el objeto Empleado
            Empleado empleado = new Empleado();
            empleado.setNombres(nombres);
            empleado.setApellidos(apellidos);
            empleado.setTipoDocumento(tipoDocumento);
            empleado.setNumeroDocumento(numeroDocumento);
            empleado.setFechaNacimiento(xmlFechaNacimiento);
            empleado.setFechaVinculacion(xmlFechaVinculacion);
            empleado.setCargo(cargo);
            empleado.setSalario(salario);

            // Agregar el empleado al objeto de respuesta
            request.setEmpleado(empleado);

            // Invocar el servicio SOAP para almacenar el empleado en la base de datos MySQL
            SaveResponse response = soapClient.saveEmpleado(request);

            // Calcular tiempo de vinculación a la compañía y edad actual del empleado
            Period tiempoVinculacion = Period.between(fechaVinculacion, fechaActual);
            Period edadActual = Period.between(fechaNacimiento, fechaActual);

            // Construir la respuesta en formato JSON con la información adicional
            ObjectMapper objectMapper = new ObjectMapper();

            String jsonResponse = objectMapper.writeValueAsString(
                    new HashMap<String, Object>() {{
                        put("nombres", response.getEmpleado().getNombres());
                        put("apellidos", response.getEmpleado().getApellidos());
                        put("tipoDocumento", response.getEmpleado().getTipoDocumento());
                        put("numeroDocumento", response.getEmpleado().getNumeroDocumento());
                        put("fechaNacimiento", fechaNacimientoStr);
                        put("fechaVinculacion",fechaVinculacionStr);
                        put("cargo", response.getEmpleado().getCargo());
                        put("salario", response.getEmpleado().getSalario());
                        put("tiempoVinculacion", tiempoVinculacion.getYears() + " años, " + tiempoVinculacion.getMonths() + " meses y " + tiempoVinculacion.getDays() + " días");
                        put("edadActual", edadActual.getYears() + " años, " + edadActual.getMonths() + " meses y " + edadActual.getDays() + " días");
                    }}
            );

            // Responder con la información del empleado en formato JSON
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud.");
        }
    }

    // Método para convertir un objeto LocalDate a XMLGregorianCalendar
    private XMLGregorianCalendar convertirAFechaXML(LocalDate fecha) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(fecha.toString());
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
