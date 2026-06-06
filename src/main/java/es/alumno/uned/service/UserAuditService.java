package es.alumno.uned.service;

import java.util.List;
import java.util.Map;

import es.alumno.uned.dto.UserAuditDTO;
import es.alumno.uned.dto.AccesoPorDia;
import es.alumno.uned.model.entities.UserAudit;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.util.Paginacion;

public interface UserAuditService {

	public Paginacion<UserAudit, UserAuditDTO> listadoPaginado(  PageParams pageable,
			Map<String, String> params);

	/**
	 * Se obtiene el listado de días y número de acceso entre fechas.
	 * @return
	 */
	public List<AccesoPorDia> contarAccesosPorDia(Map<String, String> params);

}
