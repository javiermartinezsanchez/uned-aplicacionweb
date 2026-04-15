package es.alumno.uned.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.model.entities.Usuario;

public interface UsuarioService extends UserDetailsService{

	public Usuario guardar(UsuarioRegistroDTO registroDTO);
	
	public List<UsuarioRegistroDTO> listarUsuarios();
	
}
