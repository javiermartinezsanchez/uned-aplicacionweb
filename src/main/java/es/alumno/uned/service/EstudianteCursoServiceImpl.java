package es.alumno.uned.service;


import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.dto.EstudianteCursoDTO;
import es.alumno.uned.exception.CursoNotExistException;
import es.alumno.uned.exception.EstudianteNotExistException;
import es.alumno.uned.exception.MandatoryModuloException;
import es.alumno.uned.mapper.EstudianteCursoMapper;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.CursoModulo;
import es.alumno.uned.model.entities.EstadoCursoModulo;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.EstudianteCurso;
import es.alumno.uned.model.entities.EstudianteCursoModulo;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.entities.TipoModulo;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.repository.CursoRepository;
import es.alumno.uned.model.repository.EstudianteCursoModuloRepository;
import es.alumno.uned.model.repository.EstudianteCursoRepository;
import es.alumno.uned.model.repository.EstudianteRepository;
import es.alumno.uned.model.util.Paginacion;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstudianteCursoServiceImpl implements EstudianteCursoService {

    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final EstudianteCursoModuloRepository estudianteCursoModuloRepository;
    
    private final EstudianteCursoMapper estudianteCursoMapper;

    public EstudianteCursoServiceImpl(
            EstudianteRepository estudianteRepository,
            CursoRepository cursoRepository,
            EstudianteCursoRepository estudianteCursoRepository,
            EstudianteCursoModuloRepository estudianteCursoModuloRepository,
            EstudianteCursoMapper estudianteCursoMapper) {

        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
        this.estudianteCursoRepository = estudianteCursoRepository;
        this.estudianteCursoModuloRepository = estudianteCursoModuloRepository;
        this.estudianteCursoMapper = estudianteCursoMapper;
    }

    @Transactional
    @Override
    public EstudianteCursoDTO subscribirAlumnoACurso(Long estudianteId, Long cursoId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new EstudianteNotExistException("msg.exception.notfound", null, "estudiante.titulo"));

        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new CursoNotExistException("msg.exception.notfound.", null, "curso.title"));

        boolean yaSuscrito = estudianteCursoRepository
                .existsByIdEstudianteIdAndIdCursoId(estudiante.getId(), cursoId);

        if (yaSuscrito) {
            throw new IllegalStateException("Ya estás suscrito a este curso.");
        }

        EstudianteCurso ec = new EstudianteCurso(estudiante, curso);

        // 5. Crear EstudianteCursoModulo para cada módulo del curso
        //List<Modulo> modulos = moduloRepository.findByCursoId(cursoId);
        List<CursoModulo> modulos = curso.getModulos();
        boolean first = true;
        for (CursoModulo cursoModulo : modulos) {
            EstudianteCursoModulo ecm = new EstudianteCursoModulo(ec, cursoModulo.getModulo());
            ecm.setOrden(cursoModulo.getOrden());
            ecm.setTitulo(cursoModulo.getModulo().getTitulo());
            ecm.setPeso(cursoModulo.getPeso());
            ecm.setEstado(first?EstadoCursoModulo.ACTIVO:EstadoCursoModulo.BLOQUEADO);
            first = false;
            ec.addModuloCurso(ecm);
        }
        curso.addUserRegistred();
        cursoRepository.save(curso);
        return estudianteCursoMapper.toDTO(estudianteCursoRepository.save(ec));

    }

    @Override
    public void actualizarUltimoAcceso(String username, Long cursoId) {
        // TODO: implementar actualización de último acceso
    }

    @Override
    public void marcarModuloComoCompletado(String username, Long cursoId, Long moduloId) {
        // TODO: implementar marcado de módulo completado
    }

    @Override
    public void recalcularProgreso(String username, Long cursoId) {
        // TODO: implementar recálculo de progreso
    }
	@Override
	public void completarModulo(Modulo modulo, Estudiante estudiante, MultipartFile entregable) {
		switch (modulo.getTipo()) {

        case TipoModulo.ENTREGA_OBLIGATORIA:
            if (entregable == null || entregable.isEmpty()) {
                throw new MandatoryModuloException("cualquier cosa",null, "entregable" );
            }
            guardarArchivo(entregable);
            marcarComoCompletado(estudiante, modulo);
            break;

        case TipoModulo.FINALIZACION_MANUAL:
            marcarComoCompletado(estudiante, modulo);
            break;
    }
		
	}

	private void guardarArchivo(MultipartFile entregable) {
		// TODO Auto-generated method stub
		
	}

	private void marcarComoCompletado(Estudiante estudiante, Modulo modulo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Paginacion<EstudianteCurso, EstudianteCursoDTO> listadoPaginado(PageParams pageData,
			Map<String, String> params) {
	return new Paginacion.Builder<EstudianteCurso, EstudianteCursoDTO>()
			.pagina(getPaginaBusqueda(PageRequest.of(pageData.page(), pageData.size()),params))
			.mapper(estudianteCursoMapper :: toDTO)
			.build();
	}

	private Page<EstudianteCurso> getPaginaBusqueda(Pageable pageable, Map<String, String> params){
		if (params.containsKey("estudianteId")){
			return estudianteCursoRepository.findByIdEstudianteId(Long.valueOf(params.get("estudianteId")));
		}
		return null;
	}	
}
