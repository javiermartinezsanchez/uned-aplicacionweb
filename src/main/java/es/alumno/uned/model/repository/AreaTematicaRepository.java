package es.alumno.uned.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.AreaTematica;

public interface AreaTematicaRepository extends JpaRepository<AreaTematica, Long>{
	/** Búsqueda individual por Título 
	 * 
	 * @param titulo Título a buscar. 
	 * @return Area temática encontrada.
	 */
	AreaTematica findByTitulo(String titulo);
	/**
	 * Búsqueda de Áreas por título.
	 * @param titulo Cadena a buscar que la contenga.
	 * @param pageable Definición de la página. 
	 * @return {@code Page<AreaTematica>} resultado de la búsqueda.
	 */
	Page<AreaTematica> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

	/**
	 * Búsqueda de Áreas por descripción.
	 * @param descripcion Cadena a buscar que la contenga.
	 * @param pageable Definición de la página.
	 * @return {@code Page<AreaTematica>} resultado de la búsqueda.
	 */
	
	Page<AreaTematica> findByDescripcionContainingIgnoreCase(String descripcion, Pageable pageable);
	/**
	 * Búsqueda de Areas por título y descripcion.
	 * 
	 * @param titulo Cadena a buscar que la contenga.
	 * @param descripcion Cadena a buscar que la contenga.
	 * @param pageable pageable Definición de la página.
	 * @return {@code Page<AreaTematica>} resultado de la búsqueda.
	 */
	Page<AreaTematica> findByTituloContainingIgnoreCaseAndDescripcionContainingIgnoreCase(
	        String titulo,
	        String descripcion,
	        Pageable pageable
	);
}
