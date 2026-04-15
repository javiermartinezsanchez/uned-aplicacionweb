package es.alumno.uned.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.alumno.uned.model.entities.Rol;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.RolRepository;
import es.alumno.uned.model.repository.UsuarioRepository;

/**
 * Clase inicial de carga de datos.
 */
@Configuration
public class DatabaseLoader {
	
	/**
	 *   Generamos los datos de Roles y usuarios básicos (admin, profe1, estudiante1).
	 */
	@Bean
	public CommandLineRunner initializeDatabase(RolRepository rolRepo, UsuarioRepository userRepo, PasswordEncoder passEncoder) {
		
		return args -> {
			if (rolRepo.count() == 0) {
				rolRepo.save(new Rol("ADMIN", "Rol de Administrador"));
				rolRepo.save(new Rol("PROFE", "Rol de Profesor"));
				rolRepo.save(new Rol("ESTUD", "Rol de Estudiante"));
				
			}
			if (userRepo.findByEmail("admin@correo.es").isEmpty()) {
				userRepo.save(new Usuario("admin", "admin@correo.es", "", "", passEncoder.encode("1234")  , "ADMIN", true));
				
			}
			if (userRepo.findByEmail("profe1@correo.es").isEmpty()) {
				userRepo.save(new Usuario("Manuel", "profe1@correo.es", "López", "Rodríguez", passEncoder.encode("1234")  , "PROFE", true));
				
			}
			if (userRepo.findByEmail("estudiante1@correo.es").isEmpty()) {
				userRepo.save(new Usuario("José", "estudiante1@correo.es", "García", "Avellaneda", passEncoder.encode("1234")  , "ESTUD", true));
				
			}
		};
	}
	

}
