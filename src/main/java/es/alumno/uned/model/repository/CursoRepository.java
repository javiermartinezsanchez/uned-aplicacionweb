package es.alumno.uned.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

}
