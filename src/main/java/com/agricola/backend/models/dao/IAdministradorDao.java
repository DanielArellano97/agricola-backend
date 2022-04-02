 package com.agricola.backend.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.agricola.backend.models.entity.Administrador;

public interface IAdministradorDao extends CrudRepository<Administrador,String> {
	
	
	@Query("SELECT e FROM Administrador e WHERE e.run = ?1")
	Administrador findAdministradorByRun(String run);

	@Query("SELECT e FROM Administrador e WHERE e.telefono = ?1")
	Administrador findAdministradorByTelefono(String telefono);
	
	
	@Query("SELECT e FROM Administrador e WHERE e.email = ?1")
	Administrador findAdministradorByEmail(String email);
	
	@Query("SELECT e FROM Administrador e WHERE e.pass = ?1")
	Administrador findAdministradorByPass(String pass);
    
	@Query("SELECT e FROM Administrador e WHERE e.nombre = ?1")
	Administrador findAdministradorByNombre(String nombre);

}
