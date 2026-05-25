package es.alumno.uned.mapper;

import org.springframework.stereotype.Component;

import es.alumno.uned.dto.EstudianteCursoModuloDTO;
import es.alumno.uned.model.entities.EstudianteCursoModulo;
/**
 * Mapper de la entidad EstudianteCursoModulo a EstudianteCursoModuloDTO
 * 
 * <p>toDTO Nos devuelve el DTO de la entidad.
 * 
 */
@Component
public class EstudianteCursoModuloMapper {

	public EstudianteCursoModuloDTO toDTO(EstudianteCursoModulo entidad) {
		EstudianteCursoModuloDTO dto = new EstudianteCursoModuloDTO();
				
		dto.setEstudianteId(entidad.getEstudianteCurso().getId().getEstudianteId());
		dto.setCursoId(entidad.getEstudianteCurso().getId().getCursoId());
		dto.setModuloId(entidad.getModulo().getId()); 
		dto.setTitulo(entidad.getTitulo());
		dto.setDescripcion(entidad.getDescripcion());
		dto.setContenido(entidad.getContenido());
		dto.setTipoModulo(entidad.getTipo());
		dto.setCompletado(entidad.getCompletado()) ;
		dto.setOrden(entidad.getOrden());
		dto.setEstado(entidad.getEstado());
		dto.setPeso(entidad.getPeso());
		dto.setFechaCompletado(entidad.getFechaCompletado());
		dto.setFechaUltimoAcceso(entidad.getFechaUltimoAcceso());
		dto.setUrlEntrega(entidad.getUrlEntrega());
		dto.setFechaEntrega(entidad.getFechaEntrega());
		dto.setFechaRevision(entidad.getFechaRevision());
		dto.setCalificacion(entidad.getCalificacion());
		dto.setNotasCalificacion(entidad.getNotasCalificacion());

		return dto;

	}
}
