package com.agricola.backend.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.agricola.backend.models.entity.EncargadoBPA;

public interface IEncargadoBPADao extends CrudRepository<EncargadoBPA, String>{
	
	@Query("SELECT e FROM EncargadoBPA e WHERE e.nombre = ?1")
	public EncargadoBPA findEncargadoByNombre(String nombre);
	
	@Query("SELECT e FROM EncargadoBPA e WHERE e.run = ?1")
	public EncargadoBPA findEncargadoByRun(String run);
	
	@Query("SELECT e FROM EncargadoBPA e WHERE e.telefono = ?1")
	public EncargadoBPA findEncargadoByTelefono(String telefono);
	
	@Query("SELECT e FROM EncargadoBPA e WHERE e.email = ?1")
	public EncargadoBPA findEncargadoByEmail(String email);
	
	@Query("SELECT e FROM EncargadoBPA e WHERE e.pass = ?1")
	public EncargadoBPA findEncargadoByPass(String pass);

}
