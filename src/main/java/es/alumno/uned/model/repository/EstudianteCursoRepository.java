package es.alumno.uned.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.alumno.uned.model.entities.EstadoCursoModulo;
import es.alumno.uned.model.entities.EstudianteCurso;
import es.alumno.uned.model.entities.EstudianteCursoId;

public interface EstudianteCursoRepository extends JpaRepository<EstudianteCurso, EstudianteCursoId>, JpaSpecificationExecutor<EstudianteCurso> {
	boolean existsByIdEstudianteIdAndIdCursoId(Long estudianteId, Long cursoId);

    Optional<EstudianteCurso> findByEstudianteIdAndIdCursoId(Long estudianteId, Long cursoId);

	Page<EstudianteCurso> findByIdEstudianteId(Long estudianteId, Pageable pageable);

	/**
	 * Listado de EstudianteCurso para el estado de PENDIENTE_REVISION por Profesor (idResponsable)
	 * @param estado Estado que buscamos 
	 * @param idResponsable Id del responsable del curso (Profesor)
	 * @param pageable Datos de paginación
	 * @return Página de datos encontrados.
	 */
	@Query("SELECT ec FROM EstudianteCurso ec " +
		       "LEFT JOIN ec.modulos m " +
		       "WHERE ec.estado = :estado " + 
		       "AND m.estado = :estado AND ec.curso.responsable.id = :idResponsable " +
		       "ORDER BY m.fechaEntrega DESC") 
	Page<EstudianteCurso> findByEstadoAndCursoResponsableId(
             @Param("estado") EstadoCursoModulo estado, 
             @Param("idResponsable") Long idResponsable, 
             Pageable pageable
     );
    /** 
     * Devolvemos el número total de tareas pendientes de revisión para un profesor.
     * @param estado Estado a consultar (inicialmente PENDIENTE_REVISION)
     * @param idResponsable Identificador del Profesor.
     * @return Número total de registros encontrados.
     */
    @Query("SELECT COUNT(DISTINCT ec.id) FROM EstudianteCurso ec " +
    	       "JOIN ec.modulos m " +
    	       "WHERE ec.estado = :estado " +
    	       "AND m.estado = :estado " + // Filtro de seguridad defensiva
    	       "AND ec.curso.responsable.id = :idResponsable")
    Long getNumTareasPendientes(@Param("estado") EstadoCursoModulo estado, 
             @Param("idResponsable") Long idResponsable);
    /**
     * Listado de EstudianteCursos para un Curso y un Profesor
     * @param cursoId
     * @param responsableId
     * @param pageable
     * @return Datos encontrados
     */
	Page<EstudianteCurso> findByCursoIdAndCursoResponsableId(Long cursoId, Long responsableId, Pageable pageable);
}
