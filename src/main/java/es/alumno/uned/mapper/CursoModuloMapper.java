package es.alumno.uned.mapper;

import org.springframework.stereotype.Component;

import es.alumno.uned.dto.CursoModuloDTO;
import es.alumno.uned.model.entities.CursoModulo;
/**
 * Mapper de CursoModulo
 */
@Component
public class CursoModuloMapper {

	public CursoModuloDTO toDTO(CursoModulo entidad) {
	    CursoModuloDTO dto = new CursoModuloDTO();
	    dto.setCursoId(entidad.getId().getCursoId());
	    dto.setModuloId(entidad.getId().getModuloId());
	    dto.setNombreModulo(entidad.getModulo().getTitulo()); // Importante para la vista
	    dto.setContenido(entidad.getModulo().getContenido());
	    dto.setDescripcion(entidad.getModulo().getDescripcion());
	    dto.setOrden(entidad.getOrden());
	    dto.setPeso(entidad.getPeso());
	    dto.setTipoModulo(entidad.getModulo().getTipo());
	    return dto;
	}
}
