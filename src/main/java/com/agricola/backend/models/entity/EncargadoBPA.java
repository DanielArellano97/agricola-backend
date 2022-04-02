package com.agricola.backend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "encargados_bpa")
public class EncargadoBPA implements Serializable {

	@Id
	@Column(name = "run")
	private String run;

	// length para definir tamaño, updatable para si es actualizable y nullable para
	// ver si acepta null
	@Column(length = 30, nullable = false, updatable = true)
	private String nombre;

	@Column(length = 12, nullable = false, updatable = false)
	private String telefono;

	@Column(length = 40, nullable = false, updatable = true)
	private String email;

	@Column(length = 8, nullable = false, updatable = true)
	private String pass;
	
	@Column(length = 1, nullable = false, updatable = true)
	private boolean estado;


	public String getRun() {
		return run;
	}


	public void setRun(String run) {
		this.run = run;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPass() {
		return pass;
	}


	public void setPass(String pass) {
		this.pass = pass;
	}


	public boolean isEstado() {
		return estado;
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}


	private static final long serialVersionUID = 1L;
}
