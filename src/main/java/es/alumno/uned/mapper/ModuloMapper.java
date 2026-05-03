package es.alumno.uned.mapper;

import org.springframework.stereotype.Component;

import es.alumno.uned.dto.ModuloDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.Modulo;

/**
 * Clase Mapper de la entidad Moduulo
 */
@Component
public class ModuloMapper {

    public ModuloDTO toDTO(Modulo m) {
        return new ModuloDTO(
                m.getId(),
                m.getCurso().getId(),
                m.getTitulo(),
                m.getDescripcion(),
                m.getContenido(),
                m.getPeso(),
                m.getTipo(),
                m.isRequiereEntrega(),
                m.isFinalizacionAutomatica()
        );
    }

    public Modulo toEntity(ModuloDTO dto) {
        Modulo m = new Modulo();

        m.setId(dto.id());
        m.setTitulo(dto.titulo());
        m.setDescripcion(dto.descripcion());
        m.setContenido(dto.contenido());
        m.setPeso(dto.peso());
        m.setTipo(dto.tipo());
        m.setRequiereEntrega(dto.requiereEntrega());
        m.setFinalizacionAutomatica(dto.finalizacionAutomatica());

        Curso c = new Curso();
        c.setId(dto.cursoId());
        m.setCurso(c);

        return m;
    }
}
