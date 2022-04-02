package com.agricola.backend.models.services;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.agricola.backend.models.dao.IUsuarioDao;
import com.agricola.backend.models.entity.Usuario;


@Service
public class UsuarioService implements IUsuarioService,UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	private Long id = null;
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario =usuarioDao.findByUsername(username);
		
		if(usuario==null) {
			logger.error("Error en el login: no existe el usuario " + usuario+ " en el sistema!");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario " + usuario+ " en el sistema!");
		}
		List <GrantedAuthority> authorities = usuario.getRoles()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role:  "+ authority.getAuthority()))
				.collect(Collectors.toList());
		
		return new User(username, usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

	
	@Override
	@Transactional(readOnly=true)
	public Usuario findByUsername(String username) {
		return usuarioDao.findByUsername(username);
	}

	@Override
	public Usuario save(Usuario user) {
		return usuarioDao.save(user);
	}
	
	@Override
	@Transactional
	public void delete(Usuario usuario) {
		usuarioDao.delete(usuario);;
	}

	@Override
	public Long findByRun(String run) {
		List<Usuario> usuarios = (List<Usuario>) usuarioDao.findAll();
	
		for (Iterator iterator = usuarios.iterator(); iterator.hasNext();) {
			Usuario usuario = (Usuario) iterator.next();
			if(usuario.getUsername().equalsIgnoreCase(run)) {
				id=usuario.getId();
			}
		}
		return id;
	}


	@Override
	@Transactional(readOnly=true)
	public Optional<Usuario> findById(Long id) {
		return usuarioDao.findById(id);
	}


	@Override
	public Usuario findByEmail(String email) {
		return usuarioDao.findByEmail(email);
	}





}