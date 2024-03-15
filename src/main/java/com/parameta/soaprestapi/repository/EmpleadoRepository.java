package com.parameta.soaprestapi.repository;

import com.parameta.soaprestapi.model.EmpleadoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoModel, Long> {
    EmpleadoModel findByNumeroDocumento(String numeroDocumento);
}
