package com.agricola.backend.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.agricola.backend.models.entity.Campo;

public interface ICampoDao extends CrudRepository<Campo, Long>{
	
	@Query("SELECT c FROM Campo c WHERE c.nombre = ?1")
	public Campo findCampoByNombre(String nombre);
	
	@Query("SELECT c FROM Campo c WHERE c.direccion = ?1")
	public Campo findCampoByDireccion(String direccion);

}
