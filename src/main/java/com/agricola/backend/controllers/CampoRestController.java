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
import org.springframework.web.bind.annotation.RestController;

import com.agricola.backend.models.entity.Campo;
import com.agricola.backend.models.services.ICampoService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class CampoRestController {

	@Autowired
	private ICampoService campoService;

	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@GetMapping("/campos") 
	public List<Campo> listaCampos() {
		return campoService.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@GetMapping("/campos/{id}") 
	public ResponseEntity<?> buscarCampo(@PathVariable Long id) {
		
		Campo campo = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			campo = campoService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(campo == null) {
			response.put("mensaje", "El Campo con ID : " +id+ " no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		
		return new ResponseEntity<Campo>(campo, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@PostMapping("/campos") 
	public ResponseEntity<?> crearCampo(@RequestBody Campo campo) {
		
		Map<String, Object> response = new HashMap<>();
		List<Campo> campos = campoService.findAll();
		
		for(int i=0; i<campos.size(); i++) {
			if (campos.get(i).getNombre().trim().equalsIgnoreCase(campo.getNombre().trim()) && campos.get(i).isEstado() == true) {
				response.put("mensaje", "El nombre del campo ya existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		
		for(int i=0; i<campos.size(); i++) {
			if (campos.get(i).getDireccion().trim().equalsIgnoreCase(campo.getDireccion().trim()) && campos.get(i).isEstado() == true) {
				response.put("mensaje", "La dirección del campo ya existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		
		Campo campo2 = null;
		try {
			campo2 = campoService.save(campo);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar el Campo");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "¡El campo ha sido creado con éxito!");
		response.put("campo", campo2);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	
	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@PutMapping("/campos/{id}")
	public ResponseEntity<?> actualizarCampo(@RequestBody Campo campo, @PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		List<Campo> campos = campoService.findAll();
		Campo campoActual = campoService.findById(id);		
		Campo campoActualizado = null;
		
		if (!campoActual.getNombre().trim().equalsIgnoreCase(campo.getNombre().trim())) {
			for(int i=0; i<campos.size(); i++) {
				if (campos.get(i).getNombre().trim().equalsIgnoreCase(campo.getNombre().trim()) && campos.get(i).isEstado() == true) {
					response.put("mensaje", "El nombre del campo ya existe");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
				}
			}
		}
		
		if (!campoActual.getDireccion().trim().equalsIgnoreCase(campo.getDireccion().trim())) {

			for(int i=0; i<campos.size(); i++) {
				if (campos.get(i).getDireccion().trim().equalsIgnoreCase(campo.getDireccion().trim()) && campos.get(i).isEstado() == true) {
					response.put("mensaje", "La dirección del campo ya existe");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
				}
			}
		}

		campoActual.setNombre(campo.getNombre());
		campoActual.setDireccion(campo.getDireccion());
		campoActual.setHectareas(campo.getHectareas());
		campoActual.setRunAdministradorCampo(campo.getRunAdministradorCampo());
		campoActual.setRunAdministradorCampo(campo.getRunAdministradorCampo());
		
		try {
			campoActualizado = campoService.save(campoActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el campo en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "¡El campo ha sido actualizado con éxito!");
		response.put("encargado", campoActualizado);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@DeleteMapping("/campos/{id}")
	public ResponseEntity<?> eliminarCampo(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		if (campoService.findById(id) == null) {
			response.put("mensaje", "Error, el campo a eliminar no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			campoService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el campo en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "¡El campo fue eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
