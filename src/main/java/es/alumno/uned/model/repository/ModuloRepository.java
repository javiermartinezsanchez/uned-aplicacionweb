package es.alumno.uned.model.repository;

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
	/**
	 * Se busca un curso por su Título.
	 * @param Titulo Título del módulo a buscar.
	 * @return Entidad encontrada.
	 */
	Modulo findByTituloContainingIgnoreCase(String Titulo);
	/**
	 * Búsqueda de módulos por título.
	 * @param Titulo Título de los módulos a buscar.
	 * @param pageable Configuración de la página.
	 * @return Datos encontrados.
	 */
	Page<Modulo> findByTituloContainingIgnoreCase(String Titulo, Pageable pageable);
	
	/**
	 * Búsqueda de módulos por su Tipo.
	 * <p>Devuelve la lista (paginada) de los datos encontrados.
	 * @param tipoModulo Tipo de módulo {@link TipoModulo}
	 * @param pageable Configuración de la página.
	 * @return Datos encontrados.
	 */
	Page<Modulo> findByTipo(TipoModulo tipoModulo, Pageable pageable);
	
	/**
	 * Búsqueda de módulos por Título y Tipo.
	 * @param Titulo Título del módulo a buscar.
	 * @param tipoModulo Tipo de módulo {@link TipoModulo}
	 * @param pagable Configuración de la página.
	 * @return Datos encontrados.
	 */
	Page<Modulo> findByTituloContainingIgnoreCaseAndTipo(String Titulo, TipoModulo tipoModulo, Pageable pagable);
	//List<Modulo> findByCursoId(Long cursoId);
}
