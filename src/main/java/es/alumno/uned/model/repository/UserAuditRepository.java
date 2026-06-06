package es.alumno.uned.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.alumno.uned.dto.AccesoPorDia;
import es.alumno.uned.model.entities.UserAudit;
/**
 * Repositorio de la entidad UserAudit (Auditoría de accesos).
 * 
 */
public interface UserAuditRepository extends JpaRepository<UserAudit, Long>{

	/**
	 * Búsqueda entre fechas de acceso.
	 * @param fechaIni Fecha inicial de la búsqueda.
	 * @param fechaFin Fecha final de la búsqueda.
	 * @param page
	 * @return
	 */
	Page<UserAudit> findByfechaAuditBetween( LocalDateTime fechaIni, LocalDateTime fechaFin, Pageable page);

	@Query("SELECT new es.alumno.uned.dto.AccesoPorDia(CAST(a.fechaAudit AS LocalDate), COUNT(a)) " +
	           "FROM UserAudit a " +
	           "WHERE a.fechaAudit BETWEEN :fechaIni AND :fechaFin " +
	           "GROUP BY CAST(a.fechaAudit AS LocalDate) " +
	           "ORDER BY CAST(a.fechaAudit AS LocalDate) ASC")    
	List<AccesoPorDia> contarAccesosPorDia(
			@Param("fechaIni") LocalDateTime fechaIni, 
            @Param("fechaFin") LocalDateTime fechaFin
            );
}
