package es.alumno.uned.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.entities.Rol;
import es.alumno.uned.model.entities.TipoModulo;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.AreaTematicaRepository;
import es.alumno.uned.model.repository.CursoRepository;
import es.alumno.uned.model.repository.EstudianteRepository;
import es.alumno.uned.model.repository.ModuloRepository;
import es.alumno.uned.model.repository.RolRepository;
import es.alumno.uned.model.repository.UsuarioRepository;
import es.alumno.uned.service.DataInitializationService;

/**
 * Clase inicial de carga de datos.
 * 
 * <ul>
 * <li>Tabla de ROLES de usuario</li>
 * <li>Tabla de USUARIOS</li>
 * 
 * <li>Tabla de ESTUDIANTES (con los datos de los usuarios EST)</li>
 * <li>Tabla de Areas Temáticas</li>
 * <li>Tabla de CURSOS</li>
 * <li>Tabla de modulos</li>
 * </ul>
 */
@Configuration
public class DatabaseLoader {
	
	/**
	 *   Generamos los datos de Roles y usuarios básicos (admin, profe1, estudiante1).
	 */
	@Bean
	public CommandLineRunner initializeDatabase(
			DataInitializationService initService) {
	    return args -> {
	        initService.cargarDatos();
	    };
	}
	

}
