package es.alumno.uned.model.repository;

import es.alumno.uned.model.entities.Estudiante;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
/**
 * Búsqueda de Estudiantes por su email (que está en Usuario)
 * @param email E-Mail del estudiante(usuario)
 * @return Un Optional del {@code Estudiante}
 */
	Optional<Estudiante> findByUsuarioEmail(String email);
}

