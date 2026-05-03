package es.alumno.uned.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.alumno.uned.model.entities.EstudianteCurso;
import es.alumno.uned.model.entities.EstudianteCursoModulo;
import es.alumno.uned.model.entities.EstudianteCursoModuloId;

public interface EstudianteCursoModuloRepository extends JpaRepository<EstudianteCursoModulo, EstudianteCursoModuloId> {

    @Query("""
            SELECT ecm FROM EstudianteCursoModulo ecm
            WHERE ecm.estudianteCurso.estudiante.usuario.email = :username
            AND ecm.estudianteCurso.curso.id = :cursoId
            AND ecm.modulo.id = :moduloId
        """)
    Optional<EstudianteCursoModulo> findByIds(String username, Long cursoId, Long moduloId);

    List<EstudianteCursoModulo> findByEstudianteCurso(EstudianteCurso ec);
}
