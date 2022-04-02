package com.agricola.backend.models.services;

import java.util.Optional;

import com.agricola.backend.models.entity.Usuario;

public interface IUsuarioService {

	
	public Usuario save(Usuario user);

	void delete(Usuario usuario);
	
	public Long findByRun(String run);

	public Optional<Usuario> findById(Long id);

	public Usuario findByUsername(String username);
	
	public Usuario findByEmail(String email);

}

