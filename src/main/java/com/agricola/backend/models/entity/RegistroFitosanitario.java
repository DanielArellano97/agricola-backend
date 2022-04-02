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
@Table(name = "registros_fitosanitarios")
public class RegistroFitosanitario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_registro_fitosanitario")
	private Long idRegistroFitosanitario;

	// length para definir tamaño, updatable para si es actualizable y nullable para ver si acepta null
	@Column(name = "tipo_maquinaria", length = 30, nullable = false, updatable = true)
	private String tipoMaquinaria;

	@Column(name = "estado_fenologico", length = 30, nullable = false, updatable = true)
	private String estadoFenologico;

	@Column(name = "dosis", nullable = false, updatable = true, length = 6)
	private Double dosis;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false, updatable = true)
	private Date fecha;

	@Column(name = "hora_termino", length = 5, nullable = false, updatable = true)
	private String horaTermino;

	@Column(name = "condiciones_metereologicas", length = 30, nullable = false, updatable = true)
	private String condicionesMetereologicas;

	@Column(name = "run_encargado_bpa", nullable = false, updatable = true)
	private String runEncargadoBPA;
	
	@JsonInclude()
	@Transient
	private String nombreEncargadoBPA;

	@Column(name = "id_fitosanitario", nullable = false, updatable = true)
	private Long idFitosanitario;
	
	@JsonInclude()
	@Transient
	private String nombreFitosanitario;
	
	@Column(name = "id_cuartel", nullable = false, updatable = true)
	private Long idCuartel;
	
	@JsonInclude()
	@Transient
	private String nombreCuartel;
	
	

	public String getRunEncargadoBPA() {
		return runEncargadoBPA;
	}

	public void setRunEncargadoBPA(String runEncargadoBPA) {
		this.runEncargadoBPA = runEncargadoBPA;
	}

	public String getNombreEncargadoBPA() {
		return nombreEncargadoBPA;
	}

	public void setNombreEncargadoBPA(String nombreEncargadoBPA) {
		this.nombreEncargadoBPA = nombreEncargadoBPA;
	}

	public String getNombreFitosanitario() {
		return nombreFitosanitario;
	}

	public void setNombreFitosanitario(String nombreFitosanitario) {
		this.nombreFitosanitario = nombreFitosanitario;
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

	public Long getIdRegistroFitosanitario() {
		return idRegistroFitosanitario;
	}

	public void setIdRegistroFitosanitario(Long idRegistroFitosanitario) {
		this.idRegistroFitosanitario = idRegistroFitosanitario;
	}

	public String getTipoMaquinaria() {
		return tipoMaquinaria;
	}

	public void setTipoMaquinaria(String tipoMaquinaria) {
		this.tipoMaquinaria = tipoMaquinaria;
	}

	public String getEstadoFenologico() {
		return estadoFenologico;
	}

	public void setEstadoFenologico(String estadoFenologico) {
		this.estadoFenologico = estadoFenologico;
	}

	public Double getDosis() {
		return dosis;
	}

	public void setDosis(Double dosis) {
		this.dosis = dosis;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHoraTermino() {
		return horaTermino;
	}

	public void setHoraTermino(String horaTermino) {
		this.horaTermino = horaTermino;
	}

	public String getCondicionesMetereologicas() {
		return condicionesMetereologicas;
	}

	public void setCondicionesMetereologicas(String condicionesMetereologicas) {
		this.condicionesMetereologicas = condicionesMetereologicas;
	}

	public String getIdEncargadoBPA() {
		return runEncargadoBPA;
	}

	public void setIdEncargadoBPA(String idEncargadoBPA) {
		this.runEncargadoBPA = idEncargadoBPA;
	}

	public Long getIdFitosanitario() {
		return idFitosanitario;
	}

	public void setIdFitosanitario(Long idFitosanitario) {
		this.idFitosanitario = idFitosanitario;
	}

	private static final long serialVersionUID = 1L;

}
