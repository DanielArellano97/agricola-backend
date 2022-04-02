package com.agricola.backend.models.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agricola.backend.models.dao.IEncargadoBPADao;
import com.agricola.backend.models.entity.EncargadoBPA;

@Service
public class EncargadoBPAServiceImpl implements IEncargadoBPAService {

	@Autowired
	private IEncargadoBPADao encargadoBPADao;

	@Override
	@Transactional(readOnly = true)
	public List<EncargadoBPA> findAll() {
		
		List<EncargadoBPA> listEncargadosBPA = (List<EncargadoBPA>) encargadoBPADao.findAll();
		List<EncargadoBPA> listEncargadosBPAFiltrada = new ArrayList<EncargadoBPA>();
		
		for(int i=0; i < listEncargadosBPA.size(); i++) {
			if(listEncargadosBPA.get(i).isEstado()) {
				listEncargadosBPAFiltrada.add(listEncargadosBPA.get(i));
			}
		}		
		return listEncargadosBPAFiltrada;
	}

	@Override
	@Transactional
	public EncargadoBPA save(EncargadoBPA encargadoBPA) {
		encargadoBPA.setEstado(true);
		return encargadoBPADao.save(encargadoBPA);
	}

	@Override
	@Transactional
	public void delete(String run) {
		encargadoBPADao.findById(run).orElse(null).setEstado(false);
	}

	@Override
	@Transactional(readOnly = true)
	public EncargadoBPA findById(String run) {
		return encargadoBPADao.findById(run).orElse(null);
	}

	@Override
	public EncargadoBPA findEncargadoByRun(String run) {
		return encargadoBPADao.findEncargadoByRun(run);
	}

	@Override
	public EncargadoBPA findEncargadoByTelefono(String telefono) {
		return encargadoBPADao.findEncargadoByTelefono(telefono);
	}

	@Override
	public EncargadoBPA findEncargadoByEmail(String email) {
		return encargadoBPADao.findEncargadoByEmail(email);
	}

	@Override
	public EncargadoBPA findEncargadoByPass(String pass) {
		return encargadoBPADao.findEncargadoByPass(pass);
	}

	@Override
	public EncargadoBPA findEncargadoByNombre(String nombre) {
		return encargadoBPADao.findEncargadoByNombre(nombre);
	}

}
