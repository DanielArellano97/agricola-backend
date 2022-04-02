package com.agricola.backend.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.agricola.backend.models.entity.Fitosanitario;

public interface IFitosanitarioDao extends CrudRepository<Fitosanitario, Long> {
	
	@Query("SELECT f FROM Fitosanitario f WHERE f.nombreComercial = ?1")
	public Fitosanitario findFitosanitarioByNombre(String nombre);

}
