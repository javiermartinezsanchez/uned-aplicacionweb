package es.alumno.uned.service;

import org.jspecify.annotations.Nullable;

import es.alumno.uned.dto.RegistroEstudianteDTO;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.Usuario;

public class EstudianteServiceImpl implements EstudianteService {

	@Override
	public void guardar(RegistroEstudianteDTO estudianteDTO) {
        // 1. Crear usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(estudianteDTO.getNombre());
        usuario.setApellido1(estudianteDTO.getApellido1());
        usuario.setApellido2(estudianteDTO.getApellido2());
        usuario.setEmail(estudianteDTO.getEmail());
        usuario.setPassword(estudianteDTO.getPassword());

        usuario = usuarioService.guardar(usuario);

        // 2. Crear estudiante asociado
        Estudiante estudiante = new Estudiante();
        estudiante.setId(usuario.getId()); // MapsId
        estudiante.setUsuario(usuario);
        estudiante.setDireccion(estudianteDTO.getDireccion());
        estudiante.setPoblacion(estudianteDTO.getPoblacion());
        estudiante.setProvincia(estudianteDTO.getProvincia());
        estudiante.setCodPostal(estudianteDTO.getCodPostal());

		
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
