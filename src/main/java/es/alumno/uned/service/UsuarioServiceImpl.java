package es.alumno.uned.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.UsuarioRepository;



public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario guardar(UsuarioRegistroDTO registroDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioRegistroDTO> listarUsuarios() {
	    return userRepo.findAll().stream()
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


}
