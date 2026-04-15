package es.alumno.uned.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.UserAudit;

public interface UserAuditRepository extends JpaRepository<UserAudit, Long>{

}
