package com.agricola.backend.models.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agricola.backend.models.dao.IAdministradorDao;
import com.agricola.backend.models.dao.ICampoDao;
import com.agricola.backend.models.dao.IDuenoCampoDao;
import com.agricola.backend.models.entity.Administrador;
import com.agricola.backend.models.entity.Campo;
import com.agricola.backend.models.entity.DuenoCampo;

@Service
public class CampoServiceImpl implements ICampoService{
	
	@Autowired
	private ICampoDao campoDao;
	
	@Autowired
	private IAdministradorDao adminDao;
	
	@Autowired
	private IDuenoCampoDao duenoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Campo> findAll() {
		
		List<Campo> listCampos = (List<Campo>) campoDao.findAll();
		List<Campo> listCamposFiltrada = new ArrayList<Campo>();
		
		for(int i=0; i < listCampos.size(); i++) {
			if(listCampos.get(i).isEstado()) {
				Administrador admin = adminDao.findById(listCampos.get(i).getRunAdministradorCampo()).orElse(null);
				DuenoCampo dueno = duenoDao.findById(listCampos.get(i).getRunDuenoCampo()).orElse(null);
				listCampos.get(i).setNombreAdministrador(admin.getNombre());
				listCampos.get(i).setNombreDuenoCampo(dueno.getNombre());
				listCamposFiltrada.add(listCampos.get(i));
			}
		}		
		return listCamposFiltrada;
	}

	@Override
	@Transactional
	public Campo save(Campo campo) {
		campo.setEstado(true);
		return campoDao.save(campo);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		campoDao.findById(id).orElse(null).setEstado(false);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Campo findById(Long id) {
		return campoDao.findById(id).orElse(null);
	}

	@Override
	public Campo findCampoByNombre(String nombre) {
		
		return campoDao.findCampoByNombre(nombre);
	}

	@Override
	public Campo findCampoByDireccion(String direccion) {

		return campoDao.findCampoByDireccion(direccion);
	}

}
