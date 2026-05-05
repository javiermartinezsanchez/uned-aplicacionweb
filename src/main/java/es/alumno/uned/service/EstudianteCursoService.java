package es.alumno.uned.service;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.util.Paginacion;

public interface EstudianteCursoService {

	void subscribirAlumnoACurso(String username, Long cursoId);

    void actualizarUltimoAcceso(String username, Long cursoId);

    void marcarModuloComoCompletado(String username, Long cursoId, Long moduloId);

    void recalcularProgreso(String username, Long cursoId);
    
	public void completarModulo(Modulo modulo, Estudiante estudiante, MultipartFile entregable);
	
	public Paginacion<EstudianteCurso, EstudianteCursoDTO> listadoPaginado(String url,  Pageable pageable);
}
