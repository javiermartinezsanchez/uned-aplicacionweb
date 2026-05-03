package es.alumno.uned.service;

import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.Modulo;

public interface EstudianteCursoService {

	void subscribirAlumnoACurso(String username, Long cursoId);

    void actualizarUltimoAcceso(String username, Long cursoId);

    void marcarModuloComoCompletado(String username, Long cursoId, Long moduloId);

    void recalcularProgreso(String username, Long cursoId);
    
	public void completarModulo(Modulo modulo, Estudiante estudiante, MultipartFile entregable);
	
}
