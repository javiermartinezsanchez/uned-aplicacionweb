package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.alumno.uned.dto.RegistroEstudianteDTO;
import es.alumno.uned.dto.UsuarioRegistroDTO;
import es.alumno.uned.mapper.UsuarioRegistroMapper;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.repository.EstudianteRepository;
@Service
public class EstudianteServiceImpl implements EstudianteService {

	private UsuarioService usuarioService;
	
	@Autowired
	private EstudianteRepository estudianteRepository;
	
	@Override
	public void guardar(RegistroEstudianteDTO estudianteDTO) {
		
		/*
		 * CASOS
		 * 
		 * Si viene con un Id, es una modificación
		 * Buscar usuario por su e-mail
		 Si existe updatear Usuario
		Usuario user = usuarioService.findByEmail(estudianteDTO.getEmail());
		*/ 
		Long userId = estudianteDTO.getId(); 
		if ( userId == null) {
			
		}
        // 1. Crear usuario
        UsuarioRegistroDTO usuario = new UsuarioRegistroDTO();
        usuario.setNombre(estudianteDTO.getNombre());
        usuario.setApellido1(estudianteDTO.getApellido1());
        usuario.setApellido2(estudianteDTO.getApellido2());
        usuario.setEmail(estudianteDTO.getEmail());
        usuario.setPassword(estudianteDTO.getNewPassword());

        // Guardar datos del usuario 
        //usuario = UsuarioRegistroMapper.toDTO(usuarioService.guardar(usuario));

        // 2. Crear estudiante asociado
        Estudiante estudiante = new Estudiante();
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
