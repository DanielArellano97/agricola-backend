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
@Table(name="cuarteles")
public class Cuartel implements Serializable{

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cuartel")
	private Long idCuartel;
	
	@Column(length = 30, nullable = false, updatable = true) 
	private String nombre;
	
	@Column(nullable = false, updatable = true) 
	private int hectareas;
	
	@Column(name="tipo_uva",length = 25, nullable = false, updatable = true)
	private String tipoUva;
	
	@Column(name="run_encargado_bpa", nullable = false, updatable = true)
	private String runEncargadoBPA;
	
	@Column(name="id_predio", nullable = false, updatable = true)
	private Long idPredio;
	
	
	@Column(nullable = false, updatable = true)
	private boolean estado;
	
	@JsonInclude()
	@Transient
	private String nombreEncargadoBPA;
	
	@JsonInclude()
	@Transient
	private String nombrePredio;

	public boolean isEstado() {
		return estado;
	}



	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	



	public Long getIdCuartel() {
		return idCuartel;
	}




	public void setIdCuartel(Long idCuartel) {
		this.idCuartel = idCuartel;
	}




	public String getNombre() {
		return nombre;
	}




	public void setNombre(String nombre) {
		this.nombre = nombre;
	}




	public int getHectareas() {
		return hectareas;
	}




	public void setHectareas(int hectareas) {
		this.hectareas = hectareas;
	}




	public String getTipoUva() {
		return tipoUva;
	}




	public void setTipoUva(String tipoUva) {
		this.tipoUva = tipoUva;
	}








	public String getRunEncargadoBPA() {
		return runEncargadoBPA;
	}




	public void setRunEncargadoBPA(String runEncargadoBPA) {
		this.runEncargadoBPA = runEncargadoBPA;
	}




	public Long getIdPredio() {
		return idPredio;
	}




	public void setIdPredio(Long idPredio) {
		this.idPredio = idPredio;
	}




	public String getNombrePredio() {
		return nombrePredio;
	}



	public void setNombrePredio(String nombrePredio) {
		this.nombrePredio = nombrePredio;
	}




	public String getNombreEncargadoBPA() {
		return nombreEncargadoBPA;
	}



	public void setNombreEncargadoBPA(String nombreEncargadoBPA) {
		this.nombreEncargadoBPA = nombreEncargadoBPA;
	}




	private static final long serialVersionUID = 1L;
}
