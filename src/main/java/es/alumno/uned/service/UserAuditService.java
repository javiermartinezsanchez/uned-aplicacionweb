package es.alumno.uned.service;

import java.time.LocalDate;

import org.springframework.data.domain.Pageable;

import es.alumno.uned.dto.UserAuditDTO;
import es.alumno.uned.model.entities.UserAudit;
import es.alumno.uned.model.util.Paginacion;

public interface UserAuditService {

	public Paginacion<UserAudit, UserAuditDTO> listadoPaginado(  Pageable pageable,
			LocalDate fechaIni,
			LocalDate fechaFin);

}
