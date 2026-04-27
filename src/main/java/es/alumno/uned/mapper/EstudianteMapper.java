package es.alumno.uned.mapper;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.EstudianteRepository;

@Component
public class EstudianteMapper {

	
	private EstudianteRepository estudianteRepo;
	
	public EstudianteMapper(EstudianteRepository estudianteRepo) {
		this.estudianteRepo = estudianteRepo;
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
		estudiante.setPoblacion(estudiante.getPoblacion());
		estudiante.setProvincia(estudianteDTO.getProvincia());
		estudiante.setCodPostal(estudianteDTO.getCodPostal());
		return estudiante;
	}
}
