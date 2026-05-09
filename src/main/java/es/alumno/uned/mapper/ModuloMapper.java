package es.alumno.uned.mapper;

import org.springframework.stereotype.Component;

import es.alumno.uned.dto.ModuloDTO;
import es.alumno.uned.exception.ModuloNotFoundException;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.repository.ModuloRepository;

/**
 * Clase Mapper de la entidad Modulo
 */
@Component
public class ModuloMapper {

	
	private final ModuloRepository moduloRepository;
	
	
	public ModuloMapper(ModuloRepository moduloRepository) {
		this.moduloRepository = moduloRepository;
	}
    public ModuloDTO toDTO(Modulo m) {
        return new ModuloDTO(
                m.getId(),
                //m.getCurso().getId(),
                m.getTitulo(),
                m.getDescripcion(),
                m.getContenido(),
                //m.getPeso(),
                m.getTipo(),
                m.getfIns(),
                m.getUserIns()
        );
    }

    public Modulo toEntity(ModuloDTO moduloDTO) {
    	Modulo modulo;
    	if (moduloDTO.getId() == null) {
    		modulo = new Modulo();
    	}
    	else {
    		modulo = moduloRepository.findById(moduloDTO.getId())
    				.orElseThrow(() -> new ModuloNotFoundException("msg.exception.notfound", moduloDTO,"modulo.title"));
    	}

        modulo.setId(moduloDTO.getId());
        modulo.setTitulo(moduloDTO.getTitulo());
        modulo.setDescripcion(moduloDTO.getDescripcion());
        modulo.setContenido(moduloDTO.getContenido());
        //m.setPeso(dto.peso());
        modulo.setTipo(moduloDTO.getTipo());

//        Curso c = new Curso();
//        c.setId(dto.cursoId());
//        m.setCurso(c);

        return modulo;
    }
}
