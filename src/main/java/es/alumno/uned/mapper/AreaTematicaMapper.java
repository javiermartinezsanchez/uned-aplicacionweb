package es.alumno.uned.mapper;

import org.springframework.stereotype.Component;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.model.entities.AreaTematica;
/**
 * Mapper de AreaTematica
 */
@Component
public class AreaTematicaMapper {

	/** 
	 * Mapeo desde DTO a Entidad
	 * @param areaDto DTO a mapear
	 * @return Entidad resultante.
	 */
	public AreaTematica toEntity(AreaTematicaDTO areaDto) {
		AreaTematica area = new AreaTematica();
		area.setId(areaDto.getId());
		area.setTitulo(areaDto.getTitulo());
		area.setDescripcion(areaDto.getDescripcion());
		return area;
	}
	/**
	 * Mapeo desde DTO a Entidad
	 * @param entity Entidad a mapear
	 * @return DTO resultante.
	 */
	public AreaTematicaDTO toDTO(AreaTematica entity) {
		AreaTematicaDTO areaDto = new AreaTematicaDTO();
		areaDto.setId(entity.getId());
		areaDto.setTitulo(entity.getTitulo());
		areaDto.setDescripcion(entity.getDescripcion());
		areaDto.setNumCursos(entity.getNumCursosArea());
		return areaDto;
	}
	
//	public AreaTematicaDTO toDTOList(AreaTematica entity) {
//		AreaTematicaDTO areaDto = toDTO(entity);
//		areaDto.setNumCursos(entity.getNumCursosArea());
//		return areaDto;
//	}
}
