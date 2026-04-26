package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.exception.UserAlreadyExistException;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.EstudianteRepository;

@Service
public class EstudianteServiceImpl implements EstudianteService {

	private UsuarioService usuarioService;
	
	@Autowired
	private EstudianteRepository estudianteRepository;
	
	@Override
	public void guardar(EstudianteDTO estudianteDTO, String usuarioAlta) {
		
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
		 * 		en caso contrario se genera una excepción.
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
		Estudiante estudiante = new Estudiante();
		if (estudianteDTO.getId() == null) {
			findByUsuarioEmail(estudianteDTO.getEmail());
		}
		else {
			estudiante = estudianteRepository.getReferenceById(estudianteDTO.getId());
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
	public EstudianteDTO findById(Long id) {
		// TODO añadir la búsqueda y el mapper 
		return null;
	}

	@Override
	public @Nullable Object findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Método privado para la búsqueda de un estudiante mediante su e-mail.
	 * 
	 * Si lo encuentra nos lanza la excepción {@code UserAlreadyExistException}
	 * @param email Email del estudiante a buscar.
	 */
	private void findByUsuarioEmail(String email){
		
		var estudiantes = estudianteRepository.findByUsuarioEmail(email);
		if (estudiantes.isPresent()) {
			throw new UserAlreadyExistException("{usuario.existente.exception}");
		}
	}	
}
