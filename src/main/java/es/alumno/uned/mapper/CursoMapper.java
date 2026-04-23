package es.alumno.uned.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.AreaTematicaRepository;
import es.alumno.uned.model.repository.UsuarioRepository;

@Component
public class CursoMapper {

    @Autowired
    private AreaTematicaRepository areaTematicaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Curso toEntity(CursoDTO dto, Curso entity) {

        entity.setTitulo(dto.getTitulo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setNivel(dto.getNivel());
        entity.setfIni(dto.getfIni());
        entity.setfFin(dto.getfFin());

        // Área Temática
        AreaTematica area = areaTematicaRepository.findById(dto.getAreaTematicaId())
                .orElseThrow(() -> new IllegalArgumentException("Área temática no encontrada"));
        entity.setAreaTematica(area);

        // Responsable
        Usuario responsable = usuarioRepository.findById(dto.getResponsableId())
                .orElseThrow(() -> new IllegalArgumentException("Responsable no encontrado"));
        entity.setResponsable(responsable);

        // Imagen (solo el nombre del fichero)
        entity.setUriImagen(dto.getUriImagen());

        // Auditoría
        entity.setfIns(dto.getfIni());
        entity.setUserIns(dto.getUserIns());

        return entity;
    }
    
    
    public CursoDTO toDTO(Curso entity) {

        CursoDTO dto = new CursoDTO();

        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setNivel(entity.getNivel());
        dto.setfIni(entity.getfIni());
        dto.setfFin(entity.getfFin());

        dto.setAreaTematicaId(entity.getAreaTematica().getId());
        dto.setResponsableId(entity.getResponsable().getId());

        dto.setUriImagen(entity.getUriImagen());

        dto.setfIns(entity.getfIns());
        dto.setUserIns(entity.getUserIns());

        return dto;
    }

    
}

