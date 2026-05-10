package es.alumno.uned.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import es.alumno.uned.model.entities.Curso;

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
	
}
