package com.agricola.backend.models.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agricola.backend.models.dao.IFitosanitarioDao;
import com.agricola.backend.models.entity.Fitosanitario;

@Service
public class FitosanitarioServiceImpl implements IFitosanitarioService {

	@Autowired
	private IFitosanitarioDao fitosanitarioDao;

	@Override
	@Transactional(readOnly = true)
	public List<Fitosanitario> findAll() {
		
		List<Fitosanitario> listFitosanitario = (List<Fitosanitario>) fitosanitarioDao.findAll();
		List<Fitosanitario> listFitosanitarioFiltrada = new ArrayList<Fitosanitario>();
		for(int i=0; i < listFitosanitario.size(); i++) {
			if(listFitosanitario.get(i).isEstado()) {
				listFitosanitarioFiltrada.add(listFitosanitario.get(i));
			}
		}		
		return listFitosanitarioFiltrada;
	}

	@Override
	@Transactional
	public Fitosanitario save(Fitosanitario fitosanitario) {
		fitosanitario.setEstado(true);
		return fitosanitarioDao.save(fitosanitario);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		Fitosanitario fitosaniratio = fitosanitarioDao.findById(id).orElse(null);
		fitosaniratio.setEstado(false);
		//fitosanitarioDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Fitosanitario findById(Long id) {
		return fitosanitarioDao.findById(id).orElse(null);
	}

	@Override
	public Fitosanitario findFitosanitarioByNombre(String nombre) {
		return fitosanitarioDao.findFitosanitarioByNombre(nombre);
	}

}
