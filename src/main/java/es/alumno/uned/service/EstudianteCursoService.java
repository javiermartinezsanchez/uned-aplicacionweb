package es.alumno.uned.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.dto.EstudianteCursoDTO;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.EstudianteCurso;
import es.alumno.uned.model.entities.EstudianteCursoModulo;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.entities.TipoModulo;
import es.alumno.uned.model.records.FicheroData;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.util.Paginacion;
/**
 * Interfaz pública de Estudiante Curso.
 * 
 * 
 */
public interface EstudianteCursoService {

	/**
	 * Obtenemos el EstudianteCurso de los parámetros enviados.
	 * @param idEstudiante Identificador del Estudiante.
	 * @param idCurso Identificador del Curso
	 * @return EstudianteCursoDTO encontrado.
	 * 
	 */
	EstudianteCursoDTO getCurso(Long idEstudiante, Long idCurso);
	/**
	 * Proceso de subscripción de un alumno a un curso. 
	 * @param estudianteId Identificador del Estudiante.
	 * @param cursoId Identificador del Curso
	 * @return El EstudianteCursoDTO creado (si no existía).
	 */
	EstudianteCursoDTO subscribirAlumnoACurso(Long estudianteId, Long cursoId);
	
	public Paginacion<EstudianteCurso, EstudianteCursoDTO> listadoPaginado( PageParams pageData, Map<String, String> params);

    void actualizarUltimoAcceso(String username, Long cursoId);

    void marcarModuloComoCompletado(String username, Long cursoId, Long moduloId);

    /**
     * Se recalcula el progreso del curso de acuerdo al estado del módulo enviado.
     * <p>Se determinará si está en estado "COMPLETADO".
     * <p>Se añadirá el peso al avance del curso. 
     * @param ecm EstudianteCursoModulo actual.
     */
    void recalcularProgreso(EstudianteCursoModulo ecm);
    /**
     * Completamos un módulo de un curso.
     * 
     * @param estudianteId Identificador del estudiante.
     * @param cursoId Identificador del curso.
     * @param moduloId Identificador del Módulo.
     * @param tipoModulo Tipo de Módulo (ENTREGA_OBLIGATORIA, FINALIZACION_MANUAL)
     * @param ficheroEntrega Fichero de la entrega en caso de "ENTREGA_OBLIGATORIA".
     */
	public void completarModulo(Long estudianteId, Long cursoId, Long moduloId, TipoModulo tipoModulo, FicheroData ficheroEntrega) throws IOException;
	
}
