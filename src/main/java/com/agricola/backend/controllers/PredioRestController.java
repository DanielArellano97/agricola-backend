package com.agricola.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.agricola.backend.models.entity.Predio;
import com.agricola.backend.models.services.IPredioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class PredioRestController {

	@Autowired
	private IPredioService predioService;

	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@GetMapping("/predios")
	public List<Predio> listaPredios() {
		return predioService.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@GetMapping("/predios/{id}") 
	public ResponseEntity<?> buscarPredio(@PathVariable Long id) {
		
		Predio predio = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			predio = predioService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (predio == null) {
			response.put("mensaje", "El Predio con ID : "+ id +" no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Predio>(predio, HttpStatus.OK);
	}

	
	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@PostMapping("/predios") 
	public ResponseEntity<?> crearPredio(@RequestBody Predio predio) {
		
		Map<String, Object> response = new HashMap<>();
		List<Predio> predios = predioService.findAll();
		
		for(int i=0; i<predios.size(); i++) {
			if(predio.getNombre().trim().equalsIgnoreCase(predios.get(i).getNombre()) && predios.get(i).isEstado() == true) {
				response.put("mensaje", "El nombre del predio ya existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}

		Predio predio2 = null;
		try {
			predio2 = predioService.save(predio);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar el predio");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "¡El predio ha sido creado con éxito!");
		response.put("predio", predio2);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	
	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@PutMapping("/predios/{id}") 
	public ResponseEntity<?> actualizarPredio(@RequestBody Predio predio, @PathVariable Long id) {
		
		System.out.println(predio.getNombreCampo());
		
		Map<String, Object> response = new HashMap<>();
		
		List<Predio> predios = predioService.findAll();
		
		Predio predioActual = predioService.findById(id);
		
		Predio predioActualizado = null;
		
		if (!predioActual.getNombre().trim().equalsIgnoreCase(predio.getNombre().trim())) {

			for(int i=0; i<predios.size(); i++) {
				if(predio.getNombre().trim().equalsIgnoreCase(predios.get(i).getNombre()) && predios.get(i).isEstado() == true) {
					response.put("mensaje", "El nombre del predio ya existe");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
				}
			}
		}		

		predioActual.setNombre(predio.getNombre());
		predioActual.setHectareas(predio.getHectareas());
		predioActual.setIdCampo(predio.getIdCampo());

		try {
			predioActualizado = predioService.save(predioActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el predio en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "¡El predio ha sido actualizado con éxito!");
		response.put("predio", predioActualizado);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@DeleteMapping("/predios/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	public ResponseEntity<?> eliminarPredio(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		
		if (predioService.findById(id) == null) {
			response.put("mensaje", "Error, el predio a eliminar no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			predioService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el predio en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "¡El predio fue eliminado con éxito!");
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
