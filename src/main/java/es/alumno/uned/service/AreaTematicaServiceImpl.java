package es.alumno.uned.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.alumno.uned.dto.AreaTematicaDTO;
import es.alumno.uned.exception.AreaTematicaAlreadyExistException;
import es.alumno.uned.mapper.AreaTematicaMapper;
import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.repository.AreaTematicaRepository;
import es.alumno.uned.model.util.Paginacion;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class AreaTematicaServiceImpl implements AreaTematicaService {

	@Autowired
	AreaTematicaRepository repo;
	
	@Autowired
	AreaTematicaMapper mapper;
	@Override
	public Paginacion<AreaTematica, AreaTematicaDTO> listadoPaginado(String url, Pageable pageRequest) {
		
		return new Paginacion.Builder<AreaTematica, AreaTematicaDTO>()
				.url(url)
                .pagina(repo.findAll(pageRequest))
                .mapper(mapper::toDTO)
                .build();
	}

	@Override
	public @Nullable AreaTematicaDTO getAreaTematica(Long id) {
		AreaTematica areatematica = repo.getReferenceById(id);
		return mapper.toDTO(areatematica);
	}

	@Override
	public AreaTematicaDTO grabar(AreaTematicaDTO area) {
		AreaTematica areaTematica;
		if (area.getId() == null) {
			if (isExistByTitulo(area.getTitulo())) {
			   throw new AreaTematicaAlreadyExistException("areaTematica.error.existente",
					   area, area.getTitulo());
		    }
			areaTematica = new AreaTematica();
		}
		else {
			areaTematica = repo.findById(area.getId()).get();	
		}
		areaTematica.setDescripcion(area.getDescripcion());
		areaTematica.setTitulo(area.getTitulo());
		areaTematica = repo.save(areaTematica);
		return mapper.toDTO(areaTematica) ;
	}
		
    
	/**
	 * Busca si existe una Area por su titulo
	 * @param titulo Título  abuscar
	 * @return true o false Si existe
	 */
	private boolean isExistByTitulo(String titulo) {
		return repo.findByTitulo(titulo) != null;
	}

	@Override
	public List<AreaTematicaDTO> listAll() {
		return repo.findAll().stream()
		.map(mapper :: toDTO)
		.collect(Collectors.toList());
	}
	
}
