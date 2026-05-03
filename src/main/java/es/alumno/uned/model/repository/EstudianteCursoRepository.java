package es.alumno.uned.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.EstudianteCurso;
import es.alumno.uned.model.entities.EstudianteCursoId;

public interface EstudianteCursoRepository extends JpaRepository<EstudianteCurso, EstudianteCursoId> {
	boolean existsByIdEstudianteIdAndIdCursoId(Long estudianteId, Long cursoId);

    Optional<EstudianteCurso> findByEstudianteIdAndIdCursoId(Long estudianteId, Long cursoId);
}
