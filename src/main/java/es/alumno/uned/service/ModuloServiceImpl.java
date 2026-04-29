package es.alumno.uned.service;

import org.springframework.stereotype.Service;

import es.alumno.uned.dto.ModuloDTO;
import es.alumno.uned.exception.CursoNotExistException;
import es.alumno.uned.mapper.ModuloMapper;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.repository.CursoRepository;
import es.alumno.uned.model.repository.EstudianteRepository;
import es.alumno.uned.model.repository.ModuloRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ModuloServiceImpl implements ModuloService {

    private final ModuloRepository moduloRepository;
    private final CursoRepository cursoRepository;
    private final EstudianteRepository estudianteRepository;
    private final ModuloMapper moduloMapper;
    
    public ModuloServiceImpl(ModuloRepository moduloRepository,
            CursoRepository cursoRepository,
            EstudianteRepository estudianteRepository,
            ModuloMapper moduloMapper
    ) {
        this.moduloRepository = moduloRepository;
        this.cursoRepository = cursoRepository;
        this.estudianteRepository = estudianteRepository;
        this.moduloMapper = moduloMapper;
    }

	    // ---------------------------------------------------------
	    //  GRABAR (crear o actualizar)
	    // ---------------------------------------------------------
	    @Override
	    public ModuloDTO grabar(ModuloDTO dto) {

	        // Validación: el curso debe existir
	        Curso curso = cursoRepository.findById(dto.cursoId())
	                .orElseThrow(() -> new CursoNotExistException("curso.no.encontrado"));

	        Modulo modulo;

	        if (dto.id() == null) {
	            // CREAR
	            modulo = moduloMapper.toEntity(dto);
	            modulo.setCurso(curso);

	        } else {

	        }
            return null;	
	    }
}
