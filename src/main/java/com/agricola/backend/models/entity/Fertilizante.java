package com.agricola.backend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Laboratorio
 *
 */
@Entity
@Table(name="fertilizantes")
public class Fertilizante implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_fertilizante")
	private Long idFertilizante;
	
	@Column(name = "nombre_comercial", length = 25, nullable = false, updatable = true)
	private String nombreComercial;


	@Column(name = "tipo", length = 25, nullable = false, updatable = true)
	private String tipo;

	@Column(nullable = false,length = 30, updatable = true)
	private String variedad;

	@Column(nullable = false, updatable = true)
	private double concentracion;
	
	@Column(nullable = false, updatable = true)
	private boolean estado;
	
	
	

	public boolean isEstado() {
		return estado;
	}



	public void setEstado(boolean estado) {
		this.estado = estado;
	}



	public Long getIdFertilizante() {
		return idFertilizante;
	}



	public void setIdFertilizante(Long idFertilizante) {
		this.idFertilizante = idFertilizante;
	}



	public String getNombreComercial() {
		return nombreComercial;
	}



	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public String getVariedad() {
		return variedad;
	}



	public void setVariedad(String variedad) {
		this.variedad = variedad;
	}



	public double getConcentracion() {
		return concentracion;
	}



	public void setConcentracion(double concentracion) {
		this.concentracion = concentracion;
	}




	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
