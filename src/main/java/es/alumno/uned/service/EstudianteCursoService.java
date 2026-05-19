package es.alumno.uned.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.dto.EstudianteCursoDTO;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.EstudianteCurso;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.util.Paginacion;

public interface EstudianteCursoService {

	EstudianteCursoDTO subscribirAlumnoACurso(Long estudianteId, Long cursoId);
	
	public Paginacion<EstudianteCurso, EstudianteCursoDTO> listadoPaginado( PageParams pageData, Map<String, String> params);

    void actualizarUltimoAcceso(String username, Long cursoId);

    void marcarModuloComoCompletado(String username, Long cursoId, Long moduloId);

    void recalcularProgreso(String username, Long cursoId);
    
	public void completarModulo(Modulo modulo, Estudiante estudiante, MultipartFile entregable);
	
}
