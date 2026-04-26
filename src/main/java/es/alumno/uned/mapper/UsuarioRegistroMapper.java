package es.alumno.uned.mapper;

import org.springframework.stereotype.Component;

import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.model.entities.Usuario;

@Component
public class UsuarioRegistroMapper {

	public UsuarioRegistroDTO toDTO(Usuario user) {
		
		UsuarioRegistroDTO userDTO = new UsuarioRegistroDTO(
				user.getId(),
				user.getNombre(),
				user.getEmail(),
				user.getApellido1(),
				user.getApellido2(),
				user.getPassword(),
				user.getRol(),
				user.isActivo(),
				user.getfAlta(),
				user.getUsuarioAlta()
				       );
		return userDTO;
	}
	
	public Usuario toEntity(UsuarioRegistroDTO userDTO) {
		
		Usuario  usuario = new Usuario(
				userDTO.getId(),
				userDTO.getNombre(),
				userDTO.getApellido1(),
				userDTO.getApellido2(),
				userDTO.getEmail(),
				userDTO.getNewPassword(),
				userDTO.getRol(),
				userDTO.isActivo(),
				userDTO.getfAlta(),
				userDTO.getUsuarioAlta()
        );
		
		return usuario;
	}
	
	
}
