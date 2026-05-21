package es.alumno.uned.service;

import java.util.Map;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.alumno.uned.dto.EstudianteDTO;
import es.alumno.uned.exception.EstudianteAlreadyExistException;
import es.alumno.uned.mapper.EstudianteMapper;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.records.PageParams;
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
			throw new EstudianteAlreadyExistException("usuario.existente.exception",
					estudianteDTO, estudianteDTO.getEmail());
		}
		if (usuarioService.findByEmail(estudianteDTO.getEmail()) != null) {
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
	public Paginacion<Estudiante, EstudianteDTO> listadoPaginado(Map<String, String> params, PageParams pageData) {
		
		
		return new Paginacion.Builder<Estudiante, EstudianteDTO>()
				.pagina(getPaginaBusqueda(PageRequest.of(pageData.page(), pageData.size()), params))
                .mapper(estudianteMapper::toDTO)
                .build();

	}

   /**
     * Devolvemos una búsqueda de acuerdo a los datos enviados por el filtro.
     * 
     * Dependendiendo de la existencia o no de los mismos llamamos a diferenentens métodos de nuestro repositorio.
     * 
     * 
     * @param pageable Definición de la {@code Page} a devolver.
     * @param params Mapa de Parámetros del formulario de búsqueda
     * @return Página de datos obtenida con los datos solicitados.
     */
	private Page<Estudiante> getPaginaBusqueda(Pageable pageable, Map<String, String> params){

		if ((params.containsKey("nombre")) && (params.containsKey("email"))) {
			return estudianteRepository.findByUsuarioEmailContainingIgnoreCaseAndUsuarioNombreContainingIgnoreCase(params.get("nombre"), params.get("email"), pageable);
		}
		if (params.containsKey("nombre")){
			return estudianteRepository.findByUsuarioNombreContainingIgnoreCase(params.get("nombre"), pageable);
		}
		if (params.containsKey("email")){
			return estudianteRepository.findByUsuarioEmailContainingIgnoreCase(params.get("email"), pageable);
		}
		return estudianteRepository.findAll(pageable);
	}

}
