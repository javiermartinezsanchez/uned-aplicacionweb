package es.alumno.uned.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.records.FicheroData;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.util.Paginacion;



public interface CursoService {

	/**
	 * Devolvemos un curso según del Id enviado
	 * @param id Id del curso
	 * @return DTO del curso
	 */
	public CursoDTO getCurso(Long id);
	
	/**
	 * Devolvemos un curso para el id enviado y el responsable.id que le enviamos.
	 * <p>Básicamente lo utilizaremos en los diferentes "get" de cursos para evitar que un profesor (responsable), pueda ver/modificar los 
	 * demás cursos asignados a otros profesores.
	 * <p>En el caso de no existir, o que esté asignado a otro profesor, se generará un {@code CursoNotExistException}
	 * 
	 * @param idCurso Id del Curso a Buscar.
	 * @param idUsuario Id del Usuario que realiza la consulta.
	 * @return {@link CursoDTO} Encontrado.
	 */
	public CursoDTO getCurso(Long idCurso, Long idUsuario);
	
	/**
	 * Método para dar de alta o modificar un curso.
	 * @param dto Datos del curso
	 * @param imagen Imagen si la enviamos
	 * @param contenidosExtraFiles Contenidos extra,INTERNOS que adjuntan documentación.
	 * @param usuario Usuario que realiza el alta.
	 * @return Devuelve el curso creado/modificado.
	 * @throws IOException 
	 */
	CursoDTO grabar(CursoDTO dto, FicheroData imagen, List<FicheroData> contenidosExtraFiles, String usuario) throws IOException;

	public Paginacion<Curso, CursoDTO> listadoPaginado( Pageable pageable);
	
	/**
	 * Método para consultas de Cursos con un mapa de parámetros para realizar
	 * consultas de forma dinámica.
	 * 
	 * @param pageData Definición de la página de datos a generar.
	 * @param params Mapa de parámetros {nombre=valor,....} que definirá la búsqueda.
	 * @return {@link Paginacion} Página de datos (Cursos) que cumplen las condiciones de búsqueda.
	 */
	public Paginacion<Curso, CursoDTO> listadoPaginado( PageParams pageData, Map<String, String> params);

	/**
	 * Consulta de cursos para home ordenado por número de visitas.
	 * @param pageData Datos de la paginación
	 * @param params Parámetros de búsqueda.
	 * @return Página de datos encontrados ordenados por NumVisitas descendente.
	 */
	public Paginacion<Curso, CursoDTO> listadoOrderByNumVisistas( PageParams pageData, Map<String, String> params);

	/**
	 * Consulta de cursos para home ordenado por Valoración.
	 * @param pageData Datos de la paginación
	 * @param params Parámetros de búsqueda.
	 * @return Página de datos encontrados ordenados por NumVisitas descendente.
	 */
	public Paginacion<Curso, CursoDTO> listadoOrderByValoracion( PageParams pageData, Map<String, String> params);

	/**
	 * Consulta de cursos para home ordenado por número de inscritos.
	 * @param pageData Datos de la paginación
	 * @param params Parámetros de búsqueda.
	 * @return Página de datos encontrados ordenados por NumVisitas descendente.
	 */
	public Paginacion<Curso, CursoDTO> listadoOrderByInscritos( PageParams pageData, Map<String, String> params);

	/**
	 * Consulta de cursos para estudiantes por su área de interés.
	 * <p> En los casos en los que no tenga áreas de interés se mostrarán todos.
	 * <p> No se mostrarán los cursos en los que haya participado el Estudiante.
	 * @param pageData Datos de la paginación
	 * @param idEstudiante Id del Estudiante
	 * @param areasId Lista de areas de interés que tiene marcadas. Si no tiene marcada ninguna se devolverán 
	 * @return
	 */
	public Paginacion<Curso, CursoDTO> listadoCursosDisponiblesPorAreas(  PageParams pageData, Long idEstudiante,  List<Long> areasId);

    /**
     * Proceso de guardado de una valoración de un curso
     * @param cursoId Identificador del curso que se valora.
     * @param valoracion Valoración (1-5)
     * @param usuario Nombre de usuario que valora.
     * @return Se devuelve la media de valoración del curso
     */
    BigDecimal guardarValoracion(Long cursoId, Integer valoracion, String usuario);
}
