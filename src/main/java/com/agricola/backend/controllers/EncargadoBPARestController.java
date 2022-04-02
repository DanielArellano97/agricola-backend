package com.agricola.backend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agricola.backend.models.entity.EncargadoBPA;
import com.agricola.backend.models.entity.Role;
import com.agricola.backend.models.entity.Usuario;
import com.agricola.backend.models.services.IEncargadoBPAService;
import com.agricola.backend.models.services.IRoleService;
import com.agricola.backend.models.services.IUsuarioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class EncargadoBPARestController {
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IUsuarioService userService;
	
	@Autowired
	private IEncargadoBPAService encargadoBPAService;

	@Secured({ "ROLE_ADMIN", "ROLE_ADMINCAMPO", "ROLE_DUENO","ROLE_ENCARGADOBPA" })
	@GetMapping("/encargadosBPA") 
	public List<EncargadoBPA> listaEncargadosBPA() {
		return encargadoBPAService.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_DUENO" })
	@GetMapping("/encargadosBPA/{run}") 
	public ResponseEntity<?> buscarEncargadoBPAById(@PathVariable String run) {

		EncargadoBPA encargado = null;
		Map<String, Object> response = new HashMap<>();

		try {
			encargado = encargadoBPAService.findById(run);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (encargado == null) {
			response.put("mensaje", "El Encargado BPA con RUN : ".concat(run.concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<EncargadoBPA>(encargado, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_DUENO" })
	@PostMapping("/encargadosBPA")
	public ResponseEntity<?> crearEncargadoBPA(@RequestBody EncargadoBPA encargadoBPA) {
		
		Map<String, Object> response = new HashMap<>();
		
		if (encargadoBPAService.findEncargadoByNombre(encargadoBPA.getNombre().trim()) != null) {
			response.put("mensaje", "Error, el nombre ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		if (encargadoBPAService.findEncargadoByRun(encargadoBPA.getRun().trim()) != null && encargadoBPAService.findEncargadoByRun(encargadoBPA.getRun().trim()).isEstado() == true) {
			response.put("mensaje", "Error, el rut ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		if (encargadoBPAService.findEncargadoByTelefono(encargadoBPA.getTelefono().trim()) != null) {
			response.put("mensaje", "Error, el telefono ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		
		if (userService.findByEmail(encargadoBPA.getEmail()) != null ) {
			response.put("mensaje", "Error, el email ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		if (encargadoBPAService.findEncargadoByEmail(encargadoBPA.getEmail().trim()) != null) {
			response.put("mensaje", "Error, el email ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		EncargadoBPA encargado = null;
		try {
			encargado = encargadoBPAService.save(encargadoBPA);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar el Encargado BPA");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		List<Role> list1 = (List<Role>) roleService.findAll();
	    List<Role> listaFiltrada = list1.stream().filter(item->item.getNombre().equalsIgnoreCase("ROLE_ENCARGADOBPA")).collect(Collectors.toList());
        Usuario user = new Usuario();
        user.setUsername(encargadoBPA.getRun());
        user.setNombre(encargadoBPA.getNombre());
        user.setEnabled(true);
        user.setEmail(encargadoBPA.getEmail());
        user.setPassword(passwordEncoder.encode(encargadoBPA.getPass()));
        user.setRoles(listaFiltrada);
        userService.save(user);
		
		response.put("mensaje", "El encargado ha sido creado con éxito!!");
		response.put("encargado", encargado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_DUENO" })
	@PutMapping("/encargadosBPA/{run}")
	public ResponseEntity<?> actualizarEncargadoBPA(@RequestBody EncargadoBPA encargadoBPA, @PathVariable String run) {

		Map<String, Object> response = new HashMap<>();

		EncargadoBPA encargadoBPAActual = encargadoBPAService.findById(run);
		EncargadoBPA encargadoActualizado = null;
		Usuario user = userService.findByUsername(encargadoBPA.getRun());
		
		if (!encargadoBPAActual.getNombre().trim().equalsIgnoreCase(encargadoBPA.getNombre().trim())) {

			if (encargadoBPAService.findEncargadoByNombre(encargadoBPA.getNombre().trim()) != null) {
				response.put("mensaje", "Error, el nombre ya existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}

		if (!encargadoBPAActual.getTelefono().trim().equalsIgnoreCase(encargadoBPA.getTelefono().trim())) {

			if (encargadoBPAService.findEncargadoByTelefono(encargadoBPA.getTelefono().trim()) != null) {
				response.put("mensaje", "Error, el telefono ya existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}

		if (!encargadoBPAActual.getEmail().trim().equalsIgnoreCase(encargadoBPA.getEmail().trim())) {
			if (encargadoBPAService.findEncargadoByEmail(encargadoBPA.getEmail().trim()) != null) {
				response.put("mensaje", "Error, el email ya existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		
		if (!encargadoBPAActual.getEmail().trim().equalsIgnoreCase(encargadoBPA.getEmail().trim())) {
			if (userService.findByEmail(encargadoBPA.getEmail()) != null) {
				response.put("mensaje", "Error, el email ya existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}

		try {
			encargadoBPAActual.setNombre(encargadoBPA.getNombre());
			encargadoBPAActual.setTelefono(encargadoBPA.getTelefono());
			encargadoBPAActual.setEmail(encargadoBPA.getEmail());
			encargadoBPAActual.setPass(encargadoBPA.getPass());
			
			user.setEmail(encargadoBPA.getEmail());
			user.setNombre(encargadoBPA.getNombre());
			user.setPassword(passwordEncoder.encode(encargadoBPA.getPass()));
			
			encargadoActualizado = encargadoBPAService.save(encargadoBPAActual);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar al Encargado BPA en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El encargado ha sido actualizado con éxito!!");
		response.put("encargado", encargadoActualizado);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@Secured({ "ROLE_ADMIN", "ROLE_DUENO" })
	@DeleteMapping("/encargadosBPA/{run}")
	public ResponseEntity<?> eliminarEncargadoBPA(@PathVariable String run) {
		Map<String, Object> response = new HashMap<>();

		if (encargadoBPAService.findEncargadoByRun(run) == null) {
			response.put("mensaje", "Error, el encargado a eliminar no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			userService.findByUsername(run).setEnabled(false);
			encargadoBPAService.delete(run);
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar al Encargado BPA en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El encargado fue eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
}
