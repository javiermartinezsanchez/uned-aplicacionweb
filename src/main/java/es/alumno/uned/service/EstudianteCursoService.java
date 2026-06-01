package es.alumno.uned.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.dto.EstudianteCursoDTO;
import es.alumno.uned.dto.EstudianteCursoModuloDTO;
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
	
	/**
	 * Proceso de baja de un curso activo para un alumno
	 * @param estudianteId Identificador del Estudiante
	 * @param cursoId Identificador del Curso
	 * @return El EstudianteCursoDTO actualizado EstadoCursoModulo.BAJA
	 */
	EstudianteCursoDTO bajaAlumnoCurso(Long estudianteId, Long cursoId);
	
	/**
	 * Obtenemos el listado de Estudiante Curso Paginado
	 * @param pageData Configuración de la paginación
	 * @param params Parámetros de búsqueda
	 * @return Página de datos encontrados.
	 */
	public Paginacion<EstudianteCurso, EstudianteCursoDTO> listadoPaginado( PageParams pageData, Map<String, String> params);

	/**
	 * Devuelve el número de módulos (tareas) en estado PENDIENTE_REVISION
	 * @param idResponsable Identificador del Responsable (Profesor)
	 * @return El número de tareas pendientes.
	 */
	public Long getTareasPendientes(Long idResponsable);
	/**
	 * Devolvemos la lista de tareas pendientes (estudianteCursoModulo.estado = PENDIENTE_REVISION)
	 * <p>Se devolverá ordenado por fecha de entrega descendente.
	 * @param pageData Datos de la paginación.
	 * @param params Parámetros de búsqueda.
	 * @return Página de datos
	 */
	public Paginacion<EstudianteCurso, EstudianteCursoDTO> listadoTareasPendientes( PageParams pageData, Map<String, String> params);
	
    void actualizarUltimoAcceso(String username, Long cursoId);

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
	
	/**
	 * Se actualiza los datos del EstudianteCurso y del EstudianteCursoModulo revisado por el responsable.
	 * <p>En caso de éxito, se persisten datos, y se actualiza estado. 
	 * <p>El módulo deberá de estar en estado PENDIENTE_REVISIÓN y pasará a REVISADO.
	 * @param ec EstudianteCurso con el módulo calificado.
	 */
	public void calificarModulo(EstudianteCursoModuloDTO ec);
	
	/**
	 * Devuelve el EstudianteCurso según los parámetros enviados.
	 * 
	 * @param idResponsable Identificador del responsable.
	 * @param idEstudiante Identificador del Estudiante.
	 * @param idCurso Identificador del Curso
	 * @param idModulo Identificador del módulo.
	 * @return DTO encontrado.
	 */
	EstudianteCursoDTO getCursoModulo(Long idResponsable, Long idEstudiante, Long idCurso, Long idModulo);
}
