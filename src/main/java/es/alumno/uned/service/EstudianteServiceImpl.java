package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.exception.UserAlreadyExistException;
import es.alumno.uned.mapper.EstudianteMapper;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.repository.EstudianteRepository;


@Service
public class EstudianteServiceImpl implements EstudianteService {

	private UsuarioService usuarioService;
	
	public EstudianteServiceImpl(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	@Autowired
	private EstudianteRepository estudianteRepository;
	
	@Autowired
	private EstudianteMapper estudianteMapper;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void grabar(EstudianteDTO estudianteDTO, String usuarioAlta) {
		/*
		 * Si viene con un Id, es una modificación
		 * 
		 * Cargamos el estudiante que traerá su usuario (por el id)
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
		Estudiante estudiante = estudianteMapper.toEntity(estudianteDTO, usuarioAlta);
		/**
		 * Sólo se genera la password cuando es nuevo.
		 * 
		 * Para modificar la password se generará un método específico.
		 * 
		 */
		if (estudianteDTO.getId() == null) {
			estudiante.getUsuario().setPassword(usuarioService.getEncriptedPass(estudianteDTO.getNewPassword()));
		}
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
