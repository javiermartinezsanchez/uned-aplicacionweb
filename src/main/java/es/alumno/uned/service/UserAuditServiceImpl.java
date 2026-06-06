package es.alumno.uned.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import es.alumno.uned.dto.AccesoPorDia;
import es.alumno.uned.dto.UserAuditDTO;
import es.alumno.uned.mapper.UserAuditMapper;
import es.alumno.uned.model.entities.UserAudit;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.repository.UserAuditRepository;
import es.alumno.uned.model.util.Paginacion;
@Service
public class UserAuditServiceImpl implements UserAuditService {

	UserAuditRepository repo;
	
	@Autowired
	UserAuditMapper userMapper;
	
	public UserAuditServiceImpl(UserAuditRepository repo) {
		this.repo= repo;	
    }
	@Override
	public Paginacion<UserAudit, UserAuditDTO> listadoPaginado( PageParams pageData,
			Map<String, String> params) {
		
		LocalDateTime fechaI = params.containsKey("fechaIni")
	            ? LocalDate.parse(params.get("fechaIni")).atStartOfDay()
	            : LocalDateTime.of(1900, 1, 1, 0, 0, 0);

		LocalDateTime fechaF = params.containsKey("fechaFin")
	            ? LocalDate.parse(params.get("fechaFin")).atTime(23, 59, 59)
	            : LocalDate.now().atTime(23, 59, 59);

			   return new Paginacion.Builder<UserAudit, UserAuditDTO>()
					   .pagina(repo.findByfechaAuditBetween( fechaI, fechaF,PageRequest.of(pageData.page(), pageData.size())))
					   .mapper(userMapper::toDTO)
		                .build();
				}
	@Override
	public List<AccesoPorDia> contarAccesosPorDia(Map<String, String> params) {
		
		LocalDateTime fechaIni = params.containsKey("fechaIni")
		        ? LocalDate.parse(params.get("fechaIni")).atStartOfDay()
		        : LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay();

		LocalDateTime fechaFin = (params.get("fechaFin") != null && !params.get("fechaFin").isBlank())
		        ? LocalDate.parse(params.get("fechaFin")).atTime(23, 59, 59)
		        : LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()).atTime(23, 59, 59);

		
		return repo.contarAccesosPorDia(fechaIni, fechaFin);
	}

}
