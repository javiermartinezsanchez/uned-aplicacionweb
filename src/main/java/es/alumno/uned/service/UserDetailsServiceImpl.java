package es.alumno.uned.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.alumno.uned.controller.UserSessionInfoDTO;
import es.alumno.uned.dto.UserPasswordAdminChangeDTO;
import es.alumno.uned.dto.UserPasswordChangeDTO;
import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.exception.UserPasswordNotMatchException;
import es.alumno.uned.mapper.UsuarioRegistroMapper;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.UsuarioRepository;
import es.alumno.uned.model.util.Paginacion;

@Service
public class UserDetailsServiceImpl implements UsuarioService, UserDetailsService{

	private UsuarioRepository userRepository;

	@Autowired
	UsuarioRegistroMapper usuarioMapper;
	
	@Autowired
	private PasswordEncoder passEncoder;
	

	@Autowired
	private SessionRegistry sessionRegistry;
	 
	public UserDetailsServiceImpl(UsuarioRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return new SecurityUser(getUserOrNotByEmail(username));
		/*
		Optional<Usuario> user = userRepository.findByEmail(username);
		if (user.isPresent()) {
			return new SecurityUser(user.get());
		}
		else {
			throw new UsernameNotFoundException("User not found");
		}
		*/
	}
	public List<UsuarioRegistroDTO> listarUsuarios() {
	    return users2DTO(userRepository.findAll());
	}

	private List<UsuarioRegistroDTO> users2DTO(List<Usuario> usuarios){
		return usuarios.stream()
	            .map(usuarioMapper::toDTO)
	            .collect(Collectors.toList());
	}

	@Override
	public Paginacion<Usuario, UsuarioRegistroDTO> listadoPaginado(String url, Pageable pageRequest) {
		
		return new Paginacion.Builder<Usuario, UsuarioRegistroDTO>()
				.url(url)
				.pagina(userRepository.findAll(pageRequest))
				.mapper(usuarioMapper::toDTO)
				.build()
				;
	}
	@Override
	public Usuario grabar(UsuarioRegistroDTO registroDTO, String usuarioAlta) {
		Usuario user = findByEmail(registroDTO.getEmail());
		if (user == null) {
			user = new Usuario();
			user.setEmail(registroDTO.getEmail());
			user.setPassword(passEncoder.encode(registroDTO.getNewPassword()));
			user.setUsuarioAlta(usuarioAlta);
			user.setfAlta(LocalDateTime.now());
			user.setActivo(true);
		}
		user.setRol(registroDTO.getRol());
		user.setNombre(registroDTO.getNombre());
		user.setApellido1(registroDTO.getApellido1());
		user.setApellido2(registroDTO.getApellido2());
		return userRepository.save(user);
	}

	@Override
	public Usuario findById(Long id) {
		
		return userRepository.getReferenceById(id);
	}

	@Override
	public Usuario findByEmail(String email) {
		Optional<Usuario> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

	@Override
	public UsuarioRegistroDTO getUsuario(Long id) {
		
		return usuarioMapper.toDTO(findById(id));
	}

	@Override
	public List<UserSessionInfoDTO> getConnectedUsers() {

	    return sessionRegistry.getAllPrincipals().stream()
	        .flatMap(principal -> sessionRegistry.getAllSessions(principal, false).stream())
	        .map(sessionInfo -> new UserSessionInfoDTO(
	                ((UserDetails) sessionInfo.getPrincipal()).getUsername(),
	                sessionInfo.getLastRequest()
	        ))
	        .toList();
	}

	@Override
	public List<UsuarioRegistroDTO> listarProfesores() {
		return listarUsuarios().stream()
				.filter(a -> a.getRol().equals("PROFE"))
				.toList()
				;
	}

	@Override
	public Long getIdByEmail(String email) {
		return findByEmail( email).getId();
	}

	@Override
	public void cambioPassword(UserPasswordChangeDTO dto, String email) {
		Usuario usuario = getUserOrNotByEmail(email);	    
		if (!passEncoder.matches(dto.getOldPassword(), usuario.getPassword())) {
	    	throw new UserPasswordNotMatchException("Contraseña actual no es correcta");
	    }
	    this.cambioPasswordUserAdmin(new UserPasswordAdminChangeDTO(dto.getNewPassword(), dto.getConfirmPassword()), usuario.getId());
	}

	@Override
	public void cambioPasswordUserAdmin(UserPasswordAdminChangeDTO dto, Long id) {
		Usuario usuario = getUserOrNotById(id);	    
		if (!dto.getNewPassword().matches(dto.getConfirmPassword())) {
	    	throw new UserPasswordNotMatchException("Las contraseñas no coinciden}");
	    }
	    usuario.setPassword(passEncoder.encode(dto.getNewPassword()));
	    userRepository.save(usuario);
	}
	private Usuario getUserOrNotByEmail(String email) {
		Optional<Usuario> usuario = userRepository.findByEmail(email);
		if (usuario.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		return usuario.get();
	}
	
	private Usuario getUserOrNotById(Long id) {
		Optional<Usuario> usuario = userRepository.findById(id);
		if (usuario.isEmpty()) {
			throw new UsernameNotFoundException("User not found");
		}
		return usuario.get();
	}

}
