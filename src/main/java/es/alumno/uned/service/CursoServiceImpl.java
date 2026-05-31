package es.alumno.uned.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.exception.MandatoryFileException;
import es.alumno.uned.mapper.CursoMapper;
import es.alumno.uned.model.entities.ContenidoExtra;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.CursoValoracion;
import es.alumno.uned.model.entities.TipoContenido;
import es.alumno.uned.model.records.FicheroData;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.repository.AreaTematicaRepository;
import es.alumno.uned.model.repository.CursoRepository;
import es.alumno.uned.model.repository.CursoValoracionRepository;
import es.alumno.uned.model.repository.UsuarioRepository;
import es.alumno.uned.model.util.Paginacion;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
/**
 * Clase implementadora de {@link CursoService}
 * 
 * 
 */
@Transactional(readOnly = true)
@Service
public class  CursoServiceImpl implements CursoService{
	
	@Autowired
	CursoRepository cursoRepository;
	
	@Autowired
	AreaTematicaRepository areaTematicaRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	CursoValoracionRepository cursoValoracionRepository;
	
	@Autowired
	CursoMapper cursoMapper;
	
	@Autowired
	FileStorageService fileStorageService;
	
	@Override
	public CursoDTO getCurso(Long id) {
		return cursoMapper.toDTO(getValidCurso(id));
	}
	@Transactional
	@Override
	public CursoDTO getCurso(Long idCurso, String marcarVisita) {
		Curso curso = getValidCurso(idCurso);
		if (curso.getId() != null) {
			curso.addVista();
			cursoRepository.save(curso);
		}
		return cursoMapper.toDTO(curso);
	}

	/**
	 * Obtenemos un curso válido.
	 * <
     *
	 * <ul>
	 *     <li>Si el id es null -> Nuevo Curso</li>
	 *     <li>
	 *         Si el id no es nulo:
	 *         <ul>
	 *             <li>Si se encuentra -> Curso de la BD</li>
	 *             <li>Si no se encuentra -> Nuevo Curso</li>
	 *         </ul>
	 *     </li>
	 * </ul>
	 * @param id Id del curso a buscar
	 * @return {@code Curso}
	 */
	private Curso getValidCurso(Long id) {
		Curso curso = (id != null)
				? cursoRepository.findById(id).orElse(new Curso())
				: new Curso();
		return curso;
	}
	@Transactional
	@Override
	public CursoDTO grabar(CursoDTO dto, 
			FicheroData imagen, 
			List<FicheroData> contenidoExtraFiles,
			String usuario) throws IOException {
		var curso = getValidCurso(dto.getId());
	    cursoMapper.toEntity(dto, curso);
	    if (curso.getfIns() == null) {
	    	curso.setfIns(LocalDateTime.now());
	    	curso.setUserIns(usuario);
	    }
	    // Subida de imagen
	    if (imagen != null ) {
//	    	if ((curso.getUriImagen() != null) && ())
	        curso.setUriImagen(fileStorageService.saveImagen(imagen));
	    }

	    for (int i = 0; i < curso.getContenidosExtra().size(); i++) {
	        ContenidoExtra contenido = curso.getContenidosExtra().get(i);
	        int indiceActual = i;
	        Optional<FicheroData> ficheroCorrespondiente = contenidoExtraFiles.stream()
	                .filter(f -> f.indice() == indiceActual)
	                .findFirst();
	        
	        // Buscamos si hay un archivo asociado a este índice en el mapa
	       // String key = "archivoExtra_" + ;
	       // MultipartFile archivoFisico = contenidoExtraFiles.get(key);
	        
	        if (contenido.getTipoContenido() == TipoContenido.PROPIO) {
	            if (ficheroCorrespondiente.isPresent()) {
	            	contenido.setUri( fileStorageService.saveDocumento(ficheroCorrespondiente.get()));
	            	contenido.setNombreReal(ficheroCorrespondiente.get().nombreOriginal());
	            	contenido.setContentType(ficheroCorrespondiente.get().contentType());
	            } else if (contenido.getUri() == null || contenido.getUri().isEmpty()) {
	                // Error: Es contenido propio pero no hay archivo ni URI previa
	                throw new MandatoryFileException("curso.contenidoextra.filenotexist", dto, "");
	            }
	        }
	        // Si es EXTERNO, la URI ya viene en el DTO/Entidad gracias al binding de Spring
	        
	        contenido.setCurso(curso); // Mantenemos la bidireccionalidad
	    }	    
	    return cursoMapper.toDTO(cursoRepository.save(curso));
		
	}

	@Override
	public Paginacion<Curso, CursoDTO> listadoPaginado(  Pageable pageable){
		
		return construirPaginacion( cursoRepository.findAll(pageable));
	}

	@Override
	public Paginacion<Curso, CursoDTO> listadoPaginado( PageParams pageData, Map<String, String> params) {
		Specification<Curso> condiciones = generaCondiciones(params);
		return construirPaginacion(cursoRepository.findAll(condiciones, PageRequest.of(pageData.page(), pageData.size())));
	}
	
	@Override
	public Paginacion<Curso, CursoDTO> listadoOrderByNumVisistas(PageParams pageData, Map<String, String> params) {
		Specification<Curso> condiciones = generaCondiciones(params);
		return construirPaginacion(cursoRepository.findAll(condiciones, PageRequest.of(pageData.page(), pageData.size(), Sort.by("numVistas").descending())));
	}
	@Override
	public Paginacion<Curso, CursoDTO> listadoOrderByValoracion(PageParams pageData, Map<String, String> params) {
		Specification<Curso> condiciones = generaCondiciones(params);
		return construirPaginacion(cursoRepository.findAll(condiciones, PageRequest.of(pageData.page(), pageData.size(), Sort.by("valoracion").descending())));
	}

	@Override
	public Paginacion<Curso, CursoDTO> listadoOrderByInscritos(PageParams pageData, Map<String, String> params) {
		Specification<Curso> condiciones = generaCondiciones(params);
		return construirPaginacion(cursoRepository.findAll(condiciones, PageRequest.of(pageData.page(), pageData.size(), Sort.by("usuariosRegistrados").descending())));
	}

	@Override
	public Paginacion<Curso, CursoDTO> listadoCursosDisponiblesPorAreas(PageParams pageData, Long idEstudiante,
			List<Long> areasId) {
		if (!areasId.isEmpty()) {
			return construirPaginacion(cursoRepository.findCursosDisponiblesPorAreas(areasId, idEstudiante, PageRequest.of(pageData.page(), pageData.size(), Sort.by("c.areaTematica.titulo").descending())));
			
		}
		return construirPaginacion(cursoRepository.findCursosDisponiblesEstudiante(idEstudiante, PageRequest.of(pageData.page(), pageData.size(),Sort.by("numVistas").descending())));
	}

	/**
	 * Nos devuelve la lista de condiciones {@link Specification} de la consulta de acuerdo a los parámetros enviados.
	 * <p> Si el mapa de parámetros está vacio devuelve todos.
	 * @param filtros Mapa de parámetros para definir la búsqueda de Módulos.
	 * @return Los predicados de búsqueda generados de acuerdo a los parámetros enviados.
	 */
	private static Specification<Curso> generaCondiciones(Map<String, String> filtros) {
	    return (root, query, cb) -> {
	    	/**
	    	 * Recomendación para evitar consultas innecesarias.
	    	 */
	    	if (query.getResultType() != Long.class && query.getResultType() != long.class) {
	            root.fetch("areaTematica", JoinType.LEFT);
	            root.fetch("responsable", JoinType.LEFT);
	        }
	        List<Predicate> predicates = new ArrayList<>();

	        if (filtros.containsKey("titulo")) {
	            predicates.add(cb.like(cb.lower(root.get("titulo")), 
	                           "%" + filtros.get("titulo").toLowerCase() + "%"));
	        }

	        if (filtros.containsKey("nivel")) {
	            predicates.add(cb.equal(root.get("nivel"), filtros.get("nivel")));
	        }

	        if (filtros.containsKey("areaId")) {
	            predicates.add(cb.equal(root.get("areaTematica").get("id"), 
	                           Long.parseLong(filtros.get("areaId"))));
	        }

	        if (filtros.containsKey("responsableId")) {
	            predicates.add(cb.equal(root.get("responsable").get("id"), 
	                           Long.parseLong(filtros.get("responsableId"))));
	        }

	        if (filtros.containsKey("fIni")) {
	            predicates.add(cb.greaterThanOrEqualTo(root.get("fIni"), 
	                           LocalDate.parse(filtros.get("fIni"))));
	        }

	        if (filtros.containsKey("fFin")) {
	            predicates.add(cb.lessThanOrEqualTo(root.get("fFin"), 
	                           LocalDate.parse(filtros.get("fFin"))));
	        }

	        return cb.and(predicates.toArray(new Predicate[0]));
	    };
	}
    
    
	private Paginacion<Curso, CursoDTO> construirPaginacion( Page<Curso> page) {
	        return new Paginacion.Builder<Curso, CursoDTO>()
	                .pagina(page)
	                .mapper(cursoMapper::toDTOList)
	                .build();
	    }
	
	@Transactional
	@Override
	public BigDecimal guardarValoracion(Long cursoId, Integer valoracion, String usuario) {
	    Curso curso = cursoRepository.findById(cursoId).orElse(null);
	    
	    BigDecimal media = BigDecimal.ZERO;
	    boolean addValoracion = true;
	    
	    if (curso != null) {
	        media = curso.getValoracion();
	        if (usuario != null) {
	            addValoracion = (cursoValoracionRepository.findByCursoIdAndUsuario(cursoId, usuario) == null); 
	        }
	        
	        if (addValoracion) {
	            // 1. Instanciamos la nueva valoración
	            CursoValoracion nuevaValoracion = new CursoValoracion(curso, usuario, valoracion);
	            cursoValoracionRepository.save(nuevaValoracion);
	            
	            // 2. ¡CLAVE!: Sincronizamos la lista en memoria para que el Stream la tenga en cuenta
	            curso.getValoraciones().add(nuevaValoracion);

	            // 3. Calculamos la media real con el nuevo elemento incluido
	            BigDecimal suma = curso.getValoraciones().stream()
	                    .map(v -> BigDecimal.valueOf(v.getValoracion()))
	                    .reduce(BigDecimal.ZERO, BigDecimal::add);

	            int total = curso.getValoraciones().size();

	            try {
	                media = suma.divide(
	                    BigDecimal.valueOf(total),
	                    1, // un decimal
	                    RoundingMode.HALF_UP
	                );
	                
	                // 4. Guardamos la nueva media fija en el curso para que el carrusel la lea rápido
	                curso.setValoracion(media);
	                cursoRepository.save(curso);

	            } catch (ArithmeticException e) {
	                media = BigDecimal.ZERO;
	            }
	        }
	    }
	    return media;
	}


	
}
