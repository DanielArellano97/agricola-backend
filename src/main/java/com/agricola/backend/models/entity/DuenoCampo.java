package com.agricola.backend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="duenos_campo")
public class DuenoCampo implements Serializable{

	@Id
	@Column(name="run")
	private String run;
	
	@Column(length = 50, nullable = false, updatable = true) 
	private String nombre;


	@Column(length = 50, nullable = false, updatable = true)
	private String email;

	@Column(length = 15, nullable = false, updatable = true) 
	private String pass;
	
	@Column(nullable = false, updatable = true)
	private boolean estado;
	
	
	

	public boolean isEstado() {
		return estado;
	}



	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	public String getRun() {
		return run;
	}

	public void setRun(String idDuenoCampo) {
		this.run = idDuenoCampo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getEmail() {
		return email;
	}







	public void setEmail(String email) {
		this.email = email;
	}







	public String getPassword() {
		return pass;
	}







	public void setPassword(String pass) {
		this.pass = pass;
	}







	private static final long serialVersionUID = 1L;

	 
}
