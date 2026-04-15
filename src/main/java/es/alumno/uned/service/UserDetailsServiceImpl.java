package es.alumno.uned.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.model.entities.SecurityUser;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private UsuarioRepository userRepository;

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
	    return userRepository.findAll().stream()
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

	public Page<Usuario> listadoPaginado(Pageable pageable) {
		
		return userRepository.findAll(pageable);
	}
}
