package es.alumno.uned.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.alumno.uned.model.entities.UserAudit;
import es.alumno.uned.model.repository.UserAuditRepository;
import es.alumno.uned.model.util.PaginacionComun;
@Service
public class UserAuditServiceImpl implements UserAuditService {

	UserAuditRepository repo;
	
	public UserAuditServiceImpl(UserAuditRepository repo) {
		this.repo= repo;	
    }
	@Override
	public PaginacionComun<UserAudit> listadoPaginado(String url, Pageable pageable,
			LocalDate fechaIni, 
			LocalDate fechaFin) {
			LocalDateTime fechaI = (fechaIni != null)
	            ? fechaIni.atStartOfDay()
	            : LocalDateTime.of(1900, 1, 1, 0, 0, 0);

			LocalDateTime fechaF = (fechaFin != null)
	            ? fechaFin.atTime(23, 59, 59)
	            : LocalDate.now().atTime(23, 59, 59);
			
			   return new PaginacionComun<>(url, repo.findByfechaAuditBetween( fechaI, fechaF, pageable));
				}

}
