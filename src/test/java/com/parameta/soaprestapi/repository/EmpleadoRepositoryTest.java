package com.parameta.soaprestapi.repository;

import com.parameta.soaprestapi.model.EmpleadoModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Date;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class EmpleadoRepositoryTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Test
    void testFindByNumeroDocumento() {
        // Given
        String numeroDocumento = "123456789";
        EmpleadoModel empleadoModel = new EmpleadoModel();
        empleadoModel.setId(1L);
        empleadoModel.setNombres("John");
        empleadoModel.setApellidos("Doe");
        empleadoModel.setNumeroDocumento(numeroDocumento);
        empleadoModel.setFechaNacimiento(new Date(1990-01-01));
        empleadoModel.setFechaVinculacion(new Date(2020-01-01));
        empleadoModel.setCargo("Developer");
        empleadoModel.setSalario(50000.0);
        
        // Save the entity
        empleadoRepository.save(empleadoModel);

        // Mocking the repository method
        when(empleadoRepository.findByNumeroDocumento(numeroDocumento)).thenReturn(empleadoModel);

        // When
        EmpleadoModel foundEmpleado = empleadoRepository.findByNumeroDocumento(numeroDocumento);

        // Then
        assertEquals(empleadoModel, foundEmpleado);
    }

    @Test
    void testFindByNumeroDocumento_NotFound() {
        // Given
        String numeroDocumento = "123456789";

        // Mocking the repository method to return null (not found)
        when(empleadoRepository.findByNumeroDocumento(anyString())).thenReturn(null);

        // When
        EmpleadoModel foundEmpleado = empleadoRepository.findByNumeroDocumento(numeroDocumento);

        // Then
        assertEquals(null, foundEmpleado);
    }
}
