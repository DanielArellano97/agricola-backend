package com.agricola.backend.models.services;

import java.util.List;
import com.agricola.backend.models.entity.RegistroFertilizante;

public interface IRegistroFertilizanteService {

public List <RegistroFertilizante> findAll();
	
	public RegistroFertilizante save(RegistroFertilizante registro);

	public void delete(Long id);

	public RegistroFertilizante findById(Long id);
}
