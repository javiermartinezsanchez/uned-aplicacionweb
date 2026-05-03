package es.alumno.uned.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.Modulo;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {

	List<Modulo> findByCursoId(Long cursoId);
}
