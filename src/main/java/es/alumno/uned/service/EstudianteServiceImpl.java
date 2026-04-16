package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.alumno.uned.dto.PerfilEstudianteDTO;
import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.EstudianteRepository;

@Service
public class EstudianteServiceImpl implements EstudianteService {

	private UsuarioService usuarioService;
	
	@Autowired
	private EstudianteRepository estudianteRepository;
	
	@Override
	public void guardar(PerfilEstudianteDTO estudianteDTO, String usuarioAlta) {
		
		/* 
		 * 
		 * Antes de guardar la información deberemos comprobar si el Estudiante 
		 * es nuevo o ya está registrado.
		 * 
		 * Puede venir de "registro", "miPerfil", "/estudiante/cambiopaswd"
		 *  o desde "admin/Estudiantes"
		 *  
		 * CASOS
		 * ----- 
		 * - REGISTRO
		 * 		El idEstudiante debe de ser nulo y el email NOEXISTENTE
		 * 		(Se dá de alta el usuario y la información del Estudiante),
		 * 		en caso contrario se debe de generar un error.
		 * 
		 * - MIPERFIL
		 *      El idEstudiante no es nulo.
		 *      Se recupera Usuario y Estudiante
		 *      Si el email no existe se modifica el usuario
		 *      Se modifican los datos, excepto la password.
		 *      
		 * - CambioPassw
		 *      Se encripta la nueva password y se salva el usuario.
		 * 
		 * 
		 * Si viene con un Id, es una modificación
		 * 
		 * Cargamos su usuario (por el id)
		 * 
		 * 
		 * Siempre hay que buscar el email para evitar duplicidades.
		 * 
		 * Si se encuentra 
		 * 	Si el "id" del estudiante es el mismo, se modifica.
		 *  Si "id" del estudiante es otro, no se permite.
		 *   * Buscar usuario por su e-mail
		 Si existe updatear Usuario
		Usuario user = usuarioService.findByEmail(estudianteDTO.getEmail());
		*/ 
		Long userId = estudianteDTO.getId(); 
		Estudiante estudiante = new Estudiante();
		if ( userId == null) {
			estudiante = estudianteRepository.getReferenceById(userId);
		}
		Usuario user = usuarioService.findByEmail(estudianteDTO.getEmail());
		if (user != null) {
			
		}
		// 1. Crear usuario
        UsuarioRegistroDTO usuario = new UsuarioRegistroDTO();
        usuario.setNombre(estudianteDTO.getNombre());
        usuario.setApellido1(estudianteDTO.getApellido1());
        usuario.setApellido2(estudianteDTO.getApellido2());
        usuario.setEmail(estudianteDTO.getEmail());
        usuario.setNewPassword(estudianteDTO.getNewPassword());

        // Guardar datos del usuario 
        //usuario = UsuarioRegistroMapper.toDTO(usuarioService.guardar(usuario));

        // 2. Crear estudiante asociado
        estudiante.setId(usuario.getId()); // MapsId
        // TODO para persistir el estudiante hay que ponerle el usuario asociado (o no), probarlo ya que previamente
        // se ha tenido que realizar esa comprobación.
        
        //estudiante.setUsuario(usuario);
        estudiante.setDireccion(estudianteDTO.getDireccion());
        estudiante.setPoblacion(estudianteDTO.getPoblacion());
        estudiante.setProvincia(estudianteDTO.getProvincia());
        estudiante.setCodPostal(estudianteDTO.getCodPostal());

        // Habrá que salvar el estudiante con su repositorio
		estudianteRepository.save(estudiante);
	}

	@Override
	public Estudiante findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable Object findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
