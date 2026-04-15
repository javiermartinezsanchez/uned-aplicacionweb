package es.alumno.uned.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	public Optional<Usuario> findByEmail(String email);
}
