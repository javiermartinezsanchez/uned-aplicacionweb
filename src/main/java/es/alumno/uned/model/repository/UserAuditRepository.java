package es.alumno.uned.model.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
