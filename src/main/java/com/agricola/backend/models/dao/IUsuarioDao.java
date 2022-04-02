package com.agricola.backend.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.agricola.backend.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario,Long>{
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsername(String username);

	@Query("select u from Usuario u where u.email=?1")
	public Usuario findByEmail(String email);



}