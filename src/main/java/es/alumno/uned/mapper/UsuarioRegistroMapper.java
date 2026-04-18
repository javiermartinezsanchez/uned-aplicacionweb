package es.alumno.uned.mapper;

import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.model.entities.Usuario;

public class UsuarioRegistroMapper {

	public static UsuarioRegistroDTO toDTO(Usuario user) {
		
		UsuarioRegistroDTO userDTO = new UsuarioRegistroDTO(
				user.getId(),
				user.getNombre(),
				user.getEmail(),
				user.getApellido1(),
				user.getApellido2(),
				user.getPassword(),
				user.getRol()
        );
		
		return userDTO;
	}
}
