package com.agricola.backend.models.services;

import java.util.List;

import com.agricola.backend.models.entity.Fitosanitario;

public interface IFitosanitarioService {

	public List<Fitosanitario> findAll();

	public Fitosanitario save(Fitosanitario fitosanitario);

	public void delete(Long id);

	public Fitosanitario findById(Long id);
	
	public Fitosanitario findFitosanitarioByNombre(String nombre);

}
