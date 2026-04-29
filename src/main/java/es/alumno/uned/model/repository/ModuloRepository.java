package es.alumno.uned.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.Modulo;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {

}
