package es.alumno.uned.model.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.UserAudit;

public interface UserAuditRepository extends JpaRepository<UserAudit, Long>{

	Page<UserAudit> findByfechaAuditBetween( LocalDateTime fechaIni, LocalDateTime fechaFin, Pageable page);
}
