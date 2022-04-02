package com.agricola.backend.models.services;

import java.util.List;

import com.agricola.backend.models.entity.EncargadoBPA;

public interface IEncargadoBPAService {
	
	public List <EncargadoBPA> findAll();
	
	public EncargadoBPA save(EncargadoBPA encargadoBPA);
	
	public void delete(String id);
	
	public EncargadoBPA findById(String id);
	
	public EncargadoBPA findEncargadoByNombre(String nombre);
	
	public EncargadoBPA findEncargadoByRun(String run);
	
	public EncargadoBPA findEncargadoByTelefono(String telefono);
	
	public EncargadoBPA findEncargadoByEmail(String email);
	
	public EncargadoBPA findEncargadoByPass(String pass);

}
