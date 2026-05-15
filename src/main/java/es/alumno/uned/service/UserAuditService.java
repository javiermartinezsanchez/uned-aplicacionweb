package es.alumno.uned.service;

import java.util.Map;

import es.alumno.uned.dto.UserAuditDTO;
import es.alumno.uned.model.entities.UserAudit;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.util.Paginacion;

public interface UserAuditService {

	public Paginacion<UserAudit, UserAuditDTO> listadoPaginado(  PageParams pageable,
			Map<String, String> params);

}
