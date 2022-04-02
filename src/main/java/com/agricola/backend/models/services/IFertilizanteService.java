package com.agricola.backend.models.services;

import java.util.List;
import com.agricola.backend.models.entity.Fertilizante;

public interface IFertilizanteService {

    public List <Fertilizante> findAll();
	
	public Fertilizante save(Fertilizante fertilizante);

	public void delete(Long id);

	public Fertilizante findById(Long id);

	public Fertilizante findCuartelByNombre(String nombre);

}
