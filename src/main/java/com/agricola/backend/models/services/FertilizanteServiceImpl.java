package com.agricola.backend.models.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.agricola.backend.models.dao.IFertilizanteDao;
import com.agricola.backend.models.entity.Fertilizante;

@Service
public class FertilizanteServiceImpl implements IFertilizanteService{

	@Autowired
	private IFertilizanteDao fertilizanteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Fertilizante> findAll() {
		List<Fertilizante> list1 = (List<Fertilizante>) fertilizanteDao.findAll();
	    List<Fertilizante> listaFiltrada = list1.stream().filter(item->item.isEstado()).collect(Collectors.toList());
	    return listaFiltrada;
	}

	@Override
	@Transactional
	public Fertilizante save(Fertilizante fertilizante) {
		fertilizante.setEstado(true);
		return fertilizanteDao.save(fertilizante);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		fertilizanteDao.findById(id).get().setEstado(false);

	}

	@Override
	@Transactional(readOnly = true)
	public Fertilizante findById(Long id) {
		return fertilizanteDao.findById(id).orElse(null);
	}

	@Override
	public Fertilizante findCuartelByNombre(String nombre) {
		return fertilizanteDao.findCuartelByNombre(nombre);
	}



	 
}
