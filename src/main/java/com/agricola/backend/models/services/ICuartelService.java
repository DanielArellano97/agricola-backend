package com.agricola.backend.models.services;

import java.util.List;

import com.agricola.backend.models.entity.Cuartel;

public interface ICuartelService {

	public List <Cuartel> findAll();
	
	public Cuartel save(Cuartel cuartel);

	public void delete(Long id);

	public Cuartel findById(Long id);

	public Cuartel findCuartelByNombre(String nombre);
}
