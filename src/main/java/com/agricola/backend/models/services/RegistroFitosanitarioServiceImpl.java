package com.agricola.backend.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agricola.backend.models.dao.ICuartelDao;
import com.agricola.backend.models.dao.IEncargadoBPADao;
import com.agricola.backend.models.dao.IFitosanitarioDao;
import com.agricola.backend.models.dao.IRegistroFitosanitarioDao;
import com.agricola.backend.models.entity.Cuartel;
import com.agricola.backend.models.entity.EncargadoBPA;
import com.agricola.backend.models.entity.Fitosanitario;
import com.agricola.backend.models.entity.RegistroFitosanitario;

@Service
public class RegistroFitosanitarioServiceImpl implements IRegistroFitosanitarioService {

	@Autowired
	private IRegistroFitosanitarioDao registroFitosanitarioDao;
	
	@Autowired
	private IEncargadoBPADao encargadoDao;
	
	@Autowired
	private IFitosanitarioDao fitoDao;
	
	@Autowired
	private ICuartelDao cuartelDao;

	@Override
	@Transactional(readOnly = true)
	public List<RegistroFitosanitario> findAll() {
		
		List<RegistroFitosanitario> listFito = (List<RegistroFitosanitario>) registroFitosanitarioDao.findAll();
		
		for(int i=0; i< listFito.size(); i++) {
			EncargadoBPA encargado = encargadoDao.findById(listFito.get(i).getRunEncargadoBPA()).orElse(null);
			listFito.get(i).setNombreEncargadoBPA(encargado.getNombre());
			Fitosanitario fito = fitoDao.findById(listFito.get(i).getIdFitosanitario()).orElse(null);
			listFito.get(i).setNombreFitosanitario(fito.getNombreComercial());
			Cuartel cuartel = cuartelDao.findById(listFito.get(i).getIdCuartel()).orElse(null);
			listFito.get(i).setNombreCuartel(cuartel.getNombre());			
		}
		
		return (List<RegistroFitosanitario>) registroFitosanitarioDao.findAll();
	}

	@Override
	@Transactional
	public RegistroFitosanitario save(RegistroFitosanitario registroFitosanitario) {
		return registroFitosanitarioDao.save(registroFitosanitario);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		registroFitosanitarioDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public RegistroFitosanitario findById(Long id) {
		return registroFitosanitarioDao.findById(id).orElse(null);
	}

}
