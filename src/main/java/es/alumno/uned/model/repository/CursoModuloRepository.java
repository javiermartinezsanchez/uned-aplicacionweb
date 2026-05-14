package es.alumno.uned.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.CursoModulo;
import es.alumno.uned.model.entities.CursoModuloId;

public interface CursoModuloRepository extends JpaRepository<CursoModulo, CursoModuloId> {

}
