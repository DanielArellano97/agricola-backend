package com.agricola.backend.models.services;

import java.util.List;

import com.agricola.backend.models.entity.Campo;

public interface ICampoService {
	
	public List<Campo> findAll();
	
	public Campo save(Campo campo);

	public void delete(Long id);

	public Campo findById(Long id);
	
	public Campo findCampoByNombre(String nombre);
	
	public Campo findCampoByDireccion(String direccion);

}
