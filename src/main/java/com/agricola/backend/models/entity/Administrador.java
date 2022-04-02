package com.agricola.backend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="administradores_campo")
public class Administrador  implements Serializable{
	
	@Id
	@Column(name="run")
	private String run;
 
	@Column(length = 50, nullable = false, updatable = true,unique=true) 
	private String nombre;


	@Column(length = 12, nullable = false, updatable = false, unique=true)
	private String telefono;

	@Column(length = 50, nullable = false, updatable = true, unique=true)
	private String email;

	@Column(length = 15, nullable = false, updatable = true,unique=true) 
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



	public void setRun(String id) {
		this.run = id;
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



	public String getPassword() {
		return pass;
	}



	public void setPassword(String password) {
		this.pass = password;
	}

	private static final long serialVersionUID = 1L; 
}
