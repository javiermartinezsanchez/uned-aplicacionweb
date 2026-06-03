package es.alumno.uned.service;

import java.time.LocalDate;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
/**
 * 
 * Clase {@code @Service} de inicialización de datos para las pruebas.
 * 
 * <p> Se genera 
 * 
 * <p>Comprueba si existe un dato y lo inserta.
 *  <ul>
* <li>Tabla de ROLES de usuario</li>
* <li>Tabla de USUARIOS</li>
* 
* <li>Tabla de ESTUDIANTES (con los datos de los usuarios EST)</li>
* <li>Tabla de Areas Temáticas</li>
* <li>Tabla de CURSOS</li>
* <li>Tabla de modulos</li>
* <li>Tabla de curso-modulos</li>
* </ul>
 * @param rolRepo Repositorio de Roles.
 * @param userRepo Repositorio de Usuarios.
 * @param estudianteRepo Repositorio de Estudiantes.
 * @param areaRepo Repositorio de Áreas Temáticas.
 * @param cursoRepo Repositorio de Cursos.
 * @param moduloRepo Repositorio de Módulos.
 * @param passEncoder Clase de encriptación de la contraseña.
 *
*/
@Service
public class DataInitializationService {
	RolRepository rolRepo;
	UsuarioRepository userRepo; 
	EstudianteRepository estudianteRepo; 
	AreaTematicaRepository areaRepo;
	CursoRepository cursoRepo;
	ModuloRepository moduloRepo;
	PasswordEncoder passEncoder;
	
	
	public DataInitializationService(RolRepository rolRepo,
		UsuarioRepository userRepo, 
		EstudianteRepository estudianteRepo, 
		AreaTematicaRepository areaRepo,
		CursoRepository cursoRepo,
		ModuloRepository moduloRepo,
		PasswordEncoder passEncoder) {
		
		this.rolRepo= rolRepo ;
		this.userRepo = userRepo; 
		this.estudianteRepo = estudianteRepo; 
		this.areaRepo = areaRepo;
		this.cursoRepo = cursoRepo;
		this.moduloRepo = moduloRepo;
		this.passEncoder = passEncoder;
	}
	
	@Transactional 
    public void cargarDatos() {
		if (rolRepo.count() == 0) {
			rolRepo.save(new Rol("ADMIN", "Rol de Administrador"));
			rolRepo.save(new Rol("PROFE", "Rol de Profesor"));
			rolRepo.save(new Rol("ESTUD", "Rol de Estudiante"));
			
		}
		if (userRepo.findByEmail("admin@correo.es").isEmpty()) {
			userRepo.save(new Usuario("admin", "admin@correo.es", "number", "one", passEncoder.encode("admin@correo.es")  , "ADMIN", true, LocalDateTime.now(), "CARGA INICIAL"));
			
		}
		if (userRepo.findByEmail("profe1@correo.es").isEmpty()) {
			userRepo.save(new Usuario("Manuel", "profe1@correo.es", "López", "Rodríguez", passEncoder.encode("profe1@correo.es")  , "PROFE", true, LocalDateTime.now(), "CARGA INICIAL"));
			
		}
		if (userRepo.findByEmail("profe2@correo.es").isEmpty()) {
			userRepo.save(new Usuario("Luis", "profe2@correo.es", "García", "Kalimotxo", passEncoder.encode("profe2@correo.es")  , "PROFE", true, LocalDateTime.now(), "CARGA INICIAL"));
			
		}
		
		if (estudianteRepo.findByUsuarioEmail("estudiante1@correo.es").isEmpty()) {
			estudianteRepo.save(new Estudiante( "Direccion1", "Población1", "Provincia1", "00000", 
					new Usuario("José", "estudiante1@correo.es", "García", "Avellaneda", passEncoder.encode("estudiante1@correo.es")  , "ESTUD", true, LocalDateTime.now(), "CARGA INICIAL")));
		}
		if (estudianteRepo.findByUsuarioEmail("luis.estudiante@correo.es").isEmpty()) {
			estudianteRepo.save(new Estudiante( "Direccion2", "Población2", "Provincia2", "00002", 
					new Usuario("Luís", "luis.estudiante@correo.es", "Condemor", "Sindón", passEncoder.encode("luis.estudiante@correo.es")  , "ESTUD", true, LocalDateTime.now(), "CARGA INICIAL")));
		}
		if (estudianteRepo.findByUsuarioEmail("julian.estudiante@correo.es").isEmpty()) {
			estudianteRepo.save(new Estudiante( "Direccion3", "Población3", "Provincia2", "00003", 
					new Usuario("Julián", "julian.estudiante@correo.es", "SinLuz", "Solar", passEncoder.encode("julian.estudiante@correo.es")  , "ESTUD", true, LocalDateTime.now(), "CARGA INICIAL")));
		}
		if (estudianteRepo.findByUsuarioEmail("maria.estudiante@correo.es").isEmpty()) {
			Usuario u1 = userRepo.findByEmail("maria.estudiante@correo.es").get();
			estudianteRepo.save(new Estudiante( "Direccion4", "Población4", "Provincia4", "00004", 
					new Usuario("Maria", "maria.estudiante@correo.es", "Relaño", "Wilson", passEncoder.encode("maria.estudiante@correo.es")  , "ESTUD", true, LocalDateTime.now(), "CARGA INICIAL")));
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
		if (areaRepo.findByTitulo("Desarrollo Personal") == null) {
			areaRepo.save(new AreaTematica("Desarrollo Personal", "Fomento de habilidades personales, sociales y emocionales del alumnado."));
		}

		if (areaRepo.findByTitulo("Procesos") == null) {
			areaRepo.save(new AreaTematica("Procesos", "Conjunto de actividades estructuradas orientadas a la consecución de un objetivo específico."));
		}

		if (areaRepo.findByTitulo("Calidad") == null) {
			areaRepo.save(new AreaTematica("Calidad", "Acciones destinadas a asegurar el cumplimiento de estándares, buenas prácticas y mejora continua."));
		}

		if (areaRepo.findByTitulo("Mejora Continua") == null) {
			areaRepo.save(new AreaTematica("Mejora Continua", "Identificación e implantación de cambios orientados a optimizar resultados y eficiencia."));
		}

		if (areaRepo.findByTitulo("Documentación") == null) {
			areaRepo.save(new AreaTematica("Documentación", "Creación, mantenimiento y consulta de documentos y materiales de referencia."));
		}
		
		if (areaRepo.findByTitulo("Metodología") == null) {
			areaRepo.save(new AreaTematica("Metodología", "Enfoques y técnicas pedagógicas empleadas en el proceso educativo."));
		}
		if (areaRepo.findByTitulo("Frameworks Java MVC") == null) {
			areaRepo.save(new AreaTematica("Frameworks Java MVC", "Frameworks en Java para el desarrollo de aplicaciones Web MVC."));
		}
		if (areaRepo.findByTitulo("Frameworks Front-End") == null) {
			areaRepo.save(new AreaTematica("Frameworks Front-End", "Frameworks en JS para el desarrollo de aplicaciones Web MVC."));
		}
		if (areaRepo.findByTitulo("Lenguajes de Base de Datos") == null) {
			areaRepo.save(new AreaTematica("Lenguajes de Base de Datos", "Formación sobre el lenguaje SQL normalizado en las consultas a Bases de datos relacionales."));
		}

		/*
		  CARGA INICIAL DE CURSOS
		*/
		/*
		 * 
		 * Localizamos un profesor (el primero que encontremos).
		 * 
		 * Si no encontramos ninguno, no añadimos cursos.
		 * 
		 */
		Usuario profesor = userRepo.findAll().stream()
				.filter(u -> "PROFE".equals(u.getRol()))
				.findFirst()
				.orElse(null);
		if (profesor != null) {		
			/*
			 * 
			 * SELECT CONCAT(
    'if (cursoRepo.findByTitulo("', titulo, '").isEmpty()) { ',
    'cursoRepo.save(new Curso("', titulo, '", "', descripcion, '", "', 
    COALESCE(imagen_curso, ''), '", ', nivel, ', areaRepo.findByTitulo("', 
    (SELECT titulo FROM area_tematica WHERE area_id = area_tematica_id), '"), profesor, ', 
    duracion, ', LocalDate.parse("', fecha_ini, '"), LocalDate.parse("', 
    fecha_fin, '"), LocalDateTime.now(), "CARGAINICIAL")); }'
) AS codigo_java
FROM cursos;

			 */
			if (cursoRepo.findByTitulo("React Moderno").isEmpty()) { cursoRepo.save(new Curso("React Moderno", "Hooks, componentes, estado global y buenas prácticas.", "react.jfif", 2, areaRepo.findByTitulo("Frameworks Front-End"), profesor, 100, LocalDate.parse("2026-04-20"), LocalDate.parse("2026-05-25"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("SQL desde Cero").isEmpty()) { cursoRepo.save(new Curso("SQL desde Cero", "Consultas, joins, funciones y modelado relacional.", "sql_imagen.jpg", 1, areaRepo.findByTitulo("Lenguajes de Base de Datos"), profesor, 35, LocalDate.parse("2026-05-05"), LocalDate.parse("2026-05-25"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("Spring Boot desde Cero").isEmpty()) { cursoRepo.save(new Curso("Spring Boot desde Cero", "Curso práctico para aprender a crear aplicaciones web con Spring Boot.", "spring_boot.png", 1, areaRepo.findByTitulo("Frameworks Java MVC"), profesor, 40, LocalDate.parse("2026-05-10"), LocalDate.parse("2026-06-10"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("Machine Learning con Python").isEmpty()) { cursoRepo.save(new Curso("Machine Learning con Python", "Modelos supervisados, no supervisados y pipelines de ML.", "Python_for_ia.png", 4, areaRepo.findByTitulo("IA"), profesor, 80, LocalDate.parse("2026-07-01"), LocalDate.parse("2026-08-15"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("Fundamentos de Redes").isEmpty()) { cursoRepo.save(new Curso("Fundamentos de Redes", "TCP/IP, routing, switching y configuración básica.", "Redes_de_datos.png", 1, areaRepo.findByTitulo("Comunicación"), profesor, 40, LocalDate.parse("2026-06-01"), LocalDate.parse("2026-06-30"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("Gestión del Estrés").isEmpty()) { cursoRepo.save(new Curso("Gestión del Estrés", "Técnicas prácticas para identificar, reducir y gestionar el estrés en la vida diaria.", "Gestion_del_Estres.png", 1, areaRepo.findByTitulo("Comunicación"), profesor, 20, LocalDate.parse("2026-05-10"), LocalDate.parse("2026-05-20"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("Inteligencia Emocional").isEmpty()) { cursoRepo.save(new Curso("Inteligencia Emocional", "Gestión de emociones, empatía, autocontrol y habilidades sociales para el día a día.", "Inteligencia_Emocional.png", 3, areaRepo.findByTitulo("Comunicación"), profesor, 40, LocalDate.parse("2026-07-01"), LocalDate.parse("2026-07-25"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("Diseño y Arquitectura").isEmpty()) { cursoRepo.save(new Curso("Diseño y Arquitectura", "Patrones, principios SOLID y arquitectura hexagonal.", "Patrones_POO_SOLID.png", 4, areaRepo.findByTitulo("Comunicación"), profesor, 70, LocalDate.parse("2026-07-10"), LocalDate.parse("2026-08-20"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("Java Profesional").isEmpty()) { cursoRepo.save(new Curso("Java Profesional", "Conceptos avanzados de Java, colecciones, concurrencia y buenas prácticas.", "java_avanzado.png", 3, areaRepo.findByTitulo("Comunicación"), profesor, 60, LocalDate.parse("2026-04-15"), LocalDate.parse("2026-05-30"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("Power BI Profesional").isEmpty()) { cursoRepo.save(new Curso("Power BI Profesional", "Modelado, DAX, dashboards y publicación.", "Power_BI.png", 2, areaRepo.findByTitulo("Comunicación"), profesor, 30, LocalDate.parse("2026-05-15"), LocalDate.parse("2026-06-05"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("Linux para Administradores").isEmpty()) { cursoRepo.save(new Curso("Linux para Administradores", "Gestión de usuarios, permisos, servicios y seguridad.", "java-expert.png", 3, areaRepo.findByTitulo("Comunicación"), profesor, 50, LocalDate.parse("2026-06-10"), LocalDate.parse("2026-07-05"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("Hablar en Público").isEmpty()) { cursoRepo.save(new Curso("Hablar en Público", "Desarrollo de habilidades de comunicación, oratoria y control del lenguaje corporal.", "Hablar_en_Publico.png", 2, areaRepo.findByTitulo("Comunicación"), profesor, 25, LocalDate.parse("2026-06-01"), LocalDate.parse("2026-06-18"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("Ciberseguridad para Principiantes").isEmpty()) { cursoRepo.save(new Curso("Ciberseguridad para Principiantes", "Fundamentos de seguridad informática, amenazas y medidas de protección.", "Seguridad.jpg", 1, areaRepo.findByTitulo("Comunicación"), profesor, 30, LocalDate.parse("2026-06-01"), LocalDate.parse("2026-06-20"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("Creatividad Práctica").isEmpty()) { cursoRepo.save(new Curso("Creatividad Práctica", "Métodos creativos para generar ideas, resolver problemas y fomentar la innovación personal.", "creatividad.png", 2, areaRepo.findByTitulo("Comunicación"), profesor, 35, LocalDate.parse("2026-06-10"), LocalDate.parse("2026-06-30"), LocalDateTime.now(), "CARGAINICIAL")); }
			if (cursoRepo.findByTitulo("Nutrición para la Vida").isEmpty()) { cursoRepo.save(new Curso("Nutrición para la Vida", "Fundamentos de alimentación equilibrada, lectura de etiquetas y planificación de menús.", "Nutricion_para_la_vida.png", 1, areaRepo.findByTitulo("Comunicación"), profesor, 30, LocalDate.parse("2026-05-15"), LocalDate.parse("2026-06-05"), LocalDateTime.now(), "CARGAINICIAL")); }
			
			/*
			 * -- MODULOS
SELECT CONCAT(
    'if (moduloRepo.findByTitulo("', titulo, '").isEmpty()) { ',
    'moduloRepo.save(new Modulo("', titulo, '", "', descripcion, '", "', 
    contenido, '", "', tipo , '", LocalDateTime.now(), "CARGAINICIAL")); }'
) AS codigo_java
FROM modulos;
			 */
			
			
			if (moduloRepo.findByTituloContainingIgnoreCase("Objetivo del Proyecto API REST")== null) { 
				moduloRepo.save(new Modulo("Objetivo del Proyecto API REST", "Objetivo del Proyecto", "", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
			}
			if (moduloRepo.findByTituloContainingIgnoreCase("Estructura proyecto API REST (MAVEN)")== null) { moduloRepo.save(new Modulo("Estructura proyecto API REST (MAVEN)", "Estructura proyecto", "", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
			}
			if (moduloRepo.findByTituloContainingIgnoreCase("Manejo Global de Excepciones (GlobalExceptionHandler.java)")== null) { moduloRepo.save(new Modulo("Manejo Global de Excepciones (GlobalExceptionHandler.java)", "Manejo Global de Excepciones (GlobalExceptionHandler.java)", "16589", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); }
			if (moduloRepo.findByTituloContainingIgnoreCase("DTO y Validación (ResourceDTO.java)")== null) { moduloRepo.save(new Modulo("DTO y Validación (ResourceDTO.java)", "DTO y Validación (ResourceDTO.java)", "", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); }
			if (moduloRepo.findByTituloContainingIgnoreCase("Controlador REST (ResourceController.java)")== null) { moduloRepo.save(new Modulo("Controlador REST (ResourceController.java)", "Controlador REST (ResourceController.java)", "", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); }
			if (moduloRepo.findByTituloContainingIgnoreCase("Documentación (Swagger/OpenAPI)")== null) { moduloRepo.save(new Modulo("Documentación (Swagger/OpenAPI)", "Documentación (Swagger/OpenAPI)", "16592", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); }
			if (moduloRepo.findByTituloContainingIgnoreCase("Características Avanzadas a Implementar")== null) { moduloRepo.save(new Modulo("Características Avanzadas a Implementar", "Características Avanzadas a Implementar", "", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); }
			if (moduloRepo.findByTituloContainingIgnoreCase("Criterios de Éxito (Checklist)")== null) { moduloRepo.save(new Modulo("Criterios de Éxito (Checklist)", "Criterios de Éxito (Checklist)", "", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); }
			if (moduloRepo.findByTituloContainingIgnoreCase("Ejercicio Práctico para el Alumno API-REST")== null) { moduloRepo.save(new Modulo("Ejercicio Práctico para el Alumno API-REST", "Ejercicio Práctico para el Alumno", "Entrega de memoria con la práctica realizada", TipoModulo.ENTREGA_OBLIGATORIA, LocalDateTime.now(), "CARGAINICIAL")); }
			if (moduloRepo.findByTituloContainingIgnoreCase("Introducción al Curso")== null) { moduloRepo.save(new Modulo("Introducción al Curso", "Bienvenida e Introducción al curso", 
					"""
						Nos alegra enormemente contar con tu participación en este curso. 

						Este espacio ha sido diseñado meticulosamente para ofrecerte una experiencia de aprendizaje práctica, dinámica y profundamente conectada con las demandas reales de tu entorno profesional.

						A lo largo de los diferentes módulos, exploraremos conceptos clave, resolveremos casos prácticos y desarrollaremos las habilidades estratégicas que necesitas para alcanzar tus metas. Te invitamos a aprovechar al máximo cada recurso, participar activamente en las actividades y utilizar los canales de comunicación para resolver cualquier duda que te surja en el camino.

						El conocimiento es el camino más directo hacia la transformación. 
						
						¡Mucho éxito en este viaje de aprendizaje que comienzas hoy!""", 
						TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
			}

			if (moduloRepo.findByTituloContainingIgnoreCase("Despedida")== null) { moduloRepo.save(new Modulo("Despedida", "Despedida y Agradecimientos", 
					"""
						Hemos llegado al final de nuestro curso y queremos felicitarte sinceramente por tu dedicación, constancia y esfuerzo a lo largo de todas estas semanas. 
			Concluir este programa es un reflejo de tu compromiso con tu propio crecimiento y desarrollo profesional.Esperamos que las herramientas, metodologías y conocimientos adquiridos a lo largo de las sesiones se conviertan en un activo de gran valor para tu día a día, permitiéndote afrontar los futuros retos con mayor seguridad y perspectiva. 

			El aprendizaje no termina aquí; este es solo el cierre de una etapa y el inicio de la aplicación práctica de todo lo aprendido.

			Te deseamos el mayor de los éxitos en todos tus proyectos futuros. 
			
			¡Gracias por haber formado parte de esta experiencia formativa!""", 
			TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
			}
			if (moduloRepo.findByTituloContainingIgnoreCase("Introducción a React y Componentes")== null) { moduloRepo.save(new Modulo("Introducción a React y Componentes", "Configuración del entorno, JSX y creación de componentes funcionales.", 
					"""
			
			Configuración del entorno con Vite, sintaxis JSX, componentes funcionales, renderizado condicional, manejo de eventos básicos y uso de fragmentos (<></>).""", 
			TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
			}
			if (moduloRepo.findByTituloContainingIgnoreCase("React Props y Estado (State)")== null) { moduloRepo.save(new Modulo("React Props y Estado (State)", "Pasando datos entre componentes y manejo de estados locales con useState.", 
					"""
			
			Comunicación entre componentes (padre a hijo) mediante props, validación con PropTypes, desestructuración, introducción al hook useState y actualización asíncrona del estado.
			
			Crear un lista de tareas (Todo App) interactiva donde se puedan añadir más elementos.""",
			TipoModulo.ENTREGA_OBLIGATORIA, LocalDateTime.now(), "CARGAINICIAL")); }
			if (moduloRepo.findByTituloContainingIgnoreCase("React Efectos y Ciclo de Vida")== null) { moduloRepo.save(new Modulo("React Efectos y Ciclo de Vida", "Sincronización con APIs externas y uso avanzado del hook useEffect", 
					"""
					- Ciclo de vida de los componentes.
					- Efectos secundarios con el hook useEffect.
					- Consumo de APIs REST usando fetch o axios.
					- Manejo de estados de carga (loading) y errores.
					- Limpieza de efectos (cleanup functions)."""
			, TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); }
			if (moduloRepo.findByTituloContainingIgnoreCase("Enrutamiento con React Router")== null) { moduloRepo.save(new Modulo("Enrutamiento con React Router", "Creación de aplicaciones de múltiples páginas y navegación interna.", 
					"""
			-Instalación y configuración de react-router-dom, definición de rutas estáticas y dinámicas (/productos/:id) 
			-Uso de enlaces de navegación (Link, NavLink) y redirecciones programáticas con useNavigate."""
			, TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
			}
			if (moduloRepo.findByTituloContainingIgnoreCase("React. Gestión de Estado")== null) { 
				moduloRepo.save(new Modulo("React. Gestión de Estado", "Compartir información en toda la app usando Context API o Redux Toolkit.", 
					"""
					- Limitaciones del prop drilling
			        - Uso de Context API (createContext, useContext)
			        - Proveedores de estado
			        - Persistencia de datos en localStorage
			        - Optimización de renderizados."""
			, TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
			}
			if (moduloRepo.findByTituloContainingIgnoreCase("React. Ejercicio Práctico para el Alumno")== null) { moduloRepo.save(new Modulo("React. Ejercicio Práctico para el Alumno", "Entrega final", 
					"""
				    E-commerce Completo.
			        --------------------
			        - Integración de todas las entregas anteriores en una tienda virtual con catálogo (API), carrito de compras gestionado de forma global y pasarela de pago simulada."""
      			    , TipoModulo.ENTREGA_OBLIGATORIA, LocalDateTime.now(), "CARGAINICIAL")); 
			}

			if (moduloRepo.findByTituloContainingIgnoreCase("Documentación (Swagger/OpenAPI)") == null) { moduloRepo.save(new Modulo("Documentación (Swagger/OpenAPI)", "Documentación (Swagger/OpenAPI)", null, TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); }
			
			if (moduloRepo.findByTituloContainingIgnoreCase("Objetivo del Proyecto API REST") == null) { moduloRepo.save(new Modulo("Objetivo del Proyecto API REST", "Objetivo del Proyecto", 
					"""
			
			Desarrollar una API REST robusta y segura para la gestión de recursos (ej. "Equipos de Laboratorio" o "Vehículos de Flota") que cumpla con los siguientes requisitos:
			
			•	Arquitectura Limpia: Separación estricta entre Controladores, Servicios y Repositorios.
			•	Manejo de Errores Global: Implementación de un @ControllerAdvice que estandarice las respuestas de error (códigos HTTP, mensajes en varios idiomas y detalles técnicos).
			•	Validación Rigurosa: Uso de @Valid y BindingResult para validar datos de entrada.
			•	Documentación Automática: Generación de documentación Javadoc y Swagger/OpenAPI.
			•	Auditoría: Registro de quién y cuándo creó o modificó un recurso.
			•	Despliegue: Preparación para empaquetar como WAR y desplegar en un contenedor Docker.
			"""
			,TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
			}
			if (moduloRepo.findByTituloContainingIgnoreCase("Estructura proyecto API REST (MAVEN)") == null) { 
				moduloRepo.save(new Modulo("Estructura proyecto API REST (MAVEN)", "Estructura proyecto", 
						"""
						Se establece una estructura de proyecto, estándar para esta tipología:

			com.empresa.api
			├── config
			│   ├── SwaggerConfig.java          # Configuración de OpenAPI
			│   └── SecurityConfig.java         # Configuración básica de seguridad
			├── controller
			│   └── ResourceController.java     # Endpoints REST
			├── dto
			│   ├── ResourceDTO.java            # DTO para entrada/salida
			│   └── ErrorResponse.java          # DTO para respuestas de error estandarizadas
			├── exception
			│   ├── GlobalExceptionHandler.java # @ControllerAdvice
			│   └── ResourceNotFoundException.java
			├── model
			│   └── Resource.java               # Entidad JPA
			├── repository
			│   └── ResourceRepository.java
			├── service
			│   ├── ResourceService.java
			│   └── ResourceServiceImpl.java
			└── util
			    └── AuditUtils.java             # Utilidades para auditoría
			""", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Características Avanzadas a Implementar") == null) { 
				moduloRepo.save(new Modulo("Características Avanzadas a Implementar", "Características Avanzadas a Implementar", 
						"""
			1.	Auditoría de Datos:
				•	Usar @CreatedDate y @LastModifiedDate de Spring Data JPA en la entidad Resource.
				•	Crear un @PrePersist y @PreUpdate para capturar el usuario actual (desde el contexto de seguridad o sesión) y guardarlo en campos createdBy y updatedBy.
			2.	Internacionalización (i18n):
				•	Configurar MessageSource para que los mensajes de error (ej: "El nombre es obligatorio") se muestren en Español, Inglés o Francés según el header Accept-Language de la petición.
			3.	Seguridad Básica:
				•	Implementar un filtro simple o usar Spring Security para requerir un token JWT o autenticación básica para acceder a los endpoints POST, PUT y DELETE.
			4.	Despliegue en Docker:
				•	Crear un Dockerfile que use una imagen base de OpenJDK y Tomcat.
				•	Empaquetar la aplicación como un archivo .war (excluyendo el Tomcat embebido) para desplegarlo en un Tomcat externo dentro del contenedor.
			"""
						, TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
			}
			if (moduloRepo.findByTituloContainingIgnoreCase("Criterios de Éxito (Checklist)") == null) { 
				moduloRepo.save(new Modulo("Criterios de Éxito (Checklist)", "Criterios de Éito (Checklist)", 
						"""
				•	  La API devuelve respuestas JSON consistentes en caso de error (código, mensaje, timestamp).
			    •	  Los errores de validación se muestran claramente en el JSON de respuesta.
			    •	  La documentación Swagger se genera automáticamente y es accesible.
			    •	  Los campos de auditoría (createdBy, createdDate) se rellenan automáticamente.
			    •	  La aplicación se puede desplegar en un contenedor Docker y se conecta a una base de datos externa.
			    •	  El código está documentado con Javadoc y se genera la documentación HTML
			""", 
			TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Operativa de la plataforma") == null) { 
				moduloRepo.save(new Modulo("Operativa de la plataforma", "Operativa", 
						"""
			En cada curso se mostrarán los diferentes apartados seleccionados por nuestos especialistas.
			
			Podrá avanzar en el curso finalizando cada apartado o capítulo de dos formas
			1.- Seleccionando la finalización del mismo. (como este)
			2.- Adjuntando una entrega en formato PDF que será evaluado por el responsable asignado.
			
			Una vez evaluado, en su caso se le asignará una calificación (1-10) que podrá ser consultada en todo momento por Vd.
			Le deseamos la mejor experiencia.""", 
			TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Ejercicio Práctico para el Alumno API-REST") == null) { 
				moduloRepo.save(new Modulo("Ejercicio Práctico para el Alumno API-REST", "Ejercicio Práctico para el Alumno", 
						"""
				Desafío:
			
			
			1.	Crea la API desde cero siguiendo la estructura.
			2.	Introduce un error intencional en el Service (ej: lanzar una ResourceNotFoundException si el ID no existe).
			3.	Verifica que el @ControllerAdvice capture la excepción y devuelva un JSON con el código 404 y un mensaje amigable.
			4.	Envía una petición POST con datos inválidos (ej: nombre vacío) y verifica que el Advice capture el MethodArgumentNotValidException y devuelva los detalles de los campos erróneos.
			5.	Genera la documentación Javadoc y Swagger y verifica que los endpoints estén listados correctamente.
			6. Genera un PDF con la estructura básica del proyecto y con las pruebas realizadas y su resultado""", 
			TipoModulo.ENTREGA_OBLIGATORIA, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Introducción al curso") == null) { 
				moduloRepo.save(new Modulo("Introducción al curso", "Bienvenida", 
						"""
			Le damos la bienvenida a este curso en la que mediante los apartados que le presentamos adquirirá un magnífico conocimiento sobre lo indicado
			
			Podrá navegar por los diferentes apartados e ir avanzando de acuerdo a las características del mismo. """, TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Introducción a React y Componentes") == null) { 
				moduloRepo.save(new Modulo("Introducción a React y Componentes", "Configuración del entorno, JSX y creación de componentes funcionales.", 
						"""
				Configuración del entorno con Vite, sintaxis JSX, componentes funcionales, renderizado condicional, manejo de eventos básicos y uso de fragmentos (<></>).
						""", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
						
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("React Props y Estado (State)") == null) { 
				moduloRepo.save(new Modulo("React Props y Estado (State)", "Pasando datos entre componentes y manejo de estados locales con useState.", 
						"""
				Comunicación entre componentes (padre a hijo) mediante props, validación con PropTypes, desestructuración, introducción al hook useState y actualización asíncrona del estado.
			

			Crear un lista de tareas (Todo App) interactiva donde se puedan añadir más elementos.
			""", TipoModulo.ENTREGA_OBLIGATORIA, LocalDateTime.now(), "CARGAINICIAL")); }
			if (moduloRepo.findByTituloContainingIgnoreCase("React Efectos y Ciclo de Vida") == null) { 
				moduloRepo.save(new Modulo("React Efectos y Ciclo de Vida", "Sincronización con APIs externas y uso avanzado del hook useEffect", 
						"""
			- Ciclo de vida de los componentes.
			- Efectos secundarios con el hook useEffect.
			- Consumo de APIs REST usando fetch o axios.
			- Manejo de estados de carga (loading) y errores.
			- Limpieza de efectos (cleanup functions).
			""", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); }
			if (moduloRepo.findByTituloContainingIgnoreCase("Enrutamiento con React Router") == null) { 
				moduloRepo.save(new Modulo("Enrutamiento con React Router", "Creación de aplicaciones de múltiples páginas y navegación interna.", 
						"""
				Instalación y configuración de react-router-dom, definición de rutas estáticas y dinámicas (/productos/:id), uso de enlaces de navegación (Link, NavLink) y redirecciones programáticas con useNavigate.
						""", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("React. Gestión de Estado") == null) { 
				moduloRepo.save(new Modulo("React. Gestión de Estado", "Compartir información en toda la app usando Context API o Redux Toolkit.", 
						"""
			- Limitaciones del prop drilling
			- Uso de Context API (createContext, useContext)
			- Proveedores de estado
			- Persistencia de datos en localStorage
			- Optimización de renderizados.
			""", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("React. Ejercicio Práctico para el Alumno") == null) { 
				moduloRepo.save(new Modulo("React. Ejercicio Práctico para el Alumno", "Entrega final", 
						"""
				E-commerce Completo.
				--------------------
			Integración de todas las entregas anteriores en una tienda virtual con catálogo (API), carrito de compras gestionado de forma global y pasarela de pago simulada.

			""", TipoModulo.ENTREGA_OBLIGATORIA, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Controlador REST (ResourceController.java)") == null) { 
				moduloRepo.save(new Modulo("Controlador REST (ResourceController.java)", "Controlador REST (ResourceController.java)", 
						"""
				En Spring Boot 3, un Controlador REST es una clase que maneja solicitudes HTTP y produce respuestas en formatos como JSON o XML. Para definir un controlador REST, se utiliza la anotación @RestController, que es una especialización de @Controller y @ResponseBody. Esta anotación indica a Spring que los métodos de la clase producirán respuestas directamente en el cuerpo de la respuesta HTTP, en lugar de renderizar vistas.
			
			Un ejemplo básico de un controlador REST, incluyendo las anotaciones básicas a utilizar:

			package com.ejemplo.demo.controlador;

			import org.springframework.web.bind.annotation.RestController;
			import org.springframework.web.bind.annotation.GetMapping;

			@RestController
			public class SaludoControlador {

			    @GetMapping("/saludo")
			    public String saludar() {
			        return "¡Hola, mundo!";
			    }
			}""", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); }
			if (moduloRepo.findByTituloContainingIgnoreCase("Manejo Global de Excepciones (GlobalExceptionHandler.java)") == null) { 
				moduloRepo.save(new Modulo("Manejo Global de Excepciones (GlobalExceptionHandler.java)", "Manejo Global de Excepciones (GlobalExceptionHandler.java)", 
						"""
				¿Qué es @ControllerAdvice?
			
			@ControllerAdvice es una anotación de Spring que nos ayuda a capturar y procesar excepciones desde un solo punto, evitando repetir código en cada controlador.

			¿Por qué usar @ControllerAdvice?
			- Centraliza la gestión de errores.
			- Separa responsabilidades (SoC – Separation of Concerns).
			- Hace el código más limpio y fácil de mantener.
			- Permite personalizar las respuestas de error de manera uniforme.

			Implementación de @ControllerAdvice en Spring Boot
			Veamos cómo usar @ControllerAdvice para manejar excepciones de forma global en una app Spring Boot.

			Paso 1: Crear una excepción personalizada
			Para capturar errores específicos, definimos una excepción propia:


			public class RecursoNoEncontradoException extends RuntimeException {
			    public RecursoNoEncontradoException(String mensaje) {
			        super(mensaje);
			    }
			}

			Paso 2: Crear un manejador global de excepciones
			Ahora, creamos una clase con @ControllerAdvice para capturar y responder a las excepciones:


			import org.springframework.http.HttpStatus;
			import org.springframework.http.ResponseEntity;
			import org.springframework.web.bind.annotation.ControllerAdvice;
			import org.springframework.web.bind.annotation.ExceptionHandler;

			@ControllerAdvice
			public class ManejadorGlobalExcepciones {
			    
			    @ExceptionHandler(RecursoNoEncontradoException.class)
			    public ResponseEntity<String> manejarRecursoNoEncontrado(RecursoNoEncontradoException ex) {
			        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
			    }
			    
			    @ExceptionHandler(Exception.class)
			    public ResponseEntity<String> manejarExcepcionGeneral(Exception ex) {
			        return new ResponseEntity<>("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
			    }
			}

			¿Qué hace este código?
			@ControllerAdvice: Indica que esta clase manejará errores de forma global.
			@ExceptionHandler(RecursoNoEncontradoException.class): Captura y maneja específicamente la excepción RecursoNoEncontradoException, devolviendo un código HTTP 404.
			@ExceptionHandler(Exception.class): Captura cualquier otra excepción no controlada y devuelve un código HTTP 500.

			Paso 3: Lanzar la excepción en un controlador
			Ahora, en un controlador podemos lanzar la excepción para que se maneje globalmente:

			import org.springframework.web.bind.annotation.GetMapping;
			import org.springframework.web.bind.annotation.RequestParam;
			import org.springframework.web.bind.annotation.RestController;

			@RestController
			public class ProductoController {
			    
			    @GetMapping("/producto")
			    public String obtenerProducto(@RequestParam(required = false) String id) {
			        if (id == null) {
			            throw new RecursoNoEncontradoException("El producto no fue encontrado");
			        }
			        return "Producto encontrado";
			    }
			}

			Si alguien intenta acceder a /producto sin proporcionar un id, la excepción RecursoNoEncontradoException será capturada por ManejadorGlobalExcepciones, devolviendo un error 404 con el mensaje correspondiente.

			Conclusión
			@ControllerAdvice en Spring Boot nos permite gestionar excepciones de manera eficiente y centralizada. Con esta técnica, podemos dar respuestas más claras y personalizadas, hacer el código más mantenible y seguir buenas prácticas en el desarrollo de aplicaciones.

			""", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("DTO y Validación (ResourceDTO.java)") == null) { moduloRepo.save(new Modulo("DTO y Validación (ResourceDTO.java)", "DTO y Validación (ResourceDTO.java)", 
					"""
			
			El uso de DTO o Data Transfer Object es uno de los conceptos más habituales a nivel de Arquitectura cuando devolvemos en nuestros servicios estructuras de datos . Muchos servicios devuelven objetos de negocio o gráfos con objetos de negocio relacionados . Es decir cuando nosotros tenemos un método que nos devuelve información sobre Personas lo más sencillo es devolver por ejemplo una lista de Personas desde un servicio.
			

			Vamos a ver un ejemplo con la clase Persona:

			public class Persona {

			  private String nombre;
			  private String apellidos;
			  private int edad;
			  
			  public String getNombre() {
			    return nombre;
			  }
			  public void setNombre(String nombre) {
			    this.nombre = nombre;
			  }
			  public String getApellidos() {
			    return apellidos;
			  }
			  public void setApellidos(String apellidos) {
			    this.apellidos = apellidos;
			  }
			  public int getEdad() {
			    return edad;
			  }
			  public void setEdad(int edad) {
			    this.edad = edad;
			  }
			  public Persona(String nombre, String apellidos, int edad) {
			    super();
			    this.nombre = nombre;
			    this.apellidos = apellidos;
			    this.edad = edad;
			  }
			}

			""", TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
			}
			if (moduloRepo.findByTituloContainingIgnoreCase("Módulo 3: Contenedores y Serverless (Computación Moderna)") == null) { 
				moduloRepo.save(new Modulo("Módulo 3: Contenedores y Serverless (Computación Moderna)", "Curso: Arquitectura y Despliegue en Google Cloud. Mod-3", 
						"""
				
				Contenerización: Introducción a Docker y empaquetado de aplicaciones en Artifact Registry.
			

			Orquestación: Fundamentos de Kubernetes y despliegue de microservicios en Google Kubernetes Engine (GKE).

			Serverless: Ejecución de servicios basados en eventos con Cloud Run y Cloud Functions.

			Laboratorio: Migrar la aplicación web del Módulo 1 a un contenedor Docker y desplegarla en Cloud Run.""", 
			TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); }
			if (moduloRepo.findByTituloContainingIgnoreCase("Módulo 4: Monitorización, Seguridad y DevOps") == null) { 
				moduloRepo.save(new Modulo("Módulo 4: Monitorización, Seguridad y DevOps", "Curso: Arquitectura y Despliegue en Google Cloud Mod-4", 
						"""
			Observabilidad: Configuración de logs, métricas y alertas utilizando Google Cloud Observability (antiguo Stackdriver).
			
			Automatización: Introducción a la Infraestructura como Código (IaC) en GCP mediante Deployment Manager o Terraform.

			Seguridad avanzada: Gestión de secretos con Secret Manager y protección de red con Cloud Armor.

			Laboratorio: Crear un cuadro de mando (Dashboard) para monitorizar el tráfico y el consumo de recursos de los servicios desplegados.""", 
			TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Módulo 1: Fundamentos e Infraestructura Global") == null) { 
				moduloRepo.save(new Modulo("Módulo 1: Fundamentos e Infraestructura Global", "Curso: Arquitectura y Despliegue en Google Cloud Mod-1", 
						"""
			Conceptos clave: Regiones, zonas de disponibilidad y el modelo de responsabilidad compartida de GCP.
			
			Gestión de accesos: Configuración de proyectos, jerarquía de recursos e identidades con Cloud IAM.

			Computación básica: Creación, administración y escalado de máquinas virtuales en Compute Engine.

			Laboratorio: Despliegue de una aplicación web básica sobre una instancia Linux de Compute Engine.""", 
			TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Módulo 2: Redes y Almacenamiento en la Nube") == null) { 
				moduloRepo.save(new Modulo("Módulo 2: Redes y Almacenamiento en la Nube", "Curso: Arquitectura y Despliegue en Google Cloud Mod-2", 
						"""
			Networking: Diseño de redes virtuales (VPC), subredes, reglas de firewall y asignación de IPs.
			
			Almacenamiento de objetos: Ciclos de vida y clases de almacenamiento en Cloud Storage.

			Bases de datos: Diferencias y configuración de Cloud SQL (relacional) y Cloud Firestore (NoSQL).

			Laboratorio: Conectar de forma segura un servidor web a una base de datos gestionada en Cloud SQL.""", 
			TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Módulo 5: Proyecto Fin de Curso (Arquitectura Multicapa Resiliente)") == null) { 
				moduloRepo.save(new Modulo("Módulo 5: Proyecto Fin de Curso (Arquitectura Multicapa Resiliente)", " Curso: Arquitectura y Despliegue en Google Cloud. Mod-5", 
						"""
			Objetivo: Integrar todos los conocimientos adquiridos para diseñar, desplegar y asegurar una infraestructura profesional.
				Requisitos del trabajo final:
			    Arquitectura detrás de un Cloud Load Balancing (Balanceador de carga).
			    Uso de bases de datos Cloud SQL aisladas en subredes privadas.
			    Uso de Secret Manager para almacenar las credenciales de la base de datos de forma segura (sin texto plano).
			    Escalado automático (Autoscaling) configurado ante picos de tráfico.

			Entregables: Diagrama de arquitectura del sistema, código fuente (o scripts de despliegue) y una breve memoria técnica justificando las decisiones de diseño.""", 
			TipoModulo.ENTREGA_OBLIGATORIA, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Módulo 1: Fundamentos del Modelo TCP/IP y Direccionamiento") == null) { 
				moduloRepo.save(new Modulo("Módulo 1: Fundamentos del Modelo TCP/IP y Direccionamiento", "Curso: TCP/IP, Routing, Switching y Configuración Básica MOD-1", 
						"""
			Conceptos clave: Comparativa entre el Modelo OSI y el Modelo TCP/IP (Capas de aplicación, transporte, red y acceso al medio).
			
			Direccionamiento IPv4: Estructura de una dirección IP, máscaras de red, cálculo de subredes (Subnetting) y direccionamiento público vs. privado.

			Protocolos esenciales: Funcionamiento práctico de ARP, ICMP (Ping/Traceroute) y asignación dinámica mediante DHCP.

			Laboratorio de prueba: Diseño de un esquema de direccionamiento IPv4 para tres departamentos utilizando software de simulación (ej. Packet Tracer).""", 
			TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Módulo 2: Configuración Básica de Dispositivos de Red (Cisco/Generic)") == null) { 
				moduloRepo.save(new Modulo("Módulo 2: Configuración Básica de Dispositivos de Red (Cisco/Generic)", "Curso: TCP/IP, Routing, Switching y Configuración Básica MOD-2", 
						"""
				Interfaz de línea de comandos (CLI): Modos de operación (Usuario, Privilegiado, Configuración Global).
			

			Seguridad inicial: Configuración de nombres de host, contraseñas de acceso (Console, Enable, VTY/SSH) y banners de advertencia.

			Gestión de archivos: Administración de la memoria del dispositivo (Running-config, Startup-config y copias de seguridad de IOS).

			Laboratorio de prueba: Inicialización y securización desde cero de un Router y un Switch de forma local.""", 
			TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Módulo 3: Tecnologías de Capa 2 (Switching y VLANs)") == null) { 
				moduloRepo.save(new Modulo("Módulo 3: Tecnologías de Capa 2 (Switching y VLANs)", "Curso: TCP/IP, Routing, Switching y Configuración Básica MOD-3", 
						"""
			Operación del Switch: Funcionamiento y construcción automática de la tabla de direcciones MAC.
			
			Segmentación de red: Creación, diseño y asignación de puertos en Redes Locales Virtuales (VLANs).

			Enlaces troncales: Configuración de enlaces troncales mediante el protocolo 802.1Q y etiquetado de tramas.

			Laboratorio de prueba: Implementación de VLANs de Voz y Datos en un switch para aislar el tráfico de una oficina.""", 
			TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Módulo 4: Enrutamiento IP (Routing Estático y Dinámico)") == null) { 
				moduloRepo.save(new Modulo("Módulo 4: Enrutamiento IP (Routing Estático y Dinámico)", "Curso: TCP/IP, Routing, Switching y Configuración Básica MOD-4", 
						"""
				Conceptos de Routing: Funcionamiento de la tabla de enrutamiento y el proceso de reenvío de paquetes.
			

			Enrutamiento Inter-VLAN: Configuración de routing entre VLANs utilizando la técnica Router-on-a-Stick (Subinterfaces).

			Rutas Estáticas: Configuración de rutas estáticas de red, de host y rutas por defecto (Gateway de último recurso).

			Introducción al Routing Dinámico: Conceptos básicos de protocolos de estado de enlace (como OSPF básico de área única).

			Laboratorio de prueba: Interconexión de dos sedes geográficas distintas mediante rutas estáticas flotantes para tolerancia a fallos.""", 
			TipoModulo.FINALIZACION_MANUAL, LocalDateTime.now(), "CARGAINICIAL")); 
				}
			if (moduloRepo.findByTituloContainingIgnoreCase("Módulo 5: Trabajo Final (Diseño e Implementación de una Red Corporativa)") == null) { 
				moduloRepo.save(new Modulo("Módulo 5: Trabajo Final (Diseño e Implementación de una Red Corporativa)", "Curso: TCP/IP, Routing, Switching y Configuración Básica MOD-4", 
						"""
				Objetivo: Integrar los conocimientos de direccionamiento, conmutación y enrutamiento para desplegar la red de una pequeña empresa simulación.
			

			Requisitos del trabajo final:
			           - Diseño de un esquema de subredes eficiente para 4 departamentos diferentes.
			           - Configuración de switches con VLANs separadas y enlaces troncales seguros.
			           - Implementación de un router centralizado que proporcione Routing Inter-VLAN y asignación automática por DHCP a los clientes.
			           - Habilitación de acceso seguro remoto por SSH en todos los equipos de la topología.

			Entregables: Archivo de simulación de red (.pkt o similar) con la topología operativa, una tabla oficial con el direccionamiento IP de la red y un documento con los comandos CLI utilizados para la auditoría.""", 
			TipoModulo.ENTREGA_OBLIGATORIA, LocalDateTime.now(), "CARGAINICIAL")); 
				}


			// generación de cursomodulo para datos iniciales.
			// CursoModulo(Curso curso, Modulo modulo, Integer orden, Integer peso)
			
			/*
			select concat(   'Curso c1 = cursoRepo.findByTitulo("', c.titulo, '").get();',
					   'c1.addModulo(moduloRepo.findByTituloContainingIgnoreCase("',m.titulo,'"),', orden,',', peso,');'
					) java
					from cursos_modulos cm 
					inner join cursos c on c.curso_id = cm.curso_id
					inner join modulos m on m.id = cm.modulo_id order by cm.curso_id, orden;
			*/
			Curso c1 = cursoRepo.findByTitulo("React Moderno").get();
			if ((c1 != null) && (c1.getModulos().size() == 0)){
				c1.addModulo(moduloRepo.findByTituloContainingIgnoreCase("Introducción al Curso"),1,10);
				c1.addModulo(moduloRepo.findByTituloContainingIgnoreCase("Introducción a React y Componentes"),2,10);
				c1.addModulo(moduloRepo.findByTituloContainingIgnoreCase("React Props y Estado (State)"),3,10);
				c1.addModulo(moduloRepo.findByTituloContainingIgnoreCase("React Efectos y Ciclo de Vida"),4,10);
				c1.addModulo(moduloRepo.findByTituloContainingIgnoreCase("Enrutamiento con React Router"),5,10);
				c1.addModulo(moduloRepo.findByTituloContainingIgnoreCase("React. Gestión de Estado"),6,10);
				c1.addModulo(moduloRepo.findByTituloContainingIgnoreCase("React. Ejercicio Práctico para el Alumno"),7,40);
				cursoRepo.save(c1);

			}
			c1 = cursoRepo.findByTitulo("Creatividad Práctica").get();
			if ((c1 != null) && (c1.getModulos().size() == 0)){
				c1.addModulo(moduloRepo.findByTituloContainingIgnoreCase("Introducción al Curso"),1,10);
				c1.addModulo(moduloRepo.findByTituloContainingIgnoreCase("Despedida"),2,10);
				cursoRepo.save(c1);
			}

			System.out.println("--------------------------------------------");
			System.out.println("----------FIN DE CARGA DE DATOS-------------");
			System.out.println("--------------------------------------------");
    }
   }
}
