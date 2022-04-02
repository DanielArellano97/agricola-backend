package com.agricola.backend.models.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.agricola.backend.models.dao.ICuartelDao;
import com.agricola.backend.models.dao.IEncargadoBPADao;
import com.agricola.backend.models.dao.IPredioDao;
import com.agricola.backend.models.entity.Cuartel;
import com.agricola.backend.models.entity.EncargadoBPA;
import com.agricola.backend.models.entity.Predio;


@Service
public class CuartelServiceImpl implements ICuartelService {

	
	@Autowired
	private ICuartelDao cuartelDao;
	
	@Autowired
	private IPredioDao predioDao;
	
	@Autowired
	private IEncargadoBPADao encargadoDao;
	
//	@Override
//	@Transactional(readOnly = true)
//	public List<Cuartel> findAll() {
//		List<Cuartel> list1 = (List<Cuartel>) cuartelDao.findAll();
//	    List<Cuartel> listaFiltrada = list1.stream().filter(item->item.isEstado()).collect(Collectors.toList());
//	    return listaFiltrada;
//	}

	@Override
	@Transactional(readOnly = true)
	public List<Cuartel> findAll() {
		List<Cuartel> list1 = (List<Cuartel>) cuartelDao.findAll();
	    List<Cuartel> listaFiltrada = new ArrayList<Cuartel>();
	    

		for(int i=0; i < list1.size(); i++) {
			if(list1.get(i).isEstado()) {
				EncargadoBPA encargado = encargadoDao.findById(list1.get(i).getRunEncargadoBPA()).orElse(null);
				Predio predio = predioDao.findById(list1.get(i).getIdPredio()).orElse(null);
				list1.get(i).setNombreEncargadoBPA(encargado.getNombre());
				list1.get(i).setNombrePredio(predio.getNombre());
				listaFiltrada.add(list1.get(i));
			}
		}		
		return listaFiltrada;
	}


	@Override
	@Transactional
	public Cuartel save(Cuartel cuartel) {
		cuartel.setEstado(true);
		return cuartelDao.save(cuartel);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		cuartelDao.findById(id).get().setEstado(false);
	}

	@Override
	@Transactional(readOnly = true)
	public Cuartel findById(Long id) {
		return cuartelDao.findById(id).orElse(null);
	}

	@Override
	public Cuartel findCuartelByNombre(String nombre) {
		// TODO Auto-generated method stub
		return cuartelDao.findCuartelByNombre(nombre);
	}

}
