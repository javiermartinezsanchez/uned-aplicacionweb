package es.alumno.uned.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.alumno.uned.exception.CursoNotExistException;
import es.alumno.uned.exception.EstudianteNotExistException;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.EstadoCursoModulo;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.EstudianteCurso;
import es.alumno.uned.model.entities.EstudianteCursoId;
import es.alumno.uned.model.repository.CursoRepository;
import es.alumno.uned.model.repository.EstudianteCursoRepository;
import es.alumno.uned.model.repository.EstudianteRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class InscripcionServiceImpl implements InscripcionService {

    @Autowired
    private EstudianteCursoRepository estudianteCursoRepository;
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Override
	@Transactional
    public void suscribirEstudiante(Long cursoId, String username) {
        // 1. Recuperamos las entidades base
        Curso curso = cursoRepository.findById(cursoId)
            .orElseThrow(() -> new CursoNotExistException("msg.exception.notfound", "curso.title"));
            
        Estudiante estudiante = estudianteRepository.findByUsuarioEmail(username)
            .orElseThrow(() -> new EstudianteNotExistException("msg.exception.notfound", null, "estudiante.titulo"));

        // 2. Comprobamos si ya está inscrito utilizando la clave compuesta
        EstudianteCursoId idCompuesto = new EstudianteCursoId(estudiante.getId(), curso.getId());
        boolean yaInscrito = estudianteCursoRepository.existsById(idCompuesto);

        if (!yaInscrito) {
            // 3. Creamos el nuevo registro intermedio
            EstudianteCurso inscripcion = new EstudianteCurso();
            inscripcion.setId(idCompuesto);
            inscripcion.setEstudiante(estudiante);
            inscripcion.setCurso(curso);
            inscripcion.setFechaSubscripcion(LocalDateTime.now());
            inscripcion.setFechaUltimoAcceso(LocalDateTime.now());
            inscripcion.setProgreso(0);
            inscripcion.setEstado(EstadoCursoModulo.ACTIVO);
            
            estudianteCursoRepository.save(inscripcion);

            curso.setUsuariosRegistrados(curso.getUsuariosRegistrados() + 1);
            
            cursoRepository.save(curso);
        }
    }
}

