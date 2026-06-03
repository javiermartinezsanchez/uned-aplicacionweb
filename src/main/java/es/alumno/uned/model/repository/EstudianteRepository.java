package es.alumno.uned.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.Estudiante;
/**
 * Repositorio de la entidad Estudiante.
 */
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
/**
 * Búsqueda de Estudiantes por su email (que está en Usuario)
 * @param email E-Mail del estudiante(usuario)
 * @return Un Optional del {@code Estudiante}
 */
	Optional<Estudiante> findByUsuarioEmail(String email);
	
	/**
	 * Búsqueda por email
	 * @param email Email a buscar.
	 * @param pageable Datos de la paginación.
	 * @return La página de datos encontrado.
	 */
	Page<Estudiante> findByUsuarioEmailContainingIgnoreCase(String email, Pageable pageable);
	
	/**
	 * 
	 * Búsqueda por nombre del estudiante.
	 * @param nombre Nombre a buscar.
	 * @param pageable Datos de la paginación.
	 * @return La página de datos encontrado.
	 */
	Page<Estudiante> findByUsuarioNombreContainingIgnoreCase(String nombre, Pageable pageable);

	Page<Estudiante> findByUsuarioEmailContainingIgnoreCaseAndUsuarioNombreContainingIgnoreCase(String email, String nombre, Pageable pageable);
}

