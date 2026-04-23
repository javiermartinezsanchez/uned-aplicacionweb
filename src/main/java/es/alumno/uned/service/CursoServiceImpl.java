package es.alumno.uned.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.mapper.CursoMapper;
import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.Usuario;
import es.alumno.uned.model.repository.AreaTematicaRepository;
import es.alumno.uned.model.repository.CursoRepository;
import es.alumno.uned.model.repository.UsuarioRepository;
import es.alumno.uned.model.util.Paginacion;

@Service
public class CursoServiceImpl implements CursoService{

	@Autowired
	CursoRepository cursoRepository;
	@Autowired
	AreaTematicaRepository areaTematicaRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	CursoMapper cursoMapper;
	@Override
	public void nuevoCurso(CursoDTO dto, MultipartFile imagen, String usuario) throws IOException {
	    Curso curso = (dto.getId() != null)
	            ? cursoRepository.findById(dto.getId()).orElse(new Curso())
	            : new Curso();

	    curso.setTitulo(dto.getTitulo());
	    curso.setDescripcion(dto.getDescripcion());
	    curso.setNivel(dto.getNivel());
	    curso.setfIni(dto.getfIni());
	    curso.setfFin(dto.getfFin());
	    if (curso.getfIns() == null) {
	    	curso.setfIns(LocalDateTime.now());
	    	curso.setUserIns(usuario);
	    }
	    // Área temática
	    AreaTematica area = areaTematicaRepository.findById(dto.getAreaTematicaId())
	            .orElseThrow();
	    curso.setAreaTematica(area);

	    // Responsable
	    Usuario responsable = usuarioRepository.findById(dto.getResponsableId())
	            .orElseThrow();
	    curso.setResponsable(responsable);

	    // Subida de imagen
	    if (imagen != null && !imagen.isEmpty()) {

	        String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();

	        Path ruta = Paths.get("src/main/resources/static/images/curso")
	                .resolve(nombreArchivo)
	                .toAbsolutePath();

	        Files.copy(imagen.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);

	        curso.setUriImagen(nombreArchivo);
	    }

	    cursoRepository.save(curso);

		
	}

	public Paginacion<Curso, CursoDTO> listadoPaginado(String url,  Pageable pageable){
		
		return new Paginacion<>(url, cursoRepository.findAll(pageable), cursoMapper::toDTO);
	}
}
