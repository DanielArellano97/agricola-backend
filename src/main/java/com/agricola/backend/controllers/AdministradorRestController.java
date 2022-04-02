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
import com.agricola.backend.models.entity.Administrador;
import com.agricola.backend.models.entity.Role;
import com.agricola.backend.models.entity.Usuario;
import com.agricola.backend.models.services.IAdministradorService;
import com.agricola.backend.models.services.IRoleService;
import com.agricola.backend.models.services.IUsuarioService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class AdministradorRestController {

	@Autowired
	private IRoleService roleService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private IUsuarioService userService;

	@Autowired
	private IAdministradorService administradorService;

	@Secured({ "ROLE_ADMIN", "ROLE_DUENO","ROLE_ADMINCAMPO" })
	@GetMapping("/administradores")
	public List<Administrador> listarAdministradores() {
		return administradorService.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_DUENO" })
	@GetMapping("/administradores/{run}")
	public ResponseEntity<?> buscarAdministrador(@PathVariable String run) {

		Administrador administrador = null;
		Map<String, Object> response = new HashMap<>();
		try {
			administrador = administradorService.findById(run);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (administrador == null) {
			response.put("mensaje", "El administrador:  ".concat(run).concat("  no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Administrador>(administrador, HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_DUENO" })
	@PostMapping("/administradores")
	public ResponseEntity<?> crearAdministrador(@RequestBody Administrador adm) {

		Map<String, Object> response = new HashMap<>();

		
		if (userService.findByEmail(adm.getEmail()) != null ) {
			response.put("mensaje", "Error, el email ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (administradorService.findAdministradorByNombre(adm.getNombre().trim()) != null) {
			response.put("mensaje", "Error, el nombre ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		if (administradorService.findAdministradorByRun(adm.getRun()) != null && administradorService.findAdministradorByRun(adm.getRun()).isEstado()) {
			response.put("mensaje", "Error, el rut ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		if (administradorService.findAdministradorByTelefono(adm.getTelefono()) != null) {
			response.put("mensaje", "Error, el telefono ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}

		if (administradorService.findAdministradorByEmail(adm.getEmail()) != null) {
			response.put("mensaje", "Error, el email ya existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
		}
		Administrador administradorNew = null;

		try {
			administradorNew = administradorService.save(adm);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		List<Role> list1 = (List<Role>) roleService.findAll();
		List<Role> listaFiltrada = list1.stream().filter(item -> item.getNombre().equalsIgnoreCase("ROLE_ADMINCAMPO"))
				.collect(Collectors.toList());
		Usuario user = new Usuario();
		user.setUsername(adm.getRun());
		user.setNombre(adm.getNombre());
		user.setEnabled(true);
		user.setEmail(adm.getEmail());
		user.setPassword(passwordEncoder.encode(adm.getPassword()));
		user.setRoles(listaFiltrada);
		userService.save(user);

		response.put("mensaje", "El administrador ha sido creado con éxito!");
		response.put("Administrador ", administradorNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_DUENO" })
	@PutMapping("/administradores/{run}")
	public ResponseEntity<?> actualizarAdministrador(@RequestBody Administrador adm, @PathVariable String run) {

		Administrador administradorActual = administradorService.findById(run);
		Administrador administradorUpdated = null;
		Usuario user = userService.findByUsername(adm.getRun());
		Map<String, Object> response = new HashMap<>();

		if (administradorActual == null) {
			response.put("mensaje", "Error: no se pudo editar, el administrador:  ".concat(run)
					.concat("  no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		if (!administradorActual.getEmail().trim().equalsIgnoreCase(adm.getEmail().trim())) {
			if (userService.findByEmail(adm.getEmail()) != null) {
				response.put("mensaje", "Error, el email ya existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		
		if (!administradorActual.getEmail().trim().equalsIgnoreCase(adm.getEmail().trim())) {
			if (administradorService.findAdministradorByEmail(adm.getEmail().trim()) != null) {
				System.out.println("entre al segundo if");
				response.put("mensaje", "Error, el email ya existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}

		if (!administradorActual.getNombre().trim().equalsIgnoreCase(adm.getNombre().trim())) {

			if (administradorService.findAdministradorByNombre(adm.getNombre().trim()) != null) {
				response.put("mensaje", "Error, el nombre ya existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}

		if (!administradorActual.getTelefono().trim().equalsIgnoreCase(adm.getTelefono().trim())) {

			if (administradorService.findAdministradorByTelefono(adm.getTelefono()) != null) {
				response.put("mensaje", "Error, el telefono ya existe");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_ACCEPTABLE);
			}
		}
		try {
			administradorActual.setTelefono(adm.getTelefono());
			administradorActual.setEmail(adm.getEmail());
			administradorActual.setPassword(adm.getPassword());

			user.setEmail(adm.getEmail());
			user.setPassword(passwordEncoder.encode(adm.getPassword()));
			administradorUpdated = administradorService.save(administradorActual);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el administrador en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("", "El administrador ha sido actualizado con éxito!");
		response.put("Administrador ", administradorUpdated);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_DUENO" })
	@DeleteMapping("/administradores/{run}")
	public ResponseEntity<?> eliminarAdministrador(@PathVariable String run) {
		Map<String, Object> response = new HashMap<>();
		// desactiva los permisos para inicar sesión
		// userService.delete(userService.findByUsername(run)); // elimina por completo
		// de la autenticación al usuario excepto en la tabla de la entidad en este caso
		// administrador, solo la setea
		// eliminado de forma lógica
		
		if (administradorService.findAdministradorByRun(run) == null) {
			response.put("mensaje", "Error, el administrador a eliminar no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			userService.findByUsername(run).setEnabled(false);
			administradorService.delete(run);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el administrador en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "El administrador ha sido eliminado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
