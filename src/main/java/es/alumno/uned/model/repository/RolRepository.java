package es.alumno.uned.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.Rol;
/**
 * Repositorio de la entidad ROL.
 * <p> Sólo se utiliza en la inicialización de datos y a nivel de consultas.
 */
public interface RolRepository extends JpaRepository<Rol, String>{

}
