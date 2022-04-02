package com.agricola.backend.models.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.agricola.backend.models.dao.IDuenoCampoDao;
import com.agricola.backend.models.entity.DuenoCampo;

@Service
public class DuenoCampoServiceImpl implements IDuenoCampoService{

	
	@Autowired
	private IDuenoCampoDao duenoCampoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<DuenoCampo> findAll() {
		List<DuenoCampo> list1 = (List<DuenoCampo>) duenoCampoDao.findAll();
	    List<DuenoCampo> listaFiltrada = list1.stream().filter(item->item.isEstado()).collect(Collectors.toList());
	    return listaFiltrada;
	}

	@Override
	@Transactional
	public DuenoCampo save(DuenoCampo duenoCampo) {
		duenoCampo.setEstado(true);
		return duenoCampoDao.save(duenoCampo);
	}

	@Override
	@Transactional
	public void delete(String run) {
		duenoCampoDao.findById(run).get().setEstado(false);
	}

	@Override
	@Transactional(readOnly = true)
	public DuenoCampo findById(String id) {
		return duenoCampoDao.findById(id).orElse(null);
	}
}
