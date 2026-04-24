package es.alumno.uned.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.alumno.uned.dto.UserAuditDTO;
import es.alumno.uned.mapper.CursoMapper;
import es.alumno.uned.mapper.UserAuditMapper;
import es.alumno.uned.model.entities.UserAudit;
import es.alumno.uned.model.repository.UserAuditRepository;
import es.alumno.uned.model.util.Paginacion;
import es.alumno.uned.model.util.PaginacionComun;
@Service
public class UserAuditServiceImpl implements UserAuditService {

	UserAuditRepository repo;
	
	@Autowired
	UserAuditMapper userMapper;
	
	public UserAuditServiceImpl(UserAuditRepository repo) {
		this.repo= repo;	
    }
	@Override
	public Paginacion<UserAudit, UserAuditDTO> listadoPaginado(String url, Pageable pageable,
			LocalDate fechaIni, 
			LocalDate fechaFin) {
			LocalDateTime fechaI = (fechaIni != null)
	            ? fechaIni.atStartOfDay()
	            : LocalDateTime.of(1900, 1, 1, 0, 0, 0);

			LocalDateTime fechaF = (fechaFin != null)
	            ? fechaFin.atTime(23, 59, 59)
	            : LocalDate.now().atTime(23, 59, 59);
	
			   return new Paginacion.Builder<UserAudit, UserAuditDTO>()
					   .url(url) 
					   .pagina(repo.findByfechaAuditBetween( fechaI, fechaF, pageable))
					   .mapper(userMapper::toDTO)
		                .build();
				}

}
