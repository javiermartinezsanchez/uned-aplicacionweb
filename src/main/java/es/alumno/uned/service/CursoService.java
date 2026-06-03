package es.alumno.uned.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.records.FicheroData;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.util.Paginacion;


/**
 * Interface público de los métodos de acceso a Cursos.
 */
public interface CursoService {

	/**
	 * Devolvemos un curso según del Id enviado
	 * @param id Id del curso
	 * @return DTO del curso
	 */
	public CursoDTO getCurso(Long id);
	
	/**
	 * Devolvemos un curso para el id enviado y el responsable.id que le enviamos.
	 * <p>Se utilizará para registrar las visitas a un curso por parte de usuarios anónimos o estudiantes 
	 * 
	 * <p>En el caso de no existir, se devuelve un curso vacio.
	 * 
	 * @param idCurso Id del Curso a Buscar.
	 * @param marcarVisita Cadena que nos indica que hay que registrar la visita.
	 * @return {@link CursoDTO} Encontrado.
	 */
	public CursoDTO getCurso(Long idCurso, String marcarVisita);
	
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
     * Proceso de guardado de una valoración de un curso
     * @param cursoId Identificador del curso que se valora.
     * @param valoracion Valoración (1-5)
     * @param usuario Nombre de usuario que valora.
     * @return Se devuelve la media de valoración del curso
     */
    BigDecimal guardarValoracion(Long cursoId, Integer valoracion, String usuario);
}
