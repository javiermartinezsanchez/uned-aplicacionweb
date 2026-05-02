package es.alumno.uned.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.CursoValoracion;

public interface CursoValoracionRepository extends JpaRepository<CursoValoracion, Long> {

    List<CursoValoracion> findByCurso(Curso curso);

	CursoValoracion findByCursoIdAndUsuario(Long cursoId, String usuario);
}

