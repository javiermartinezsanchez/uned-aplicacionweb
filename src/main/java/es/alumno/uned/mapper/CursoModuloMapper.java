package es.alumno.uned.mapper;

import org.springframework.stereotype.Component;

import es.alumno.uned.dto.CursoModuloDTO;
import es.alumno.uned.model.entities.CursoModulo;

@Component
public class CursoModuloMapper {

	public CursoModuloDTO toDTO(CursoModulo entidad) {
	    CursoModuloDTO dto = new CursoModuloDTO();
	    dto.setId(entidad.getId());
	    dto.setModuloId(entidad.getModulo().getId());
	    dto.setNombreModulo(entidad.getModulo().getTitulo()); // Importante para la vista
	    dto.setOrden(entidad.getOrden());
	    dto.setPeso(entidad.getPeso());
	    return dto;
	}
}
