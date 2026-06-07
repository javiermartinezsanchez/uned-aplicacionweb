package es.alumno.uned.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.alumno.uned.model.entities.Curso;
/**
 * Repositorio de la entidad Curso.
 */
public interface CursoRepository extends JpaRepository<Curso, Long>, JpaSpecificationExecutor<Curso> {

	/**
	 * 	Búsqueda de cursos por Id Curso e Id responsable (Profesor).
	 * 
	 * Nos devuelve de forma paginada los cursos del Id Usuario del profesor.
	 * 
	 * @param responsableId Id del profesor asociado.
	 * @param cursoId Id del curso a consultar)
	 * @return Curso Encontrado.
	 */
	Optional<Curso> findByIdAndResponsableId(Long cursoId, Long responsableId);

	/**
	 * Búsqueda individual por titulo del curso
	 * @param titulo Título del curso
	 * @return {@code Optional<Curso>} Encontrado
	 */
	Optional<Curso> findByTitulo(String titulo);
	  
	/**
	 * Consulta de los cursos que no ha cursado el Estudiante.
	 * <p> Para los casos que no tenga definidas áreas de interés.
	 * 
	 * @param estudianteId Id del estudiante.
	 * @param pageable Configuración de la página
	 * @return Página de resultados.
	 */
	  @Query("SELECT c FROM Curso c WHERE c.id NOT IN " +
	         "(SELECT ec.id.cursoId FROM EstudianteCurso ec WHERE ec.id.estudianteId = :estudianteId)")
	  Page<Curso> findCursosDisponiblesEstudiante(@Param("estudianteId") Long estudianteId, Pageable pageable);
 
}
