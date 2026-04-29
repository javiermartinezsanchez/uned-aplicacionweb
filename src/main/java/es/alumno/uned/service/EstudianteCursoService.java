package es.alumno.uned.service;

import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.Modulo;

public interface EstudianteCursoService {

	public void completarModulo(Modulo modulo, Estudiante estudiante, MultipartFile entregable);
	
}
