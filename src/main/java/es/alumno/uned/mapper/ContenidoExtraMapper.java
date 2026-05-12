package es.alumno.uned.mapper;

import org.springframework.stereotype.Component;

import es.alumno.uned.dto.ContenidoExtraDTO;
import es.alumno.uned.model.entities.ContenidoExtra;

@Component
public class ContenidoExtraMapper {

	public ContenidoExtra toEntity(ContenidoExtraDTO dto) {
		return new ContenidoExtra(
				dto.getDescripcion(), 
				dto.getUri(), 
				dto.getTipoContenido());
	}
}
