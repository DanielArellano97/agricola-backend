package com.agricola.backend.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.agricola.backend.models.entity.Predio;

public interface IPredioDao extends CrudRepository<Predio, Long>{
	
	@Query("SELECT p FROM Predio p WHERE p.nombre = ?1")
	public Predio findPredioByNombre(String nombre);
}
