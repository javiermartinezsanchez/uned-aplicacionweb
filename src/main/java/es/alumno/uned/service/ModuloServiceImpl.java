package es.alumno.uned.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.alumno.uned.dto.ModuloDTO;
import es.alumno.uned.mapper.ModuloMapper;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.entities.TipoModulo;
import es.alumno.uned.model.repository.CursoRepository;
import es.alumno.uned.model.repository.EstudianteRepository;
import es.alumno.uned.model.repository.ModuloRepository;
import es.alumno.uned.model.util.Paginacion;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ModuloServiceImpl implements ModuloService {

    private final ModuloRepository moduloRepository;
    private final ModuloMapper moduloMapper;
    
    public ModuloServiceImpl(ModuloRepository moduloRepository,
            CursoRepository cursoRepository,
            EstudianteRepository estudianteRepository,
            ModuloMapper moduloMapper
    ) {
        this.moduloRepository = moduloRepository;
        this.moduloMapper = moduloMapper;
    }

	/**
	 * Método de grabación de un módulo.
	 * <p>Recibe el DTO del mismo, si no tiene id crea uno nuevo y le asigna el usuario y fecha de inserción.
	 * <p>Si lo guarda sus cambios.
	 * @param dto MóduloDTO a guardar
	 * @param userIns Nombre del usuario que añade el módulo
	 * 
	 * @return MóduloDTO nuevo o actualizado.   
	 */
    @Override
	public ModuloDTO grabar(ModuloDTO dto, String userIns) {

	        // Validación: el curso debe existir
//	        Curso curso = cursoRepository.findById(dto.cursoId())
//	                .orElseThrow(() -> new CursoNotExistException("curso.no.encontrado", dto, dto.cursoId().toString()));

	        Modulo modulo = moduloMapper.toEntity(dto);

	        if (dto.getId() == null) {
	            modulo.setUserIns(userIns);
	            modulo.setfIns(LocalDateTime.now());
	           // modulo.setCurso(curso);
	        }
            return moduloMapper.toDTO(moduloRepository.save(modulo));	
	    }

		@Override
		public Paginacion<Modulo, ModuloDTO> listadoPaginado(String url, Pageable pageable, Map<String,String> params) {
			return construirPaginacion(url,selectByParams( pageable, params) );
		}
		private Paginacion<Modulo, ModuloDTO> construirPaginacion(String url, Page<Modulo> page) {
	        return new Paginacion.Builder<Modulo, ModuloDTO>()
	                .url(url)
	                .pagina(page)
	                .mapper(moduloMapper::toDTO)
	                .build();
	    }

		/**
		 * Nos devuelve la {@link Page} de la consulta de acuerdo a los parámetros enviados.
		 * <p> Si el mapa de parámetros está vacio devuelve todos.
		 * @param pageable Definición de la página (tamaño y página)
		 * @param params Mapa de parámetros para definir la búsqueda de Módulos.
		 * @return La página que se determine de acuerdo a los parámetros enviados.
		 */
		private Page<Modulo> selectByParams(Pageable pageable, Map<String,String> params){
			if (params.containsKey("titulo") && params.containsKey("tipo")) {
				return moduloRepository.findByTituloContainingIgnoreCaseAndTipo(params.get("titulo"), TipoModulo.valueOf(params.get("tipo")), pageable);
			}
			if (params.containsKey("titulo")) {
				return moduloRepository.findByTituloContainingIgnoreCase(params.get("titulo"), pageable);
			}
			if (params.containsKey("tipo")) {
				return moduloRepository.findByTipo(TipoModulo.valueOf(params.get("tipo")), pageable);
			}
			return moduloRepository.findAll(pageable);
		}
		@Override
		public ModuloDTO get(Long idModulo) {
			
			return moduloMapper.toDTO(getValidModulo(idModulo));
		}
		/**
		 * Obtenemos un Modulo válido.
	     *
		 * <ul>
		 *     <li>Si el id es null -> Nuevo Modulo</li>
		 *     <li>
		 *         Si el id no es nulo:
		 *         <ul>
		 *             <li>Si se encuentra -> Modulo de la BD</li>
		 *             <li>Si no se encuentra -> Nuevo Modulo</li>
		 *         </ul>
		 *     </li>
		 * </ul>
		 * @param id Id del Modulo a buscar
		 * @return {@code Modulo}
		 */
		private Modulo getValidModulo(Long id) {
			Modulo entity = (id != null)
					? moduloRepository.findById(id).orElse(new Modulo())
					: new Modulo();
			return entity;
		}

		@Override
		public List<ModuloDTO> listAll() {
			return moduloRepository.findAll().stream()
					.map(moduloMapper :: toDTO)
					.toList();
		}

}
