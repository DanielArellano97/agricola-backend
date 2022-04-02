package com.agricola.backend.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agricola.backend.models.dao.ICuartelDao;
import com.agricola.backend.models.dao.IEncargadoBPADao;
import com.agricola.backend.models.dao.IFertilizanteDao;
import com.agricola.backend.models.dao.IRegistroFertilizanteDao;
import com.agricola.backend.models.entity.Cuartel;
import com.agricola.backend.models.entity.EncargadoBPA;
import com.agricola.backend.models.entity.Fertilizante;
import com.agricola.backend.models.entity.RegistroFertilizante;

@Service
public class RegistroFertilizanteServiceImpl implements IRegistroFertilizanteService{

	
	@Autowired
	private IRegistroFertilizanteDao registroFertilizanteDao;
	
	@Autowired
	private IEncargadoBPADao encargadoDao;
	
	@Autowired
	private IFertilizanteDao fertilizanteDao;
	
	@Autowired
	private ICuartelDao cuartelDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<RegistroFertilizante> findAll() {
		
		List<RegistroFertilizante> listFerti = (List<RegistroFertilizante>) registroFertilizanteDao.findAll();
		
		for(int i=0; i< listFerti.size(); i++) {
			EncargadoBPA encargado = encargadoDao.findById(listFerti.get(i).getRunEncargadoBPA()).orElse(null);
			listFerti.get(i).setNombreEncargadoBPA(encargado.getNombre());
			Fertilizante fito = fertilizanteDao.findById(listFerti.get(i).getIdFertilizante()).orElse(null);
			listFerti.get(i).setNombreFertilizante(fito.getNombreComercial());
			Cuartel cuartel = cuartelDao.findById(listFerti.get(i).getIdCuartel()).orElse(null);
			listFerti.get(i).setNombreCuartel(cuartel.getNombre());			
		}
		
		return (List<RegistroFertilizante>) registroFertilizanteDao.findAll();
	}

	@Override
	@Transactional
	public RegistroFertilizante save(RegistroFertilizante registro) {
		return registroFertilizanteDao.save(registro);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		registroFertilizanteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public RegistroFertilizante findById(Long id) {
		return registroFertilizanteDao.findById(id).orElse(null);
	}

}
