package es.alumno.uned.service;

import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.exception.AreaTematicaAlreadyExistException;
import es.alumno.uned.exception.EstudianteAlreadyExistException;
import es.alumno.uned.mapper.EstudianteMapper;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.repository.EstudianteRepository;
import es.alumno.uned.model.util.Paginacion;


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
			checkEmail(estudianteDTO);
			estudiante.getUsuario().setPassword(usuarioService.getEncriptedPass(estudianteDTO.getNewPassword()));
		}
        // Habrá que salvar el estudiante con su repositorio
		estudianteRepository.save(estudiante);
	}
	/**
	 * Comprobamos si el nuevo e-mail ya existe en Estudiantes o en Usuarios.
	 * <p>Si existe se genera una excepción
	 * 
	 * @param estudianteDTO Estudiante a comprobar.
	 */
	private void checkEmail(EstudianteDTO estudianteDTO) {
		if (estudianteRepository.findByUsuarioEmail(estudianteDTO.getEmail()).isPresent()) {
		//if (estudianteRepository.findByUsuarioEmail(estudianteDTO.getEmail()).isPresent()) {
			throw new EstudianteAlreadyExistException("usuario.existente.exception",
					estudianteDTO, estudianteDTO.getEmail());
		}
		if (usuarioService.getIdByEmail(estudianteDTO.getEmail()) != null) {
			throw new EstudianteAlreadyExistException("usuario.existente.exception",
					estudianteDTO, estudianteDTO.getEmail());
			
		}
		
	}

	@Override
	public EstudianteDTO findById(Long id) {
		return estudianteMapper.toDTO(estudianteRepository.getReferenceById(id));
	}

	@Override
	public @Nullable Object findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Paginacion<Estudiante, EstudianteDTO> listadoPaginado(String url, Pageable pageRequest) {
		
		return new Paginacion.Builder<Estudiante, EstudianteDTO>()
				.url(url)
				.pagina(estudianteRepository.findAll(pageRequest))
				.mapper(estudianteMapper :: toDTO)
				.build();
	}

	
}
