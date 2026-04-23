package es.alumno.uned.model.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

/**
 */
	/**
	 * 	Búsqueda de cursos por responsable (Profesor).
	 * 
	 * Nos devuelve de forma paginada los cursos del Id Usuario del profesor.
	 * 
	 * @param responsableId Id del profesor asociado.
	 * @param pageable Definición de la paginación, (número de página solicitada y tamaño de la página)
	 * @return Página de datos encontrados.
	 */
	Page<Curso> findByResponsableId(Long responsableId, Pageable pageable);

	/**
	 * 	Búsqueda de cursos por Area Temática.
	 * 
	 * Nos devuelve de forma paginada los cursos de Área Temática solicitada.
	 * 
	 * @param areaId Identificador del área buscada.
	 * @param pageable Definición de la paginación, (número de página solicitada y tamaño de la página)
	 * @return Página de datos encontrados.
	 */
	Page<Curso> findByAreaTematicaId(Long areaId, Pageable pageable);

	/**
	 * 	Búsqueda de cursos por Nivel.
	 * 
	 * Nos devuelve de forma paginada los cursos de Área Temática solicitada.
	 * 
	 * @param nivel Valor de nivel buscado.
	 * @param pageable Definición de la paginación, (número de página solicitada y tamaño de la página)
	 * @return Página de datos encontrados.
	 */
	Page<Curso> findByNivel(int nivel, Pageable pageable);

	/**
	 * 	Búsqueda de cursos por Nombre del título.
	 * 
	 * Nos devuelve de forma paginada los cursos del texto solicitado.
	 * 
	 * @param titulo Cadena de búsqueda del "titulo" del curso.
	 * @param pageable Definición de la paginación, (número de página solicitada y tamaño de la página)
	 * @return Página de datos encontrados.
	 */
	Page<Curso> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

	/**
	 * 	Búsqueda de cursos por su fecha de comienzo.
	 *  Entre dos fechas.
	 *  
	 * Nos devuelve de forma paginada los cursos según las fechas enviadas.
	 * 
	 * @param desde Fecha Inicial de búsqueda.
	 * @param hasta  Fecha Final de búsqueda
	 * @param pageable Definición de la paginación, (número de página solicitada y tamaño de la página)
	 * @return Página de datos encontrados.
	 */
	Page<Curso> findByFIniBetween(LocalDateTime desde, LocalDateTime hasta, Pageable pageable);
	/**
	 * 	Búsqueda de cursos por su fecha de finalización.
	 *  Entre dos fechas (intervalo).
	 * 
	 * @param desde Fecha Inicial de búsqueda.
	 * @param hasta  Fecha Final de búsqueda
	 * @param pageable Definición de la paginación, (número de página solicitada y tamaño de la página)
	 * @return Página de datos encontrados.
	 */
	Page<Curso> findByFFinBetween(LocalDateTime desde, LocalDateTime hasta, Pageable pageable);
	/**
	 * Búsqueda de curso de un responsable para cierto nivel.
	 * 
	 * 
	 * @param responsableId Id del usuario del responsable (Profesor)
	 * @param nivel Valor del nivel asignado al curso
	 * @param pageable Definición de la paginación, (número de página solicitada y tamaño de la página)
	 * @return Página de datos encontrados.
	 */
	Page<Curso> findByResponsableIdAndNivel(Long responsableId, int nivel, Pageable pageable);
	/**
	 * Búsqueda de cursos para un área temática y un nivel
	 * @param areaId Identificador del Área a buscar.
	 * @param nivel Valor del nivel del curso a buscar.
	 * @param pageable Definición de la paginación, (número de página solicitada y tamaño de la página)
	 * @return Página de datos encontrados.
	 */
	Page<Curso> findByAreaTematicaIdAndNivel(Long areaId, int nivel, Pageable pageable);
	/**
	 * Búsqueda combinada de cursos por título, área y Responsable.
	 * @param titulo Texto del curso a buscar (título)
	 * @param areaId Identificador del Área a buscar.
	 * @param nivel Valor del nivel del curso a buscar.
	 * @param pageable Definición de la paginación, (número de página solicitada y tamaño de la página)
	 * @return Página de datos encontrados.
	 */
	Page<Curso> findByTituloContainingIgnoreCaseAndAreaTematicaIdAndResponsableId(
	        String titulo,
	        Long areaId,
	        Long responsableId,
	        Pageable pageable
	);
	/**
	 * 	Búsqueda de cursos a partir de una fecha de inicio.
	 *  Entre dos fechas.
	 *  
	 * Nos devuelve de forma paginada los cursos según las fechas enviadas.
	 * 
	 * @param fecha Fecha a partir deInicial de búsqueda.
	 * @param pageable Definición de la paginación, (número de página solicitada y tamaño de la página)
	 * @return Página de datos encontrados.
	 */
	Page<Curso> findByFIniAfter(LocalDateTime fecha, Pageable pageable);
	/**
	 * 	Búsqueda de cursos que finalizen antes de una fecha.
	 *  
	 * Nos devuelve de forma paginada los cursos que finalizen "antes" de la fecha enviada.
	 * 
	 * @param fecha Fecha final de búsqueda.
	 * @param pageable Definición de la paginación, (número de página solicitada y tamaño de la página)
	 * @return Página de datos encontrados.
	 */
	Page<Curso> findByFFinBefore(LocalDateTime fecha, Pageable pageable);

}
