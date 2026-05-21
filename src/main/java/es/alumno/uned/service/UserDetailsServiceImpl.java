package es.alumno.uned.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.repository.UsuarioRepository;
import es.alumno.uned.model.util.Paginacion;
import jakarta.persistence.criteria.Predicate;
/**
 * Clase "Service" de la entidad usuario.
 * 
 * <p>Engloba todas las operaciones de consulta y actualización de la entidad Usuario.
 * <p>Interactua con {@link UserDetails} de Spring para obtener sus valores.
 * <p>Nos gestion la consulta de "Usuarios Conectados" desde el {@link SessionRegistry} de Spring.
 */
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
	}

	@Override
	public Paginacion<Usuario, UsuarioRegistroDTO> listadoPaginado(Map<String, String> params, PageParams pageData) {
		Specification<Usuario> condiciones = generaCondiciones(params);
		return construirPaginacion(userRepository.findAll(condiciones,PageRequest.of(pageData.page(), pageData.size())));
	}
	
	/**
	 * Nos devuelve la lista de condiciones {@link Specification} de la consulta de acuerdo a los parámetros enviados.
	 * <p> Si el mapa de parámetros está vacio devuelve todos.
	 * @param filtros Mapa de parámetros para definir la búsqueda de Módulos.
	 * @return Los predicados de búsqueda generados de acuerdo a los parámetros enviados.
	 */
	private static Specification<Usuario> generaCondiciones(Map<String, String> filtros) {
	    return (root, query, cb) -> {
	        List<Predicate> predicates = new ArrayList<>();

	        if (filtros.containsKey("nombre")) {
	            predicates.add(cb.like(cb.lower(root.get("nombre")), 
	                           "%" + filtros.get("nombre").toLowerCase() + "%"));
	        }

	        if (filtros.containsKey("email")) {
	            predicates.add(cb.equal(root.get("email"), filtros.get("email")));
	        }

	        if (filtros.containsKey("rol")) {
	            predicates.add(cb.equal(root.get("rol"), 
	                           filtros.get("rol")));
	        }

	        return cb.and(predicates.toArray(new Predicate[0]));
	    };
	}
	private Paginacion<Usuario, UsuarioRegistroDTO> construirPaginacion( Page<Usuario> page) {
        return new Paginacion.Builder<Usuario, UsuarioRegistroDTO>()
                .pagina(page)
                .mapper(usuarioMapper::toDTO)
                .build();
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
		return userRepository.findAll().stream()
				.filter(a -> a.getRol().equals("PROFE"))
				.map(usuarioMapper::toDTO)
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
	    	throw new UserPasswordNotMatchException("validations.password.nocoincide", dto, "");
	    }
	    this.cambioPasswordUserAdmin(new UserPasswordAdminChangeDTO(dto.getNewPassword(), dto.getConfirmPassword()), usuario.getId());
	}

	@Override
	public void cambioPasswordUserAdmin(UserPasswordAdminChangeDTO dto, Long id) {
		Usuario usuario = getUserOrNotById(id);	    
		if (!dto.getNewPassword().matches(dto.getConfirmPassword())) {
	    	throw new UserPasswordNotMatchException("validations.password.nocoincide", dto, "");
	    }
	    usuario.setPassword(getEncriptedPass(dto.getNewPassword()));
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

	@Override
	public String getEncriptedPass(String password) {
		
		return passEncoder.encode(password);
	}

}
