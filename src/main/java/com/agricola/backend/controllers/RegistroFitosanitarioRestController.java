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

import com.agricola.backend.models.entity.RegistroFitosanitario;
import com.agricola.backend.models.services.IRegistroFitosanitarioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class RegistroFitosanitarioRestController {

	@Autowired
	private IRegistroFitosanitarioService registroFitosanitarioService;


	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA","ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@GetMapping("/registrosFitosanitarios") // okei
	public List<RegistroFitosanitario> listaFitosanitarios() {
		return registroFitosanitarioService.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA","ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@GetMapping("/registrosFitosanitarios/{id}")
	public ResponseEntity<?> buscarRegistroFitosanitarioById(@PathVariable Long id) {
		
		RegistroFitosanitario registroFito = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			registroFito = registroFitosanitarioService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (registroFito == null) {
			response.put("mensaje", "El registro fitosanitario con ID : "+ id +" no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RegistroFitosanitario>(registroFito, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA" })
	@PostMapping("/registrosFitosanitarios")
	public ResponseEntity<?> crearRegistroFitosanitario(@RequestBody RegistroFitosanitario registroFitosanitario) {
		
		Map<String, Object> response = new HashMap<>();
		
		RegistroFitosanitario registroFito = null;
		
		try {
			registroFito = registroFitosanitarioService.save(registroFitosanitario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar el registro fitosanitario");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "¡El registro fitosanitario ha sido creado con éxito!");
		response.put("encargado", registroFito);
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA" })
	@PutMapping("/registrosFitosanitarios/{id}")
	public ResponseEntity<?> actualizarFitosanitario(@RequestBody RegistroFitosanitario registroFitosanitario, @PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();

		RegistroFitosanitario registroFitoActualizado = null;
		RegistroFitosanitario registroFitosanitarioActual = registroFitosanitarioService.findById(id);

		registroFitosanitarioActual.setTipoMaquinaria(registroFitosanitario.getTipoMaquinaria());
		registroFitosanitarioActual.setEstadoFenologico(registroFitosanitario.getEstadoFenologico());
		registroFitosanitarioActual.setDosis(registroFitosanitario.getDosis());
		System.out.println(registroFitosanitario.getFecha());
		registroFitosanitarioActual.setFecha(registroFitosanitario.getFecha());
		registroFitosanitarioActual.setHoraTermino(registroFitosanitario.getHoraTermino());
		registroFitosanitarioActual.setCondicionesMetereologicas(registroFitosanitario.getCondicionesMetereologicas());

		try {
			registroFitoActualizado = registroFitosanitarioService.save(registroFitosanitarioActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el registro fitosanitario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "¡El registro fitosanitario ha sido actualizado con éxito!");
		response.put("registroFitosanitario", registroFitoActualizado);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);


	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA" })
	@DeleteMapping("/registrosFitosanitarios/{id}")
	public ResponseEntity<?> eliminarRegistroFitosanitario(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		if (registroFitosanitarioService.findById(id) == null) {
			response.put("mensaje", "Error, el registro fitosanitario a eliminar no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			registroFitosanitarioService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el registro fitosanitario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El registro fitosanitario fue eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
