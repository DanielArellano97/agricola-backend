package com.agricola.backend.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.agricola.backend.models.entity.Cuartel;


public interface ICuartelDao extends CrudRepository<Cuartel, Long> {

	@Query("SELECT e FROM Cuartel e WHERE e.nombre = ?1")
	Cuartel findCuartelByNombre(String nombre);

}
