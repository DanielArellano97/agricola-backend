package com.agricola.backend.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name="registros_fertilizantes")
public class RegistroFertilizante implements Serializable{

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_registro_fertilizante")
	private Long idRegistro;
	

	@Column(name = "metodo_aplicacion", length = 40, nullable = false, updatable = true)
	private String metodoAplicacion;

	@Column(name = "estado_fenologico", length = 40, nullable = false, updatable = true)
	private String estadoFenologico;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable = false, updatable = true)
	private Date fecha;
	
	@Column(name = "cantidad_Aplicada", nullable = false, updatable = true)
	private double cantidadAplicada;

	@Column(name = "tipo_maquinaria", length = 25, nullable = false, updatable = true)
	private String tipoMaquinaria;

	@Column(name="run_encargado_bpa",nullable = false, updatable = true)
	private String runEncargadoBPA;
	
	@JsonInclude()
	@Transient
	private String nombreEncargadoBPA;

	@Column(name="id_fertilizante",nullable = false, updatable = true)
	private Long idFertilizante;
	
	@JsonInclude()
	@Transient
	private String nombreFertilizante;
	
	@Column(name="id_cuartel",nullable = false, updatable = true)
	private Long idCuartel;
	
	@JsonInclude()
	@Transient
	private String nombreCuartel;
	
	public String getNombreEncargadoBPA() {
		return nombreEncargadoBPA;
	}


	public void setNombreEncargadoBPA(String nombreEncargadoBPA) {
		this.nombreEncargadoBPA = nombreEncargadoBPA;
	}


	public String getNombreFertilizante() {
		return nombreFertilizante;
	}


	public void setNombreFertilizante(String nombreFertilizante) {
		this.nombreFertilizante = nombreFertilizante;
	}


	public String getNombreCuartel() {
		return nombreCuartel;
	}


	public void setNombreCuartel(String nombreCuartel) {
		this.nombreCuartel = nombreCuartel;
	}
	
	public Long getIdCuartel() {
		return idCuartel;
	}


	public void setIdCuartel(Long idCuartel) {
		this.idCuartel = idCuartel;
	}


	public Long getIdRegistro() {
		return idRegistro;
	}


	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}


	public String getMetodoAplicacion() {
		return metodoAplicacion;
	}


	public void setMetodoAplicacion(String metodoAplicacion) {
		this.metodoAplicacion = metodoAplicacion;
	}


	public String getEstadoFenologico() {
		return estadoFenologico;
	}


	public void setEstadoFenologico(String estadoFenologico) {
		this.estadoFenologico = estadoFenologico;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public double getCantidadAplicada() {
		return cantidadAplicada;
	}


	public void setCantidadAplicada(double cantidadAplicada) {
		this.cantidadAplicada = cantidadAplicada;
	}


	public String getTipoMaquinaria() {
		return tipoMaquinaria;
	}


	public void setTipoMaquinaria(String tipoMaquinaria) {
		this.tipoMaquinaria = tipoMaquinaria;
	}




	public String getRunEncargadoBPA() {
		return runEncargadoBPA;
	}


	public void setRunEncargadoBPA(String runEncargadoBPA) {
		this.runEncargadoBPA = runEncargadoBPA;
	}


	public Long getIdFertilizante() {
		return idFertilizante;
	}


	public void setIdFertilizante(Long idFertilizante) {
		this.idFertilizante = idFertilizante;
	}


	
	

	private static final long serialVersionUID = 1L;
}
