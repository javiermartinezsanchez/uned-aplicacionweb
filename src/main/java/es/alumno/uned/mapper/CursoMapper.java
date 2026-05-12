package es.alumno.uned.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.dto.CursoModuloDTO;
import es.alumno.uned.dto.ModuloDTO;
import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.CursoModulo;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.AreaTematicaRepository;
import es.alumno.uned.model.repository.CursoModuloRepository;
import es.alumno.uned.model.repository.ModuloRepository;
import es.alumno.uned.model.repository.UsuarioRepository;

@Component
public class CursoMapper {

    @Autowired
    private AreaTematicaRepository areaTematicaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoModuloMapper cursoModuloMapper;
    
    @Autowired
    private ModuloRepository moduloRepository;
    
    public Curso toEntity(CursoDTO dto, Curso entity) {

        entity.setTitulo(dto.getTitulo());
        entity.setDescripcion(dto.getDescripcion());
        entity.setNivel(dto.getNivel());
        entity.setDuracion(dto.getDuracion());
        entity.setfIni(dto.getFIni());
        entity.setfFin(dto.getFFin());

        AreaTematica area = areaTematicaRepository.findById(dto.getAreaTematicaId())
                .orElseThrow(() -> new IllegalArgumentException("Área temática no encontrada"));
        entity.setAreaTematica(area);

        Usuario responsable = usuarioRepository.findById(dto.getResponsableId())
                .orElseThrow(() -> new IllegalArgumentException("Responsable no encontrado"));
        entity.setResponsable(responsable);

        entity.setUriImagen(dto.getUriImagen());
        actualizarModulos(dto, entity);
        return entity;
    }
    
    
    public CursoDTO toDTO(Curso entity) {

        CursoDTO dto = new CursoDTO();

        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setDescripcion(entity.getDescripcion());
        dto.setNivel(entity.getNivel());
        dto.setDuracion(entity.getDuracion());
        dto.setFIni(entity.getfIni());
        dto.setFFin(entity.getfFin());
        dto.setAreaTematicaId(entity.getAreaTematica().getId());
        dto.setAreaTematicaText(entity.getAreaTematica().getTitulo());
        dto.setResponsableId(entity.getResponsable().getId());
        dto.setNombreResponsable(getNombreCompleto(entity.getResponsable()));       
        dto.setUriImagen(entity.getUriImagen());
        dto.setfIns(entity.getfIns());
        dto.setUserIns(entity.getUserIns());
        dto.setValoracion(entity.getValoracion());
        dto.setUsuariosRegistrados(entity.getUsuariosRegistrados());
        dto.setNumVistas(entity.getNumVistas());
        dto.setModulos(entity.getModulos().stream()
        		.map(cursoModuloMapper :: toDTO)
        		.toList());
        
        return dto;
    }
    /**
     * Devolvemos el nombre completo del Usuario responsable del curso (profesor)
     * <p>El formato será: (nombre + " "+ apellido1 + " "+ apellido2)
     * <p> Se tiene en cuenta los posibles nullos para evitarnos problemas.
     * @param idUsuario Id del 
     * @return
     */
    private String getNombreCompleto(Usuario idUsuario) {
    	return (idUsuario.getNombre()!=null?idUsuario.getNombre():"")+ 
               (idUsuario.getApellido1() !=null?" ".concat(idUsuario.getApellido1()):"") +
    	       (idUsuario.getApellido2() !=null?" ".concat(idUsuario.getApellido2()):"");
    }
    /**
     * Realizamos el "merge" de los módulo que vienen en el DTO con los que teníamos.
     * <p>Sería mucho más sencillo setear la lista de módulos y que JPA se encargara de todo, pero Al actualizar los módulos del curso penalizaríamos mucho la BD y puede provocarnos errores diversos al realizar el commit en la BD)
     * <ol>
     * <li>Obtenemos {@link Modulo} del repositorio. Se filtra los que existan.</li>
     * <li>Se eliminan los que módulos de nuestra entidad que no existan en la nueva.</li>
     * <li>Se añaden los nuevos</li>
     * </ol>
     * @param dto El {@link CursoDTO} que viene de la vista.
     * @param entity {@link Curso} La entidad a guardar.
     */
    private void actualizarModulos(CursoDTO dto, Curso entity) {
    		entity.getModulos().clear();
            for (CursoModuloDTO mDTO : dto.getModulos()) {
            	Modulo modulo = moduloRepository.findById(mDTO.getModuloId())
						.orElseThrow();
                if (!entity.getModulos().contains(mDTO)) {
                	entity.addModulo(modulo, mDTO.getOrden(), mDTO.getPeso());
                }
            }
    }
}

