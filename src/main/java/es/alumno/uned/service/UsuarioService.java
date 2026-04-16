package es.alumno.uned.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.model.entities.Usuario;

public interface UsuarioService extends UserDetailsService{

	/**
	 * Guardamos la información de un usuario a través del POJO UsuarioRegistroDTO.
	 * 
	 * @param registroDTO La informacion del usuario encapsulada en el DTO
	 * @return Devolvemos el usuario guardado
	 */
	public Usuario guardar(UsuarioRegistroDTO registroDTO);
	
	public List<UsuarioRegistroDTO> listarUsuarios();

	public Usuario findById(Long id);

	public Usuario findByEmail(String name);

	
}
