package es.alumno.uned.service;

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
	public PaginacionComun<UserAudit> listadoPaginado(String url, Pageable pageable) {
			return new PaginacionComun<>(url,repo.findAll(pageable));
	}

}
