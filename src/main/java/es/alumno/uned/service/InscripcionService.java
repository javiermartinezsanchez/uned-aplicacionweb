package es.alumno.uned.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.EstadoCurso;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.EstudianteCurso;
import es.alumno.uned.model.entities.EstudianteCursoId;
import es.alumno.uned.model.repository.CursoRepository;
import es.alumno.uned.model.repository.EstudianteCursoRepository;
import es.alumno.uned.model.repository.EstudianteRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class InscripcionService {

    @Autowired
    private EstudianteCursoRepository estudianteCursoRepository;
    
    @Autowired
    private CursoRepository cursoRepository;
    
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Transactional
    public void suscribirEstudiante(Long cursoId, String username) {
        // 1. Recuperamos las entidades base
        Curso curso = cursoRepository.findById(cursoId)
            .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado"));
            
        Estudiante estudiante = estudianteRepository.findByUsuarioEmail(username)
            .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado"));

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
            inscripcion.setEstado(EstadoCurso.ACTIVO);
            
            estudianteCursoRepository.save(inscripcion);

            // 4. ¡CLAVE PARA EL CARRUSEL!: Incrementamos el contador en el Curso
            curso.setUsuariosRegistrados(curso.getUsuariosRegistrados() + 1);
            
            // Si tienes la relación bidireccional en Curso, sincronizamos en memoria
            // curso.getInscripciones().add(inscripcion); 
            
            cursoRepository.save(curso);
        }
    }
}

