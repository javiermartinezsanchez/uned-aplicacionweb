package es.alumno.uned.service;

import java.util.List;

import org.springframework.stereotype.Service;

import es.alumno.uned.model.entities.Rol;
import es.alumno.uned.model.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService{

	RolRepository repo;
	
	public RolServiceImpl(RolRepository repo) {
		this.repo = repo;
	}
	@Override
	public List<Rol> getList() {
		
		return repo.findAll();
	}

}
