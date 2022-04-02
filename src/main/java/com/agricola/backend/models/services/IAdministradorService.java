package com.agricola.backend.models.services;

import java.util.List;

import com.agricola.backend.models.entity.Administrador;


public interface IAdministradorService {

	public List <Administrador> findAll();
	
	public Administrador save(Administrador adm);

	public void delete(String id);

	public Administrador findById(String run);

	Administrador findAdministradorByEmail(String email);

	Administrador findAdministradorByPass(String pass);

	Administrador findAdministradorByTelefono(String telefono);

	Administrador findAdministradorByRun(String run);

	public Object findAdministradorByNombre(String run);

	
}
