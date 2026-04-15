package es.alumno.uned.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.Rol;

public interface RolRepository extends JpaRepository<Rol, String>{

}
