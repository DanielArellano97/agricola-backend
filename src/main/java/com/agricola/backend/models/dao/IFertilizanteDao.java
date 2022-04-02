package com.agricola.backend.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.agricola.backend.models.entity.Fertilizante;


public interface IFertilizanteDao extends CrudRepository<Fertilizante,Long>{

	@Query("SELECT e FROM Fertilizante e WHERE e.nombreComercial = ?1")
	Fertilizante findCuartelByNombre(String nombre);

}
