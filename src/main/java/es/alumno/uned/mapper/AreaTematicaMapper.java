package es.alumno.uned.mapper;

import org.springframework.stereotype.Component;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.model.entities.AreaTematica;

@Component
public class AreaTematicaMapper {

	public AreaTematica toEntity(AreaTematicaDTO areaDto) {
		AreaTematica area = new AreaTematica();
		area.setId(areaDto.getId());
		area.setTitulo(areaDto.getTitulo());
		area.setDescripcion(areaDto.getDescripcion());
		return area;
	}
	
	public AreaTematicaDTO toDTO(AreaTematica entity) {
		AreaTematicaDTO areaDto = new AreaTematicaDTO();
		areaDto.setId(entity.getId());
		areaDto.setTitulo(entity.getTitulo());
		areaDto.setDescripcion(entity.getDescripcion());
		return areaDto;
	}
}
