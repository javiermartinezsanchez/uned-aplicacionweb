package es.alumno.uned.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.CursoValoracion;
/**
 * Repositorio entidad CursoValoración
 */
public interface CursoValoracionRepository extends JpaRepository<CursoValoracion, Long> {

    List<CursoValoracion> findByCurso(Curso curso);
/**
 * Buscar el CursoValoración por usuario.
 * 
 * @param cursoId Identificador del Curso valorado.
 * @param usuario Identificador del usuario valorador 
 * @return Se devuelve el CursoValoración de los datos enviados.
 */
	CursoValoracion findByCursoIdAndUsuario(Long cursoId, String usuario);
	
	/**
	 * Obtenemos la media de las valoraciones de un curso.
	 * @param cursoId
	 * @return Valoración media del curso.
	 */
    @Query("SELECT AVG(v.valoracion) FROM CursoValoracion v WHERE v.curso.id = :cursoId")
    Double obtenerMediaValoraciones(@Param("cursoId") Long cursoId);
}

