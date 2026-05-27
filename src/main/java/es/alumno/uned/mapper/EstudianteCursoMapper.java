package es.alumno.uned.mapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.alumno.uned.dto.EstudianteCursoDTO;
import es.alumno.uned.model.entities.EstadoCursoModulo;
import es.alumno.uned.model.entities.EstudianteCurso;

/**
 * 
 */
@Component
public class EstudianteCursoMapper {
	@Autowired
	EstudianteMapper estudianteMapper;
	@Autowired
	CursoMapper cursoMapper;
	@Autowired
	ContenidoExtraMapper contenidoExtraMapper;
	@Autowired
	EstudianteCursoModuloMapper estudianteCursoModuloMapper;
	public EstudianteCurso toEntity(EstudianteCursoDTO dto) {
		return null;
	}
	
	public EstudianteCursoDTO toDTO(EstudianteCurso entity) {
		var dto = toDTOList( entity);
        dto.setModulos(entity.getModulos().stream()
        		.map(estudianteCursoModuloMapper :: toDTO)
        		.toList());
		dto.setContenidosExtra(entity.getCurso().getContenidosExtra().stream()
				.map(contenidoExtraMapper:: toDTO)
				.toList());		
		
		return dto;
	}
	/**
	 * Se genera un DTO "reducido" para listados.
	 * @param entity Entidad a convertir.
	 * @return DTO resultante.
	 */
	public EstudianteCursoDTO toDTOList(EstudianteCurso entity) {
		EstudianteCursoDTO dto = new EstudianteCursoDTO();
		dto.setEstudiante(estudianteMapper.toDTO(entity.getEstudiante()));
        dto.setCurso(cursoMapper.toDTO(entity.getCurso()));
        dto.setFechaSubscripcion(entity.getFechaSubscripcion());
        dto.setFechaUltimoAcceso(entity.getFechaUltimoAcceso());
        dto.setFechaCompletado(entity.getFechaCompletado());
        dto.setProgreso(entity.getProgreso());
        dto.setCalificacionFinal(entity.getCalificacionFinal());
        dto.setEstado(entity.getEstado());
		
		return dto;
		
	}
	/**
	 * Mapeo al DTO pero sólo volcando el módulo en estado PENDIENTE_REVISION
	 * @param entity Entidad a mapear
	 * @return DTO Con el módulo (sólo debería existir uno) Pendiente
	 */
	public EstudianteCursoDTO toDTOPendientes(EstudianteCurso entity) {
		var dto = toDTOList( entity);
		var estadoPendiente = EstadoCursoModulo.PENDIENTE_REVISION;
        dto.setModulos(entity.getModulos().stream()
        		.filter(m -> m.getEstado().equals(estadoPendiente))
        		.map(estudianteCursoModuloMapper :: toDTO)
        		.toList());
       return dto;
	}
}
