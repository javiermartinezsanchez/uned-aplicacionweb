package es.alumno.uned.service;

import org.springframework.data.domain.Pageable;

import es.alumno.uned.model.entities.UserAudit;
import es.alumno.uned.model.util.PaginacionComun;

public interface UserAuditService {

	public PaginacionComun<UserAudit> listadoPaginado(String url, Pageable pageable);

}
