package es.alumno.uned.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.AreaTematicaRepository;
import es.alumno.uned.model.repository.EstudianteRepository;

@Component
public class EstudianteMapper {
	
	private EstudianteRepository estudianteRepo;
	private AreaTematicaMapper areaMapper;
	private AreaTematicaRepository areaRepository;
	public EstudianteMapper(EstudianteRepository estudianteRepo, 
			AreaTematicaMapper areaMapper,
			AreaTematicaRepository areaRepository) {
		this.estudianteRepo = estudianteRepo;
		this.areaMapper = areaMapper;
		this.areaRepository = areaRepository;
	}

	public Estudiante toEntity(EstudianteDTO estudianteDTO, String userAlta) {
		Estudiante estudiante;
		if (estudianteDTO.getId() == null) {
			estudiante = new Estudiante();
			estudiante.setUsuario(new Usuario());
			estudiante.getUsuario().setEmail(estudianteDTO.getEmail());
			estudiante.getUsuario().setActivo(true);
			estudiante.getUsuario().setRol("ESTUD");
			estudiante.getUsuario().setfAlta(LocalDateTime.now());
			estudiante.getUsuario().setUsuarioAlta(userAlta);

		}
		else {
			estudiante = estudianteRepo.getReferenceById(estudianteDTO.getId());
		}	
		estudiante.getUsuario().setNombre(estudianteDTO.getNombre());
		estudiante.getUsuario().setApellido1(estudianteDTO.getApellido1());
		estudiante.getUsuario().setApellido2(estudianteDTO.getApellido2());
		estudiante.setDireccion(estudianteDTO.getDireccion());
		estudiante.setPoblacion(estudianteDTO.getPoblacion());
		estudiante.setProvincia(estudianteDTO.getProvincia());
		estudiante.setCodPostal(estudianteDTO.getCodPostal());
		//estudiante.setAreasInteres(estudianteDTO.getAreasInteres().stream().map(areaMapper :: toEntity).toList());
		 actualizarAreas(estudianteDTO, estudiante);
		
		return estudiante;
	}
	public EstudianteDTO toDTO(Estudiante estudiante) {
		EstudianteDTO eDTO = new EstudianteDTO();
		eDTO.setId(estudiante.getId());
		eDTO.setNombre(estudiante.getUsuario().getNombre());
		eDTO.setApellido1(estudiante.getUsuario().getApellido1());
		eDTO.setApellido2(estudiante.getUsuario().getApellido2());
		eDTO.setRol(estudiante.getUsuario().getRol());
		eDTO.setEmail(estudiante.getUsuario().getEmail());
		eDTO.setActivo(estudiante.getUsuario().isActivo());
		eDTO.setfAlta(estudiante.getUsuario().getfAlta());
		eDTO.setUsuarioAlta(estudiante.getUsuario().getUsuarioAlta());
		eDTO.setDireccion(estudiante.getDireccion());
		eDTO.setPoblacion(estudiante.getPoblacion());
		eDTO.setProvincia(estudiante.getProvincia());
		eDTO.setCodPostal(estudiante.getCodPostal());
		eDTO.setAreasInteres(estudiante.getAreasInteres().stream().map(areaMapper :: toDTO).toList());
	return eDTO;
	}
	 /**
     * Realizamos el "merge" de las áreas de interés del Estudiante que vienen el DTO con los que teníamos.
     * 
     * <ol>
     * <li>Obtenemos {@link AreaInteres} del repositorio. Se filtra los que existan.</li>
     * <li>Se eliminan las Áreas que tenía y que ya no vienen en nuestro DTO.</li>
     * <li>Se añaden los nuevos</li>
     * </ol>
     * @param dto El {@link EstudianteDTO} que viene de la vista.
     * @param entity {@link Estudiante} La entidad a guardar.
     */
	 private void actualizarAreas(EstudianteDTO dto, Estudiante entity) {
		 List<AreaTematica> nuevasAreas = (dto.getAreasInteres() == null) ? new ArrayList<>() : dto.getAreasInteres()
				 .stream().filter(a -> a.getId() != null)
				 .<Optional<AreaTematica>>map(a -> areaRepository.findById(a.getId()))
				 .flatMap(Optional::stream)
				 .toList();
		 entity.getAreasInteres().removeIf(m-> !nuevasAreas.contains(m));
		 for (AreaTematica m : nuevasAreas) {
			 if (!entity.getAreasInteres().contains(m)) {
				 entity.addAreaInteres(m);
			 }
		 }
		 
	 }
}
