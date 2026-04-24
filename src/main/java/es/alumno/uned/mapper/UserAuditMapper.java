package es.alumno.uned.mapper;

import org.springframework.stereotype.Component;

import es.alumno.uned.dto.UserAuditDTO;
import es.alumno.uned.model.entities.UserAudit;

/**
 * Clase utilitaria para mapear la entidad UserAudit a UserAuditDTO para no mostrar 
 * datos de entidades conectadas a la BD con los mostrados en las vistas.
 * 
 * 
 */
@Component
public class UserAuditMapper {

	/**
	 * Mapea la entidad UserAudit a UserAuditDTO
	 * @param userA Entidad a mapear
	 * @return Objeto DTO mapeado
	 */
	public UserAuditDTO toDTO(UserAudit userA) {
		UserAuditDTO userADTO = new UserAuditDTO();
		
		userADTO.setId(userA.getId());
		userADTO.setNombreUsuario(userA.getNombreUsuario()); 
		userADTO.setMensaje(userA.getMensaje()) ;
		userADTO.setFechaAudit(userA.getFechaAudit());
		return userADTO;
	}
	/**
	 * Mapea el objeto DTO a la entidad UserAudit
	 * @param userADTO Objeto UserAuditDTO a mapear
	 * @return Entidad mapeado
	 */
	
	public UserAudit toEntity(UserAuditDTO userADTO) {
		UserAudit userA = new UserAudit();
		
		userA.setId(userADTO.getId());
		userA.setNombreUsuario(userADTO.getNombreUsuario()); 
		userA.setMensaje(userADTO.getMensaje()) ;
		userA.setFechaAudit(userADTO.getFechaAudit());
		return userA;
	}
	
}
