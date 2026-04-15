package es.alumno.uned.mapper;

import es.alumno.uned.dto.RegistroEstudianteDTO;
import es.alumno.uned.dto.PerfilEstudianteDTO;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.Usuario;

public class RegistroEstudianteMapper {

    // DTO → Usuario
    public static Usuario toUsuario(RegistroEstudianteDTO dto) {
        Usuario u = new Usuario();
        u.setNombre(dto.getNombre());
        u.setApellido1(dto.getApellido1());
        u.setApellido2(dto.getApellido2());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        return u;
    }

    // DTO → Estudiante
    public static Estudiante toEstudiante(RegistroEstudianteDTO dto, Usuario usuario) {
        Estudiante e = new Estudiante();
        e.setId(usuario.getId());
        e.setUsuario(usuario);
        e.setDireccion(dto.getDireccion());
        e.setPoblacion(dto.getPoblacion());
        e.setProvincia(dto.getProvincia());
        e.setCodPostal(dto.getCodPostal());
        return e;
    }

    // Usuario + Estudiante → PerfilEstudianteDTO
    public static PerfilEstudianteDTO toPerfilDTO(Usuario u, Estudiante e) {
        PerfilEstudianteDTO dto = new PerfilEstudianteDTO();
        dto.setNombre(u.getNombre());
        dto.setApellido1(u.getApellido1());
        dto.setApellido2(u.getApellido2());
        dto.setEmail(u.getEmail());
        dto.setDireccion(e.getDireccion());
        dto.setPoblacion(e.getPoblacion());
        dto.setProvincia(e.getProvincia());
        dto.setCodPostal(e.getCodPostal());
        return dto;
    }

    // PerfilEstudianteDTO → actualizar entidades
    public static void updateFromPerfilDTO(PerfilEstudianteDTO dto, Usuario u, Estudiante e) {
        u.setNombre(dto.getNombre());
        u.setApellido1(dto.getApellido1());
        u.setApellido2(dto.getApellido2());
        u.setEmail(dto.getEmail());

        e.setDireccion(dto.getDireccion());
        e.setPoblacion(dto.getPoblacion());
        e.setProvincia(dto.getProvincia());
        e.setCodPostal(dto.getCodPostal());
    }
}

