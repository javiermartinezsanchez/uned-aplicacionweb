package es.alumno.uned.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.EstudianteCursoModulo;
import es.alumno.uned.model.entities.EstudianteCursoModuloId;
/**
 * Repositorio de la entidad EstudianteCursoModulo
 */
public interface EstudianteCursoModuloRepository extends JpaRepository<EstudianteCursoModulo, EstudianteCursoModuloId> {
}
