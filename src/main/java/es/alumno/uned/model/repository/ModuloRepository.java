package es.alumno.uned.model.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.entities.TipoModulo;
/**
 * Repositorio para la entidad Módulo.
 * 
 * Contiene las operaciones no incluidas en el {@link JpaRepository} y que añadimos para nuestra operativa.
 */
public interface ModuloRepository extends JpaRepository<Modulo, Long> {

	Modulo findByTituloContainingIgnoreCase(String Titulo);
	Page<Modulo> findByTituloContainingIgnoreCase(String Titulo, Pageable pageable);
	
	Page<Modulo> findByTipo(TipoModulo tipoModulo, Pageable pageable);
	
	Page<Modulo> findByTituloContainingIgnoreCaseAndTipo(String Titulo, TipoModulo tipoModulo, Pageable pagable);
	//List<Modulo> findByCursoId(Long cursoId);
}
