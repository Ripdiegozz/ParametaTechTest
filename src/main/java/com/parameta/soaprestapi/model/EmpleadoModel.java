package com.parameta.soaprestapi.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

@Getter
@Entity
@Data
public class EmpleadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombres;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
    private Date fechaNacimiento;
    private Date fechaVinculacion;
    private String cargo;
    private Double salario;

    public EmpleadoModel() {
    }

    public EmpleadoModel(Long id, String nombres, String apellidos, String tipoDocumento, String numeroDocumento, Date fechaNacimiento, Date fechaVinculacion, String cargo, Double salario) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaVinculacion = fechaVinculacion;
        this.cargo = cargo;
        this.salario = salario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setFechaVinculacion(Date fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Long getId() {
		return id;
	}

	public String getNombres() {
		return nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public Date getFechaVinculacion() {
		return fechaVinculacion;
	}

	public String getCargo() {
		return cargo;
	}

	public Double getSalario() {
		return salario;
	}

	@Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", nombres=" + nombres + ", apellidos=" + apellidos + ", tipoDocumento=" + tipoDocumento + ", numeroDocumento=" + numeroDocumento + ", fechaNacimiento=" + fechaNacimiento + ", fechaVinculacion=" + fechaVinculacion + ", cargo=" + cargo + ", salario=" + salario + '}';
    }
}