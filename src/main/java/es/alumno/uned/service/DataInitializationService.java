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
