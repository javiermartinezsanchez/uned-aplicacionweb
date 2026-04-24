package es.alumno.uned.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.entities.Rol;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.AreaTematicaRepository;
import es.alumno.uned.model.repository.RolRepository;
import es.alumno.uned.model.repository.UsuarioRepository;

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
 * </ul>
 */
@Configuration
public class DatabaseLoader {
	
	/**
	 *   Generamos los datos de Roles y usuarios básicos (admin, profe1, estudiante1).
	 */
	@Bean
	public CommandLineRunner initializeDatabase(
			RolRepository rolRepo, 
			UsuarioRepository userRepo, 
			AreaTematicaRepository areaRepo,
			PasswordEncoder passEncoder) {
		
		return args -> {
			if (rolRepo.count() == 0) {
				rolRepo.save(new Rol("ADMIN", "Rol de Administrador"));
				rolRepo.save(new Rol("PROFE", "Rol de Profesor"));
				rolRepo.save(new Rol("ESTUD", "Rol de Estudiante"));
				
			}
			if (userRepo.findByEmail("admin@correo.es").isEmpty()) {
				userRepo.save(new Usuario("admin", "admin@correo.es", "", "", passEncoder.encode("1234")  , "ADMIN", true, LocalDateTime.now(), "CARGA INICIAL"));
				
			}
			if (userRepo.findByEmail("profe1@correo.es").isEmpty()) {
				userRepo.save(new Usuario("Manuel", "profe1@correo.es", "López", "Rodríguez", passEncoder.encode("1234")  , "PROFE", true, LocalDateTime.now(), "CARGA INICIAL"));
				
			}
			if (userRepo.findByEmail("estudiante1@correo.es").isEmpty()) {
				userRepo.save(new Usuario("José", "estudiante1@correo.es", "García", "Avellaneda", passEncoder.encode("1234")  , "ESTUD", true, LocalDateTime.now(), "CARGA INICIAL"));
				
			}
			if (userRepo.findByEmail("luis.estudiante@correo.es").isEmpty()) {
				userRepo.save(new Usuario("Luís", "luis.estudiante@correo.es", "Condemor", "Sindón", passEncoder.encode("1234")  , "ESTUD", true, LocalDateTime.now(), "CARGA INICIAL"));
				
			}
			if (userRepo.findByEmail("julian.estudiante@correo.es").isEmpty()) {
				userRepo.save(new Usuario("Julián", "julian.estudiante@correo.es", "SinLuz", "Solar", passEncoder.encode("1234")  , "ESTUD", true, LocalDateTime.now(), "CARGA INICIAL"));
				
			}
			if (userRepo.findByEmail("maria.estudiante@correo.es").isEmpty()) {
				userRepo.save(new Usuario("Maria", "maria.estudiante@correo.es", "Relaño", "Wilson", passEncoder.encode("1234")  , "ESTUD", true, LocalDateTime.now(), "CARGA INICIAL"));
				
			}
			if (areaRepo.findByTitulo("Sanidad y Salud") == null) {
				areaRepo.save(new AreaTematica("Sanidad y Salud","cursos online de sanidad y salud"));
			}
			if (areaRepo.findByTitulo("Gestión") == null) {
				areaRepo.save(new AreaTematica("Gestión", "Actividades relacionadas con la organización, planificación y coordinación de recursos y tareas."));
			}
			if (areaRepo.findByTitulo("Comunicación") == null) {
				areaRepo.save(new AreaTematica("Comunicación", "Intercambio de información entre personas, equipos o entidades, tanto interna como externamente."));
			}

			if (areaRepo.findByTitulo("Procesos") == null) {
				areaRepo.save(new AreaTematica("Procesos", "Conjunto de actividades estructuradas orientadas a la consecución de un objetivo específico."));
			}

			if (areaRepo.findByTitulo("Calidad") == null) {
				areaRepo.save(new AreaTematica("Calidad", "Acciones destinadas a asegurar el cumplimiento de estándares, buenas prácticas y mejora continua."));
			}

			if (areaRepo.findByTitulo("Formación") == null) {
				areaRepo.save(new AreaTematica("Formación", "Iniciativas orientadas al aprendizaje, capacitación y desarrollo de conocimientos y habilidades."));
			}

			if (areaRepo.findByTitulo("Información") == null) {
				areaRepo.save(new AreaTematica("Información", "Gestión, tratamiento y uso de datos y contenidos relevantes para la organización."));
			}

			if (areaRepo.findByTitulo("Análisis") == null) {
				areaRepo.save(new AreaTematica("Análisis", "Evaluación y estudio de datos, situaciones o resultados para facilitar la toma de decisiones."));
			}

			if (areaRepo.findByTitulo("Seguimiento") == null) {
				areaRepo.save(new AreaTematica("Seguimiento", "Control periódico del estado y evolución de actividades, tareas o indicadores."));
			}

			if (areaRepo.findByTitulo("Planificación") == null) {
				areaRepo.save(new AreaTematica("Planificación", "Definición anticipada de objetivos, acciones y recursos necesarios para alcanzarlos."));
			}

			if (areaRepo.findByTitulo("Mejora Continua") == null) {
				areaRepo.save(new AreaTematica("Mejora Continua", "Identificación e implantación de cambios orientados a optimizar resultados y eficiencia."));
			}

			if (areaRepo.findByTitulo("Coordinación") == null) {
				areaRepo.save(new AreaTematica("Coordinación", "Alineación de esfuerzos entre distintas personas, áreas o unidades de trabajo."));
			}

			if (areaRepo.findByTitulo("Documentación") == null) {
				areaRepo.save(new AreaTematica("Documentación", "Creación, mantenimiento y consulta de documentos y materiales de referencia."));
			}
			
			if (areaRepo.findByTitulo("Evaluación") == null) {
				areaRepo.save(new AreaTematica("Evaluación", "Valoración sistemática de resultados, desempeño o impacto de acciones realizadas."));
			}

			if (areaRepo.findByTitulo("Soporte General") == null) {
				areaRepo.save(new AreaTematica("Soporte General", "Atención y apoyo a personas o áreas para resolver dudas, incidencias o necesidades."));
			}

			if (areaRepo.findByTitulo("Cumplimiento") == null) {
				areaRepo.save(new AreaTematica("Cumplimiento", "Verificación del respeto a normas, políticas, acuerdos o requisitos establecidos."));
			}

			if (areaRepo.findByTitulo("Aprendizaje") == null) {
				areaRepo.save(new AreaTematica("Aprendizaje", "Procesos mediante los cuales el alumnado adquiere conocimientos, habilidades y competencias."));
			}

			if (areaRepo.findByTitulo("Enseñanza") == null) {
				areaRepo.save(new AreaTematica("Enseñanza", "Conjunto de métodos, estrategias y acciones orientadas a facilitar el aprendizaje."));
			}

			if (areaRepo.findByTitulo("Evaluación") == null) {
				areaRepo.save(new AreaTematica("Evaluación", "Valoración del progreso, rendimiento y logro de objetivos educativos."));
			}

			if (areaRepo.findByTitulo("Orientación Educativa") == null) {
				areaRepo.save(new AreaTematica("Orientación Educativa", "Acompañamiento y apoyo al alumnado en su desarrollo académico y personal."));
			}

			if (areaRepo.findByTitulo("Formación") == null) {
				areaRepo.save(new AreaTematica("Formación", "Actividades educativas dirigidas a la adquisición y mejora de conocimientos y capacidades."));
			}

			if (areaRepo.findByTitulo("Desarrollo Personal") == null) {
				areaRepo.save(new AreaTematica("Desarrollo Personal", "Fomento de habilidades personales, sociales y emocionales del alumnado."));
			}

			if (areaRepo.findByTitulo("Metodología") == null) {
				areaRepo.save(new AreaTematica("Metodología", "Enfoques y técnicas pedagógicas empleadas en el proceso educativo."));
			}

			if (areaRepo.findByTitulo("Inclusión") == null) {
				areaRepo.save(new AreaTematica("Inclusión", "Acciones orientadas a garantizar la igualdad de oportunidades y la atención a la diversidad."));
			}

			if (areaRepo.findByTitulo("Seguimiento Académico") == null) {
				areaRepo.save(new AreaTematica("Seguimiento Académico", "Control y análisis del progreso educativo a lo largo del tiempo."));
			}
			
			if (areaRepo.findByTitulo("Convivencia") == null) {
				areaRepo.save(new AreaTematica("Convivencia", "Promoción de relaciones respetuosas, participativas y saludables en la comunidad educativa."));
			}
			
			if (areaRepo.findByTitulo("Innovación Educativa") == null) {
				areaRepo.save(new AreaTematica("Innovación Educativa", "Aplicación de nuevas ideas, prácticas o recursos para mejorar la enseñanza y el aprendizaje."));
			}
												
			if (areaRepo.findByTitulo("Competencias") == null) {
				areaRepo.save(new AreaTematica("Competencias", "Desarrollo de capacidades clave para el aprendizaje permanente y la vida activa."));
			}

												
			if (areaRepo.findByTitulo("Planificación Educativa") == null) {
				areaRepo.save(new AreaTematica("Planificación Educativa", "Organización y diseño de objetivos, contenidos y actividades formativas."));
			}
												
			if (areaRepo.findByTitulo("Recursos Educativos") == null) {
				areaRepo.save(new AreaTematica("Recursos Educativos", "Materiales, herramientas y medios utilizados para apoyar el proceso educativo."));
			}
												
			if (areaRepo.findByTitulo("Participación") == null) {
				areaRepo.save(new AreaTematica("Participación", "Implicación activa del alumnado y otros agentes en la comunidad educativa."));
			}
			
		};
	}
	

}
