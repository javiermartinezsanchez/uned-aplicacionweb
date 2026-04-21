package es.alumno.uned.service;

import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.exception.AreaTematicaAlreadyExistException;
import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.repository.AreaTematicaRepository;
import es.alumno.uned.model.util.PaginacionComun;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AreaTematicaServiceImpl implements AreaTematicaService {

	@Autowired
	AreaTematicaRepository repo;
	@Override
	public PaginacionComun<AreaTematica> listadoPaginado(String url, Pageable pageRequest) {
		
		return new PaginacionComun<>(url,repo.findAll(pageRequest));
	}

	@Override
	public @Nullable AreaTematicaDTO getAreaTematica(Long id) {
		AreaTematica at = repo.getReferenceById(id);
		return new AreaTematicaDTO(at.getId(), at.getTitulo(), at.getDescripcion());
	}

	@Override
	public AreaTematicaDTO nuevaArea(AreaTematicaDTO area) {
		if (isExistByTitulo(area.getTitulo()) ) {
            throw new AreaTematicaAlreadyExistException("{areaTematica.error.existente}" + area.getTitulo());

		}
		AreaTematica areaTematica = new AreaTematica();
		areaTematica.setTitulo(area.getTitulo());
		areaTematica.setDescripcion(area.getDescripcion());
		areaTematica =  repo.save(areaTematica);
		return new AreaTematicaDTO(areaTematica.getId(), areaTematica.getTitulo(), areaTematica.getDescripcion()) ;
	}

	@Override
	public AreaTematicaDTO grabar(AreaTematicaDTO area) {
		Optional<AreaTematica> areaTematica = repo.findById(area.getId());
		if ((areaTematica.isPresent()) && (isExistByTitulo(area.getTitulo()))) {
			AreaTematica atsave = areaTematica.get();
			atsave.setDescripcion(area.getDescripcion());
			atsave.setTitulo(area.getTitulo());
			atsave = repo.save(atsave);
			return new AreaTematicaDTO(atsave.getId(), atsave.getTitulo(), atsave.getDescripcion()) ;
		}
		return area;
	}
    
	/**
	 * Busca si existe una Area por su titulo
	 * @param titulo Título  abuscar
	 * @return true o false Si existe
	 */
	private boolean isExistByTitulo(String titulo) {
		return repo.findByTitulo(titulo) != null;
	}
	
	
	

}
