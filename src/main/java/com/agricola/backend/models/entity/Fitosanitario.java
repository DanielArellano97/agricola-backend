package com.agricola.backend.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fitosanitarios")
public class Fitosanitario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_fitosanitario")
	private Long idFitosanitario;

	// length para definir tamaño, updatable para si es actualizable y nullable para
	// ver si acepta null
	@Column(name = "nombre_comercial", length = 30, nullable = false, updatable = true)
	private String nombreComercial;

	@Column(length = 30, nullable = false, updatable = true)
	private String objetivo;
	
	@Column(length = 30, nullable = false, updatable = true)
	private String tipo;

	@Column(name = "ingrediente_activo", length = 30, nullable = false, updatable = true)
	private String ingredienteActivo;

	@Column(nullable = false, updatable = true, length = 6)
	private Double mojamiento;

	@Column(nullable = false, updatable = true, length = 2)
	private int carencia;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Column(length = 1, nullable = false, updatable = true)
	private boolean estado;

	public Long getIdFitosanitario() {
		return idFitosanitario;
	}

	public void setIdFitosanitario(Long idFitosanitario) {
		this.idFitosanitario = idFitosanitario;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getIngredienteActivo() {
		return ingredienteActivo;
	}

	public void setIngredienteActivo(String ingredienteActivo) {
		this.ingredienteActivo = ingredienteActivo;
	}

	public double getMojamiento() {
		return mojamiento;
	}

	public void setMojamiento(Double mojamiento) {
		this.mojamiento = mojamiento;
	}

	public int getCarencia() {
		return carencia;
	}

	public void setCarencia(int carencia) {
		this.carencia = carencia;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	private static final long serialVersionUID = 1L;

}
