package es.alumno.uned.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.alumno.uned.dto.ContenidoExtraDTO;
import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.dto.CursoModuloDTO;
import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.entities.ContenidoExtra;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.entities.TipoContenido;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.AreaTematicaRepository;
import es.alumno.uned.model.repository.ModuloRepository;
import es.alumno.uned.model.repository.UsuarioRepository;
/**
 * Mapper Curso
 * <p>Convierte una entidad Curso a Curso DTO o viceversa.
 */
@Component
public class CursoMapper {

    @Autowired
    private AreaTematicaRepository areaTematicaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoModuloMapper cursoModuloMapper;
    
    @Autowired 
    private ContenidoExtraMapper contenidoExtraMapper;
    @Autowired
    private ModuloRepository moduloRepository;
    
    /**
     * Convertimos el DTO editado o nuevo a Entidad para su persistencia
     * @param dto Datos CursoDTO. 
     * @param entity Entidad Curso a persistir.
     * @return {@link Curso}
     */
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
        actualizarContenidoExtra(dto.getContenidosExtra(), entity);
        return entity;
    }
    
    /**
     * Mappea la entidad Curso a CursoDTO para su mostrarlo o editarlo.
     * @param entity Entidad a modificar
     * @return CursoDTO correspondiente.
     */
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
        dto.setNumValoraciones(entity.getValoraciones().size());
        dto.setUsuariosRegistrados(entity.getUsuariosRegistrados());
        dto.setNumVistas(entity.getNumVistas());
        dto.setModulos(entity.getModulos().stream()
        		.map(cursoModuloMapper :: toDTO)
        		.toList());
        dto.setContenidosExtra(entity.getContenidosExtra().stream()
        		.map(c -> new ContenidoExtraDTO(c.getId(), c.getDescripcion(), c.getUri(), c.getTipoContenido(),
        				c.getNombreReal(), c.getContentType()))
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
    		if (dto.getModulos() != null) {
	            for (CursoModuloDTO mDTO : dto.getModulos()) {
	            	Modulo modulo = moduloRepository.findById(mDTO.getModuloId())
							.orElseThrow();
	                if (!entity.getModulos().contains(mDTO)) {
	                	entity.addModulo(modulo, mDTO.getOrden(), mDTO.getPeso());
	                }
	            }
    		}
    }
    /**
     * Se mergea los contenidos que vienen informados con los actuales.
     * <ul>
     * <ol>Borramos los que no existan en la entidad actual.</ol>
     * <ol>Actualizamos los que existan y añadimos los nuevos</ol>
     * </ul>
     * @param dtos Lista de Contenidos del DTO.
     * @param entity Entidad {@link Curso} destino
     */
    private void actualizarContenidoExtra(List<ContenidoExtraDTO> dtos, Curso entity) {
    	
    	entity.getContenidosExtra().removeIf(entidad ->
    	dtos.stream().noneMatch(dto -> dto.getId() != null && dto.getId().equals(entidad.getId())));
    	
    	for (ContenidoExtraDTO dto : dtos) {
            if (dto.getId() != null) {
                // Caso ACTUALIZAR: Buscamos el objeto existente en la lista del curso
                entity.getContenidosExtra().stream()
                    .filter(entidad -> entidad.getId().equals(dto.getId()))
                    .findFirst()
                    .ifPresent(entidad -> {
                        entidad.setDescripcion(dto.getDescripcion());
                        entidad.setTipoContenido(dto.getTipoContenido());
                        // Solo actualizamos la URI si es externa (la propia se gestiona con el Multipart)
                        if (dto.getTipoContenido() == TipoContenido.EXTERNO) {
                            entidad.setUri(dto.getUri());
                        }
                    });
            } else {
                // Caso NUEVO: Convertimos DTO a Entidad y añadimos
                ContenidoExtra nuevaEntidad = contenidoExtraMapper.toEntity(dto);
                entity.addContenidoExtra(nuevaEntidad); // Usamos el helper para el curso_id
            }
    	 }
    }
}

