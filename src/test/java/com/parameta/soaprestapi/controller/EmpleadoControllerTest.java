package com.parameta.soaprestapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parameta.soaprestapi.client.SoapClient;
import com.parameta.soaprestapi.gen.Empleado;
import com.parameta.soaprestapi.gen.SaveResponse;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.xml.datatype.DatatypeConfigurationException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class EmpleadoControllerTest {

	@Mock
	private SoapClient soapClient;

	@InjectMocks
	private EmpleadoController empleadoController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testRegistrarEmpleado_Success()
			throws DatatypeConfigurationException, JsonProcessingException, JSONException {
		// Mocking the SOAP response
		Empleado empleado = new Empleado();
		empleado.setNombres("John");
		empleado.setApellidos("Doe");
		empleado.setTipoDocumento("CC");
		empleado.setNumeroDocumento("123456789");
		empleado.setCargo("Developer");
		empleado.setSalario(50000.0);

		SaveResponse soapResponse = new SaveResponse();
		soapResponse.setEmpleado(empleado);

		when(soapClient.saveEmpleado(any())).thenReturn(soapResponse);

		// Prepare the input parameters for the controller method
		String nombres = "John";
		String apellidos = "Doe";
		String tipoDocumento = "CC";
		String numeroDocumento = "123456789";
		String fechaNacimientoStr = "1990-01-01";
		String fechaVinculacionStr = "2020-01-01";
		String cargo = "Developer";
		Double salario = 50000.0;

		// Call the controller method
		ResponseEntity<Object> responseEntity = empleadoController.registrarEmpleado(nombres, apellidos, tipoDocumento,
				numeroDocumento, fechaNacimientoStr, fechaVinculacionStr, cargo, salario);

		// Assert the response
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

		// Assert the response body
		Map<String, Object> expectedResponseBody = new HashMap<>();
		expectedResponseBody.put("nombres", "John");
		expectedResponseBody.put("apellidos", "Doe");
		expectedResponseBody.put("tipoDocumento", "CC");
		expectedResponseBody.put("numeroDocumento", "123456789");
		expectedResponseBody.put("fechaNacimiento", "1990-01-01");
		expectedResponseBody.put("fechaVinculacion", "2020-01-01");
		expectedResponseBody.put("cargo", "Developer");
		expectedResponseBody.put("salario", 50000.0);

		ObjectMapper objectMapper = new ObjectMapper();
		expectedResponseBody.put("tiempoVinculacion", "4 años, 2 meses y 14 días");
		expectedResponseBody.put("edadActual", "34 años, 2 meses y 14 días");
		String expectedJsonResponse = objectMapper.writeValueAsString(expectedResponseBody);
		JSONAssert.assertEquals(expectedJsonResponse, (String) responseEntity.getBody(), false);
		assertEquals(expectedJsonResponse, responseEntity.getBody());
	}

	@Test
	public void testRegistrarEmpleado_InvalidSalary() {
		// Prepare the input parameters for the controller method
		String nombres = "John";
		String apellidos = "Doe";
		String tipoDocumento = "CC";
		String numeroDocumento = "123456789";
		String fechaNacimientoStr = "1990-01-01";
		String fechaVinculacionStr = "2020-01-01";
		String cargo = "Developer";
		Double salario = -50000.0;

		// Call the controller method
		ResponseEntity<Object> responseEntity = empleadoController.registrarEmpleado(nombres, apellidos, tipoDocumento,
				numeroDocumento, fechaNacimientoStr, fechaVinculacionStr, cargo, salario);

		// Assert the response
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("El salario debe ser mayor a 0.", responseEntity.getBody());
	}

	@Test
	public void testRegistrarEmpleado_EmptyFields() {
		// Prepare the input parameters for the controller method
		String nombres = "John";
		String apellidos = "";
		String tipoDocumento = "CC";
		String numeroDocumento = "123456789";
		String fechaNacimientoStr = "1990-01-01";
		String fechaVinculacionStr = "2020-01-01";
		String cargo = "Developer";
		Double salario = 50000.0;

		// Call the controller method
		ResponseEntity<Object> responseEntity = empleadoController.registrarEmpleado(nombres, apellidos, tipoDocumento,
				numeroDocumento, fechaNacimientoStr, fechaVinculacionStr, cargo, salario);

		// Assert the response
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("Ningún campo puede estar vacío.", responseEntity.getBody());
	}

	@Test
	public void testRegistrarEmpleado_InvalidDateFormat() {
		// Prepare the input parameters for the controller method
		String nombres = "John";
		String apellidos = "Doe";
		String tipoDocumento = "CC";
		String numeroDocumento = "123456789";
		String fechaNacimientoStr = "19920-01-01";
		String fechaVinculacionStr = "2020-01-021";
		String cargo = "Developer";
		Double salario = 50000.0;

		// Call the controller method
		ResponseEntity<Object> responseEntity = empleadoController.registrarEmpleado(nombres, apellidos, tipoDocumento,
				numeroDocumento, fechaNacimientoStr, fechaVinculacionStr, cargo, salario);

		// Assert the response
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("Las fechas deben tener el formato yyyy-MM-dd.", responseEntity.getBody());
	}

	@Test
	public void testRegistrarEmpleado_FutureDates() {
		// Prepare the input parameters for the controller method
		String nombres = "John";
		String apellidos = "Doe";
		String tipoDocumento = "CC";
		String numeroDocumento = "123456789";
		String fechaNacimientoStr = "2006-01-01";
		String fechaVinculacionStr = "2026-01-01";
		String cargo = "Developer";
		Double salario = 50000.0;

		// Call the controller method
		ResponseEntity<Object> responseEntity = empleadoController.registrarEmpleado(nombres, apellidos, tipoDocumento,
				numeroDocumento, fechaNacimientoStr, fechaVinculacionStr, cargo, salario);

		// Assert the response
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("Las fechas no pueden ser mayores a la fecha actual.", responseEntity.getBody());

	}
	
	@Test
	public void testRegistrarEmpleado_Underage() {
		// Prepare the input parameters for the controller method
		String nombres = "John";
		String apellidos = "Doe";
		String tipoDocumento = "CC";
		String numeroDocumento = "123456789";
		String fechaNacimientoStr = "2010-01-01";
		String fechaVinculacionStr = "2020-01-01";
		String cargo = "Developer";
		Double salario = 50000.0;

		// Call the controller method
		ResponseEntity<Object> responseEntity = empleadoController.registrarEmpleado(nombres, apellidos, tipoDocumento,
				numeroDocumento, fechaNacimientoStr, fechaVinculacionStr, cargo, salario);

		// Assert the response
		assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		assertEquals("El empleado debe ser mayor de edad.", responseEntity.getBody());
	}
}
