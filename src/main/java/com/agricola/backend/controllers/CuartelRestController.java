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
import com.agricola.backend.models.entity.Cuartel;
import com.agricola.backend.models.services.ICuartelService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class CuartelRestController {

	@Autowired
	private ICuartelService cuartelService;

	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO","ROLE_ENCARGADOBPA" })
	@GetMapping("/cuarteles")
	public List<Cuartel> listarCuarteles() {
		return cuartelService.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@GetMapping("/cuarteles/{id}")
	public ResponseEntity<?> buscarCuarteles(@PathVariable Long id) {

		Cuartel cuartel = null;
		Map<String, Object> response = new HashMap<>();
		try {
			cuartel = cuartelService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (cuartel == null) {
			response.put("mensaje",
					"El cuartel:  ".concat(String.valueOf(id)).concat("  no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cuartel>(cuartel, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@PostMapping("/cuarteles")
	public ResponseEntity<?> crearCuartel(@RequestBody Cuartel cuartel) {

		Map<String, Object> response = new HashMap<>();
		if (cuartelService.findCuartelByNombre(cuartel.getNombre().trim()) != null && cuartelService.findCuartelByNombre(cuartel.getNombre().trim()).isEstado() == true ) {
			response.put("mensaje", "Error, el cuartel con nombre: ".concat(cuartel.getNombre()).concat(" ya existe!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		Cuartel cuartelNew = null;

		try {
			cuartelNew = cuartelService.save(cuartel);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("", "El cuartel ha sido creado con éxito!");
		response.put("Cuartel ", cuartelNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@PutMapping("/cuarteles/{id}")
	public ResponseEntity<?> actualizarcuartel(@RequestBody Cuartel cuartel, @PathVariable Long id) {
		System.out.println(id);
		System.out.println(cuartel.getRunEncargadoBPA());
		Cuartel cuartelActual = cuartelService.findById(id);
		Cuartel cuartelUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		
		if (cuartelActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el cuartel:  ".concat(String.valueOf(id))
					.concat(" , no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if(!cuartelActual.getNombre().trim().equalsIgnoreCase(cuartel.getNombre().trim())) {
			if (cuartelService.findCuartelByNombre(cuartel.getNombre().trim()) != null) {
				response.put("mensaje", "Error, el cuartel con nombre: ".concat(cuartel.getNombre()).concat(" ya existe!"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		
		try {
				cuartelActual.setNombre(cuartel.getNombre());
				cuartelActual.setHectareas(cuartel.getHectareas());
				cuartelActual.setTipoUva(cuartel.getTipoUva());
				cuartelActual.setRunEncargadoBPA(cuartel.getRunEncargadoBPA());
				cuartelUpdated= cuartelService.save(cuartelActual);
		}catch(DataAccessException e){
			response.put("mensaje", "Error al actualizar el cuartel en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("", "El cuartel ha sido actualizado con éxito!");
		response.put("Cuartel ", cuartelUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@DeleteMapping("/cuarteles/{id}")
	public ResponseEntity<?> eliminarCuartel(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			
			cuartelService.delete(id); 
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el cuartel en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El cuartel ha sido eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	}


