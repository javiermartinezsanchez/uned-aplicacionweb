package es.alumno.uned.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.CursoValoracion;

public interface CursoValoracionRepository extends JpaRepository<CursoValoracion, Long> {

    List<CursoValoracion> findByCurso(Curso curso);

	CursoValoracion findByCursoIdAndUsuario(Long cursoId, String usuario);
	
    @Query("SELECT AVG(v.valoracion) FROM CursoValoracion v WHERE v.curso.id = :cursoId")
    Double obtenerMediaValoraciones(@Param("cursoId") Long cursoId);
}

