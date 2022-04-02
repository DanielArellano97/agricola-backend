package com.agricola.backend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "campos")
public class Campo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_campo")
	private Long idCampo;

	@Column(length = 30, nullable = false, updatable = true)
	private String nombre;

	@Column(length = 80, nullable = false, updatable = true)
	private String direccion;

	@Column(nullable = false, updatable = true, length = 2)
	private int hectareas;

	@Column(name = "run_dueno_campo", nullable = false, updatable = true)
	private String runDuenoCampo;
	
	@JsonInclude()
	@Transient
	private String nombreDuenoCampo;

	@Column(name = "run_administrador_campo", nullable = false, updatable = true)
	private String runAdministradorCampo;
	
	@JsonInclude()
	@Transient
	private String nombreAdministrador;

	@Column(length = 1, nullable = false, updatable = true)
	private boolean estado;
	
	
	public String getNombreDuenoCampo() {
		return nombreDuenoCampo;
	}

	public void setNombreDuenoCampo(String nombreDuenoCampo) {
		this.nombreDuenoCampo = nombreDuenoCampo;
	}

	public Long getIdCampo() {
		return idCampo;
	}

	public String getNombreAdministrador() {
		return nombreAdministrador;
	}

	public void setNombreAdministrador(String nombreAdministrador) {
		this.nombreAdministrador = nombreAdministrador;
	}

	public void setIdCampo(Long idCampo) {
		this.idCampo = idCampo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getHectareas() {
		return hectareas;
	}

	public void setHectareas(int hectareas) {
		this.hectareas = hectareas;
	}

	public String getRunDuenoCampo() {
		return runDuenoCampo;
	}

	public void setRunDuenoCampo(String runDuenoCampo) {
		this.runDuenoCampo = runDuenoCampo;
	}

	public String getRunAdministradorCampo() {
		return runAdministradorCampo;
	}

	public void setRunAdministradorCampo(String runAdministradorCampo) {
		this.runAdministradorCampo = runAdministradorCampo;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	private static final long serialVersionUID = 1L;

}
