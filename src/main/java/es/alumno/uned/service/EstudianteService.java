package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;

import es.alumno.uned.dto.RegistroEstudianteDTO;
import es.alumno.uned.model.entities.Estudiante;

public interface EstudianteService {

	void guardar(RegistroEstudianteDTO estudiante);

	Estudiante findById(Long id);

	@Nullable
	Object findAll();

}
