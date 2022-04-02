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

import com.agricola.backend.models.entity.Fitosanitario;
import com.agricola.backend.models.services.IFitosanitarioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class FitosanitarioRestController {

	@Autowired
	private IFitosanitarioService fitosanitarioService;


	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA", "ROLE_DUENO","ROLE_ADMINCAMPO" })
	@GetMapping("/fitosanitarios") // okei
	public List<Fitosanitario> listaFitosanitarios() {
		return fitosanitarioService.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA", "ROLE_DUENO" ,"ROLE_ADMINCAMPO"})
	@GetMapping("/fitosanitarios/{id}")
	public ResponseEntity<?> buscarFitosanitarioById(@PathVariable Long id) {
		
		Fitosanitario fitosanitario = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			fitosanitario = fitosanitarioService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (fitosanitario == null) {
			response.put("mensaje", "El producto fitosanitario con ID : "+ id +" no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Fitosanitario>(fitosanitario, HttpStatus.OK);
	}
	

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA", "ROLE_DUENO" })
	@PostMapping("/fitosanitarios")
	public ResponseEntity<?> crearFitosanitario(@RequestBody Fitosanitario fitosanitario) {
		
		Map<String, Object> response = new HashMap<>();
		
		if (fitosanitarioService.findFitosanitarioByNombre(fitosanitario.getNombreComercial().trim()) != null &&
			fitosanitarioService.findFitosanitarioByNombre(fitosanitario.getNombreComercial()).isEstado() == true)  {
			response.put("mensaje", "El nombre del producto fitosanitario ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		
		Fitosanitario fito = null;
		try {
			fito = fitosanitarioService.save(fitosanitario);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar el producto fitosanitario");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "¡El producto fitosanitario ha sido creado con éxito!");
		response.put("fito", fito);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA", "ROLE_DUENO" })
	@PutMapping("/fitosanitarios/{id}")
	public ResponseEntity<?> actualizarFitosanitario(@RequestBody Fitosanitario fitosanitario, @PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();

		Fitosanitario fitoActualizado = null;
		
		Fitosanitario fitosanitarioActual = fitosanitarioService.findById(id);
		
		if (!fitosanitarioActual.getNombreComercial().trim().equalsIgnoreCase(fitosanitario.getNombreComercial().trim())) {

			if (fitosanitarioService.findFitosanitarioByNombre(fitosanitario.getNombreComercial().trim()) != null && 
					fitosanitarioService.findFitosanitarioByNombre(fitosanitario.getNombreComercial()).isEstado() == true) {
				response.put("mensaje", "El nombre del producto fitosanitario ya existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}	

		fitosanitarioActual.setNombreComercial(fitosanitario.getNombreComercial());
		fitosanitarioActual.setTipo(fitosanitario.getTipo());
		fitosanitarioActual.setObjetivo(fitosanitario.getObjetivo());
		fitosanitarioActual.setIngredienteActivo(fitosanitario.getIngredienteActivo());
		fitosanitarioActual.setMojamiento(fitosanitario.getMojamiento());
		fitosanitarioActual.setCarencia(fitosanitario.getCarencia());
		
		try {
			fitoActualizado = fitosanitarioService.save(fitosanitarioActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar al producto fitosanitario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El producto fitosanitario ha sido actualizado con éxito!!");
		response.put("encargado", fitoActualizado);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@Secured({ "ROLE_ADMIN", "ROLE_ENCARGADOBPA", "ROLE_DUENO" })
	@DeleteMapping("/fitosanitarios/{id}")
	public ResponseEntity<?> eliminarFitosanitario(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		if (fitosanitarioService.findById(id) == null) {
			response.put("mensaje", "Error, el producto fitosanitario a eliminar no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		try {
			fitosanitarioService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el producto fitosanitario en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El producto fitosanitario fue eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
