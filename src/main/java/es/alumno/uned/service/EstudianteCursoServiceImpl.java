package es.alumno.uned.service;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.alumno.uned.dto.EstudianteCursoDTO;
import es.alumno.uned.dto.EstudianteCursoModuloDTO;
import es.alumno.uned.exception.CursoModuloEstadoIncompatibleException;
import es.alumno.uned.exception.CursoNotExistException;
import es.alumno.uned.exception.CursoResponsableMandatoryException;
import es.alumno.uned.exception.EstudianteCursoAlreadySubscribeException;
import es.alumno.uned.exception.EstudianteNotExistException;
import es.alumno.uned.exception.MandatoryModuloException;
import es.alumno.uned.exception.ModuloNotFoundException;
import es.alumno.uned.exception.ResponsableNotMachtException;
import es.alumno.uned.mapper.EstudianteCursoMapper;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.CursoModulo;
import es.alumno.uned.model.entities.EstadoCursoModulo;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.EstudianteCurso;
import es.alumno.uned.model.entities.EstudianteCursoId;
import es.alumno.uned.model.entities.EstudianteCursoModulo;
import es.alumno.uned.model.entities.EstudianteCursoModuloId;
import es.alumno.uned.model.entities.TipoModulo;
import es.alumno.uned.model.records.FicheroData;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.repository.CursoRepository;
import es.alumno.uned.model.repository.EstudianteCursoModuloRepository;
import es.alumno.uned.model.repository.EstudianteCursoRepository;
import es.alumno.uned.model.repository.EstudianteRepository;
import es.alumno.uned.model.util.Paginacion;

/**
 * Implementación del servicio de Estudiante curso.
 * <p>
 */
@Service
@Transactional
public class EstudianteCursoServiceImpl implements EstudianteCursoService {

    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final EstudianteCursoModuloRepository estudianteCursoModuloRepository;
    private final FileStorageService fileStorageService;
    private final EstudianteCursoMapper estudianteCursoMapper;

    public EstudianteCursoServiceImpl(
            EstudianteRepository estudianteRepository,
            CursoRepository cursoRepository,
            EstudianteCursoRepository estudianteCursoRepository,
            EstudianteCursoModuloRepository estudianteCursoModuloRepository,
            FileStorageService fileStorageService,
            EstudianteCursoMapper estudianteCursoMapper) {

        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
        this.estudianteCursoRepository = estudianteCursoRepository;
        this.estudianteCursoModuloRepository = estudianteCursoModuloRepository;
        this.estudianteCursoMapper = estudianteCursoMapper;
        this.fileStorageService = fileStorageService;
    }

	@Override
	public EstudianteCursoDTO getCurso(Long idEstudiante, Long idCurso) {
		
		return estudianteCursoMapper.toDTO(estudianteCursoRepository.getReferenceById(new EstudianteCursoId(idEstudiante, idCurso)));
	}	
	
	@Override
	public EstudianteCursoDTO getCursoModulo(Long idResponsable, Long idEstudiante, Long idCurso, Long idModulo) {
		var ec = estudianteCursoRepository.getReferenceById(new EstudianteCursoId(idEstudiante, idCurso));
		if (ec == null) 
			throw new CursoNotExistException("msg.exception.notfound.", null, "curso.title");
		
		if (!ec.getCurso().getResponsable().getId().equals(idResponsable)){
			throw new ResponsableNotMachtException("error.curso.responsablenotmatch");
		}
		var dto = estudianteCursoMapper.toDTOPendientes(ec);
		if (dto.getModulos().size() == 0) {
			throw new CursoModuloEstadoIncompatibleException("modulo.not.found", null, "");
		}
		
		return dto;
	}
    @Transactional
    @Override
    public EstudianteCursoDTO subscribirAlumnoACurso(Long estudianteId, Long cursoId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new EstudianteNotExistException("msg.exception.notfound", null, "estudiante.titulo"));

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new CursoNotExistException("msg.exception.notfound.", null, "curso.title"));



        if (estudianteCursoRepository.existsByIdEstudianteIdAndIdCursoId(estudiante.getId(), cursoId)) {
            throw new EstudianteCursoAlreadySubscribeException("error.curso.yainscrito");
        }

        EstudianteCurso ec = new EstudianteCurso(estudiante, curso);
        estudianteCursoRepository.saveAndFlush(ec);

        List<CursoModulo> modulos = curso.getModulos();
        boolean first = true;
        for (CursoModulo cursoModulo : modulos) {
            EstudianteCursoModulo ecm = new EstudianteCursoModulo(ec, cursoModulo.getModulo());
            ecm.setOrden(cursoModulo.getOrden());
            ecm.setTitulo(cursoModulo.getModulo().getTitulo());
            ecm.setDescripcion(cursoModulo.getModulo().getDescripcion());
            ecm.setContenido(cursoModulo.getModulo().getContenido());
            ecm.setPeso(cursoModulo.getPeso());
            ecm.setTipo(cursoModulo.getModulo().getTipo());
            ecm.setEstado(first?EstadoCursoModulo.ACTIVO:EstadoCursoModulo.BLOQUEADO);
            first = false;
            ec.addModuloCurso(ecm);
        }
        estudianteCursoModuloRepository.saveAll(ec.getModulos());
        curso.addUserRegistred();
        cursoRepository.save(curso);
        return estudianteCursoMapper.toDTO(estudianteCursoRepository.save(ec));

    }

	@Override
	public Paginacion<EstudianteCurso, EstudianteCursoDTO> listadoTareasPendientes(PageParams pageData,
			Map<String, String> params) {
		if (!params.containsKey("responsableId")) {
			throw new CursoResponsableMandatoryException("validations.curso.responsable.mandatory");
		}
		var idResponsable = Long.valueOf(params.get("responsableId"));
		// ,Sort.by("m.fechaEntrega").descending() La ordenación la realizamos en la query.
		return construirPaginacion(estudianteCursoRepository.findByEstadoAndCursoResponsableId(
				EstadoCursoModulo.PENDIENTE_REVISION,
				idResponsable,
				PageRequest.of(pageData.page(), pageData.size())));
	}

	private Paginacion<EstudianteCurso, EstudianteCursoDTO> construirPaginacion( Page<EstudianteCurso> page){
		
		return new Paginacion.Builder<EstudianteCurso, EstudianteCursoDTO>()
				.pagina(page)
				.mapper(estudianteCursoMapper :: toDTOPendientes)
				.build();
	}
    @Override
    public void actualizarUltimoAcceso(String username, Long cursoId) {
        // TODO: implementar actualización de último acceso
    }

    @Override
    public void recalcularProgreso(EstudianteCursoModulo ecm) {
        if (ecm.getEstado() == EstadoCursoModulo.COMPLETADO) {
        	ecm.getEstudianteCurso().addProgreso(ecm.getPeso());
        }
    }
	@Override
	public void completarModulo(Long estudianteId, Long cursoId, Long moduloId, TipoModulo tipoModulo, FicheroData ficheroEntrega) throws IOException {
		
		EstudianteCursoModulo ecm = estudianteCursoModuloRepository.getReferenceById(
				new EstudianteCursoModuloId(new EstudianteCursoId(estudianteId, cursoId), moduloId));

		if (ecm == null) {
			throw new ModuloNotFoundException("modulo.not.found", null, "");
		}
		
		if ((ecm.getEstado() != EstadoCursoModulo.ACTIVO)  && 
		   (ecm.getEstado() != EstadoCursoModulo.REVISADO)){
			throw new CursoModuloEstadoIncompatibleException("modulo.not.found", null, "");
		}
        if ( ecm.getTipo() == TipoModulo.ENTREGA_OBLIGATORIA ) {
        	if (ecm.getEstado() == EstadoCursoModulo.ACTIVO) {
	            if (ficheroEntrega == null ) {
	                throw new MandatoryModuloException("cualquier cosa", null, "entregable" );
	                }
	    		ecm.setUrlEntrega(fileStorageService.saveDocumento(ficheroEntrega));
	    		ecm.setFechaEntrega(LocalDateTime.now());
	    		ecm.setEstado(EstadoCursoModulo.PENDIENTE_REVISION);
        	}
        	if (ecm.getEstado() == EstadoCursoModulo.REVISADO) {
        		marcarModuloComoCompletado( ecm);
        		
        	}
        }
        else {
    		marcarModuloComoCompletado( ecm);
        }
        recalcularProgreso(ecm);
        revisarEstado(ecm);
	}

	/**
	 * De acuerdo al estado del EstudianteCursoModulo recibido se busca el
	 * siguiente módulo.
	 * <ol>
	 * <li>Existe. Se asigna al módulo siguiente el estado a ABIERTO.
	 * <li>No existe. Se marca al EstudianteCurso como completado.
	 * @param ecm
	 */
	private void revisarEstado(EstudianteCursoModulo ecm) {
		if (ecm.getEstado() == EstadoCursoModulo.COMPLETADO) {
			var siguienteModulo = ecm.getEstudianteCurso().getModulos().stream()
				    .sorted(Comparator.comparingInt(EstudianteCursoModulo::getOrden)) // Cambia Modulo::getOrden por tu getter real
				    .filter(m -> m.getOrden() > ecm.getOrden())
				    .findFirst()
				    .orElse(null); // Retorna null si no hay un módulo siguiente
			if (siguienteModulo == null) {
				//CURSO COMPLETADO
				ecm.getEstudianteCurso().setFechaCompletado(LocalDateTime.now());
				ecm.getEstudianteCurso().setEstado(EstadoCursoModulo.COMPLETADO);
			}
			else {
				if (siguienteModulo.getEstado() == EstadoCursoModulo.BLOQUEADO) {
					siguienteModulo.setEstado(EstadoCursoModulo.ACTIVO);
				}
			}
		}
		else {
			ecm.getEstudianteCurso().setEstado(ecm.getEstado());
		}
		ecm.getEstudianteCurso().setFechaUltimoAcceso(LocalDateTime.now());
	}

	/**
	 * Se define el estado y la fecha de COMPLETADO
	 * @param ecm El EstudianteCursoModulo completado.
	 */
	private void marcarModuloComoCompletado(EstudianteCursoModulo ecm) {
		ecm.setFechaCompletado(LocalDateTime.now());
		ecm.setEstado(EstadoCursoModulo.COMPLETADO);
		
	}
	@Transactional(readOnly = true)
	@Override
	public Paginacion<EstudianteCurso, EstudianteCursoDTO> listadoPaginado(PageParams pageData,
			Map<String, String> params) {
	return new Paginacion.Builder<EstudianteCurso, EstudianteCursoDTO>()
			.pagina(getPaginaBusqueda(PageRequest.of(pageData.page(), pageData.size()),params))
			.mapper(estudianteCursoMapper :: toDTOList)
			.build();
	}

	private Page<EstudianteCurso> getPaginaBusqueda(Pageable pageable, Map<String, String> params){
		if (params.containsKey("estudianteId")){
			return estudianteCursoRepository.findByIdEstudianteId(Long.valueOf(params.get("estudianteId")),pageable);
		}
		return null;
	}

	@Override
	public Long getTareasPendientes(Long idResponsable) {
		
		return estudianteCursoRepository.getNumTareasPendientes(EstadoCursoModulo.PENDIENTE_REVISION, idResponsable);
	}
	@Transactional
	@Override
	public void calificarModulo(EstudianteCursoModuloDTO ec) {
		EstudianteCursoModulo ecm = estudianteCursoModuloRepository.getReferenceById(
				new EstudianteCursoModuloId(new EstudianteCursoId(
						ec.getEstudianteId(), 
						ec.getCursoId()), 
						ec.getModuloId()));

		if (ecm == null) {
			throw new ModuloNotFoundException("modulo.not.found", null, "");
		}
		
		if (ecm.getEstado() != EstadoCursoModulo.PENDIENTE_REVISION){
			throw new CursoModuloEstadoIncompatibleException("modulo.not.found", null, "");
		}
		ecm.setCalificacion(ec.getCalificacion());
		ecm.setNotasCalificacion(ec.getNotasCalificacion());
		ecm.setFechaRevision(LocalDateTime.now());
		ecm.setEstado(EstadoCursoModulo.REVISADO);
		ecm.getEstudianteCurso().setEstado(EstadoCursoModulo.REVISADO);
		estudianteCursoModuloRepository.save(ecm);
		
	}




	


	
}
