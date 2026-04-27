package es.alumno.uned.mapper;

import org.springframework.stereotype.Component;

import es.alumno.uned.dto.RolDTO;
import es.alumno.uned.model.entities.Rol;

/**
 * Clase "mapper" entre entidad Rol y RolDTO
 */
@Component
public class RolMapper {
	/** 
	 * Mapeamos la entidad a su DTO
	 * @param entity {@code Rol} 
	 * @return {@code RolDTO}
	 */
	public RolDTO toDTO(Rol entity) {
		
		return new RolDTO(entity.getNombre(), entity.getDescripcion());
	}
}
