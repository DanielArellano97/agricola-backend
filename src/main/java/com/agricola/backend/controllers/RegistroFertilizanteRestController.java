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

import com.agricola.backend.models.entity.RegistroFertilizante;
import com.agricola.backend.models.services.IRegistroFertilizanteService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class RegistroFertilizanteRestController {

	@Autowired
	private IRegistroFertilizanteService registroFertilizanteService;

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA", "ROLE_DUENO", "ROLE_ADMINCAMPO" })
	@GetMapping("/registrosFertilizantes")
	public List<RegistroFertilizante> listarRegistrosFertilizantes() {

		return registroFertilizanteService.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA", "ROLE_ADMINCAMPO", "ROLE_DUENO" })
	@GetMapping("/registrosFertilizantes/{id}")
	public ResponseEntity<?> buscarRegistro(@PathVariable Long id) {

		RegistroFertilizante registroFertilizante = null;
		Map<String, Object> response = new HashMap<>();
		try {
			registroFertilizante = registroFertilizanteService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (registroFertilizante == null) {
			response.put("mensaje",
					"El registro:  ".concat(String.valueOf(id)).concat("  no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<RegistroFertilizante>(registroFertilizante, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA" })
	@PostMapping("/registrosFertilizantes")
	public ResponseEntity<?> crearRegistro(@RequestBody RegistroFertilizante registro) {
		Map<String, Object> response = new HashMap<>();

		RegistroFertilizante registroNew = null;

		try {
			registroNew = registroFertilizanteService.save(registro);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("", "El registro  ha sido guardado con éxito!");
		response.put("Registro Fertilizante ", registroNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA" })
	@PutMapping("/registrosFertilizantes/{id}")
	public ResponseEntity<?> actualizarRegistro(@RequestBody RegistroFertilizante registro, @PathVariable Long id) {

		RegistroFertilizante registroActual = registroFertilizanteService.findById(id);
		RegistroFertilizante registroUpdated = null;
		Map<String, Object> response = new HashMap<>();

		if (registroActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el registro:  ".concat(String.valueOf(id))
					.concat(" , no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			registroActual.setMetodoAplicacion(registro.getMetodoAplicacion());
			registroActual.setEstadoFenologico(registro.getEstadoFenologico());
			registroActual.setFecha(registro.getFecha());
			registroActual.setCantidadAplicada(registro.getCantidadAplicada());
			registroActual.setTipoMaquinaria(registro.getTipoMaquinaria());
			registroUpdated = registroFertilizanteService.save(registroActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("", "El registro fertilizante ha sido actualizado con éxito!");
		response.put("Registro Fertilizante ", registroUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA" })
	@DeleteMapping("/registrosFertilizantes/{id}")
	public ResponseEntity<?> eliminarRegistro(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		
		try {
			
			registroFertilizanteService.delete(id);
			
		} catch (DataAccessException e) {
			
			response.put("mensaje", "Error al eliminar el registro en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El registro ha sido eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
