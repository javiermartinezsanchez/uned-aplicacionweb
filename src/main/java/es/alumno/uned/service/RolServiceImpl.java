package es.alumno.uned.service;

import es.alumno.uned.mapper.RolMapper;
import java.util.List;

import org.springframework.stereotype.Service;

import es.alumno.uned.dto.RolDTO;
import es.alumno.uned.model.entities.Rol;
import es.alumno.uned.model.repository.RolRepository;

@Service
public class RolServiceImpl implements RolService{

	
	RolRepository repo;
	private final RolMapper rolMapper;
	public RolServiceImpl(RolRepository repo, RolMapper rolMapper) {
		this.repo = repo;
		this.rolMapper = rolMapper;
	}
	@Override
	public List<RolDTO> getList() {
		
		return repo.findAll().stream()
				.map(rolMapper::toDTO)
				.toList();
	}

}
