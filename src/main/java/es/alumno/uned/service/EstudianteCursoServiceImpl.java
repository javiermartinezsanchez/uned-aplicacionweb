package es.alumno.uned.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.exception.CursoNotExistException;
import es.alumno.uned.exception.ModuloException;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.EstudianteCurso;
import es.alumno.uned.model.entities.EstudianteCursoModulo;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.entities.TipoModulo;
import es.alumno.uned.model.repository.CursoRepository;
import es.alumno.uned.model.repository.EstudianteCursoModuloRepository;
import es.alumno.uned.model.repository.EstudianteCursoRepository;
import es.alumno.uned.model.repository.EstudianteRepository;
import es.alumno.uned.model.repository.ModuloRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class EstudianteCursoServiceImpl implements EstudianteCursoService {

    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;
    private final EstudianteCursoRepository estudianteCursoRepository;
    private final EstudianteCursoModuloRepository estudianteCursoModuloRepository;
    private final ModuloRepository moduloRepository;

    public EstudianteCursoServiceImpl(
            EstudianteRepository estudianteRepository,
            CursoRepository cursoRepository,
            EstudianteCursoRepository estudianteCursoRepository,
            EstudianteCursoModuloRepository estudianteCursoModuloRepository,
            ModuloRepository moduloRepository) {

        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
        this.estudianteCursoRepository = estudianteCursoRepository;
        this.estudianteCursoModuloRepository = estudianteCursoModuloRepository;
        this.moduloRepository = moduloRepository;
    }

    @Override
    public void subscribirAlumnoACurso(String username, Long cursoId) {
        // 1. Buscar estudiante por username
        Estudiante estudiante = estudianteRepository.findByUsuarioEmail(username)
                .orElseThrow(() -> new IllegalStateException("El estudiante no existe."));

        // 2. Buscar curso por ID
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new CursoNotExistException("El curso no existe."));

        // 3. Validar si ya está suscrito
        boolean yaSuscrito = estudianteCursoRepository
                .existsByIdEstudianteIdAndIdCursoId(estudiante.getId(), cursoId);

        if (yaSuscrito) {
            throw new IllegalStateException("Ya estás suscrito a este curso.");
        }

        // 4. Crear EstudianteCurso
        EstudianteCurso ec = new EstudianteCurso(estudiante, curso);
        estudianteCursoRepository.save(ec);

        // 5. Crear EstudianteCursoModulo para cada módulo del curso
        List<Modulo> modulos = moduloRepository.findByCursoId(cursoId);

        for (Modulo modulo : modulos) {
            EstudianteCursoModulo ecm = new EstudianteCursoModulo(ec, modulo);
            estudianteCursoModuloRepository.save(ecm);
        }

        // 6. Incrementar usuarios registrados del curso
        Integer actuales = curso.getUsuariosRegistrados();
        if (actuales == null) {
            actuales = 0;
        }
        curso.setUsuariosRegistrados(actuales + 1);
        // Si tu CursoService se encarga de esto, puedes delegar; si no:
        cursoRepository.save(curso);

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
                throw new ModuloException("Debe subir un archivo para completar este módulo");
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


}
