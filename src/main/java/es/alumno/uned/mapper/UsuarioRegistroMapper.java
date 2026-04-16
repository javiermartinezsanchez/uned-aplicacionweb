package es.alumno.uned.mapper;

import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.model.entities.Usuario;

public class UsuarioRegistroMapper {

	public static UsuarioRegistroDTO toDTO(Usuario user) {
		
		UsuarioRegistroDTO userDTO = new UsuarioRegistroDTO();
		
		return userDTO;
	}
}
