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
import com.agricola.backend.models.entity.Fertilizante;
import com.agricola.backend.models.services.IFertilizanteService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class FertilizanteRestController {

	@Autowired
	private IFertilizanteService fertilizanteService;

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA", "ROLE_DUENO", "ROLE_ADMINCAMPO" })
	@GetMapping("/fertilizantes")
	public List<Fertilizante> listarFertilizantes() {
		return fertilizanteService.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA", "ROLE_DUENO", "ROLE_ADMINCAMPO" })
	@GetMapping("/fertilizantes/{id}")
	public ResponseEntity<?> buscarFertilizante(@PathVariable Long id) {
		Fertilizante fertilizante = null;
		Map<String, Object> response = new HashMap<>();
		try {
			fertilizante = fertilizanteService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (fertilizante == null) {
			response.put("mensaje",
					"El fertilizante:  ".concat(String.valueOf(id)).concat("  no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Fertilizante>(fertilizante, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA", "ROLE_DUENO" })
	@PostMapping("/fertilizantes")
	public ResponseEntity<?> crearFertilizante(@RequestBody Fertilizante fertilizante) {
		Map<String, Object> response = new HashMap<>();
		
		if (fertilizanteService.findCuartelByNombre(fertilizante.getNombreComercial().trim()) != null && fertilizanteService.findCuartelByNombre(fertilizante.getNombreComercial().trim()).isEstado()) {
			response.put("mensaje", "Error, el fertilzante ".concat(fertilizante.getNombreComercial())
					.concat(" ya existe!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		Fertilizante fertilizanteNew = null;

		try {
			fertilizanteNew = fertilizanteService.save(fertilizante);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("", "El fertilizante ha sido creado con éxito!");
		response.put("Fertilizante ", fertilizanteNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA", "ROLE_DUENO" })
	@PutMapping("/fertilizantes/{id}")
	public ResponseEntity<?> actualizarFertilizante(@RequestBody Fertilizante fertilizante, @PathVariable Long id) {

		Fertilizante fertilizanteActual = fertilizanteService.findById(id);
		Fertilizante fertilizanteUpdated = null;
		Map<String, Object> response = new HashMap<>();
		
		if (fertilizanteActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el fertilizante  ".concat(String.valueOf(id))
					.concat(" , no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		if(!fertilizanteActual.getNombreComercial().trim().equalsIgnoreCase(fertilizante.getNombreComercial().trim())) {
			if (fertilizanteService.findCuartelByNombre(fertilizante.getNombreComercial().trim()) != null) {
				response.put("mensaje", "Error, el fertilizante ".concat(fertilizante.getNombreComercial())
						.concat(" ya existe!"));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		
		try {
			fertilizanteActual.setNombreComercial(fertilizante.getNombreComercial());
			fertilizanteActual.setConcentracion(fertilizante.getConcentracion());
			fertilizanteActual.setTipo(fertilizante.getTipo());
			fertilizanteActual.setVariedad(fertilizante.getVariedad());
			fertilizanteUpdated=fertilizanteService.save(fertilizanteActual);
			
		}catch(DataAccessException e){
			response.put("mensaje", "Error al actualizar el fertilizante en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("", "El fertilizante ha sido actualizado con éxito!");
		response.put("Fertilizante ", fertilizanteUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA", "ROLE_DUENO" })
	@DeleteMapping("/fertilizantes/{id}")
	public ResponseEntity<?>  eliminarFertilizante(@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		try {
			fertilizanteService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el fertilizante en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El fertilizante ha sido eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
