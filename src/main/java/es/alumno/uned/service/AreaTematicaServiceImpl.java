package es.alumno.uned.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	public Paginacion<AreaTematica, AreaTematicaDTO> listadoPaginado(String url, String titulo, String descripcion, Pageable pageRequest) {
		
		return new Paginacion.Builder<AreaTematica, AreaTematicaDTO>()
				.url(url)
                .pagina(getPaginaBusqueda(pageRequest, titulo, descripcion))
                .mapper(mapper::toDTO)
                .build();
	}
    /**
     * Devolvemos una búsqueda de acuerdo a los datos enviados por el filtro.
     * 
     * Dependendiendo de la existencia o no de los mismos llamamos a diferenentens métodos de nuestro repositorio.
     * 
     * 
     * @param pageable Definición de la {@code Page} a devolver.
     * @param titulo Texto para buscar en título
     * @param descripcion Texto para buscar en Descripción
     * @return Página de datos obtenida con los datos solicitados.
     */
	private Page<AreaTematica> getPaginaBusqueda(Pageable pageable, String titulo, String descripcion){
		if ((titulo !=null) && (descripcion != null)) {
			return repo.findByTituloContainingIgnoreCaseAndDescripcionContainingIgnoreCase(titulo, descripcion, pageable);
		}
		if (titulo != null){
			return repo.findByTituloContainingIgnoreCase(titulo, pageable);
		}
		if (descripcion != null){
			return repo.findByDescripcionContainingIgnoreCase(descripcion, pageable);
		}
		return repo.findAll(pageable);
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
