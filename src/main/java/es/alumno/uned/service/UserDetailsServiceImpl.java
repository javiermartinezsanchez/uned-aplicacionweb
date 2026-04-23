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
import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.mapper.UsuarioRegistroMapper;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.UsuarioRepository;
import es.alumno.uned.model.util.PaginacionComun;

@Service
public class UserDetailsServiceImpl implements UsuarioService, UserDetailsService{

	private UsuarioRepository userRepository;

	@Autowired
	private PasswordEncoder passEncoder;
	

	 @Autowired
	private SessionRegistry sessionRegistry;
	 
	public UserDetailsServiceImpl(UsuarioRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> user = userRepository.findByEmail(username);
		if (user.isPresent()) {
			return new SecurityUser(user.get());
		}
		else {
			throw new UsernameNotFoundException("User not found");
		}
	}
	public List<UsuarioRegistroDTO> listarUsuarios() {
	    return users2DTO(userRepository.findAll());
	}

	public List<UsuarioRegistroDTO> users2DTO(List<Usuario> usuarios){
		return usuarios.stream()
	            .map(v -> new UsuarioRegistroDTO(
	                    v.getId(),
	                    v.getNombre(),
	                    v.getEmail(),
	                    v.getApellido1(),
	                    v.getApellido2(),
	                    v.getPassword(),
	                    v.getRol()
	            ))
	            .collect(Collectors.toList());
	}
	public PaginacionComun<Usuario> listadoPaginado(String url, Pageable pageable) {
		
		return new PaginacionComun<>(url,userRepository.findAll(pageable));
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
		
		return UsuarioRegistroMapper.toDTO(findById(id));
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
}
