package com.agricola.backend.models.services;

import java.util.List;

import com.agricola.backend.models.entity.Predio;

public interface IPredioService {
	
	public List<Predio> findAll();
	
	public Predio save(Predio predio);

	public void delete(Long id);

	public Predio findById(Long id);
	
	public Predio findPredioByNombre(String nombre);
}
