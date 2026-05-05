package es.alumno.uned.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.config.AppProperties;
import es.alumno.uned.dto.CursoDTO;
import es.alumno.uned.mapper.CursoMapper;
import es.alumno.uned.model.entities.Curso;
import es.alumno.uned.model.entities.CursoValoracion;
import es.alumno.uned.model.repository.AreaTematicaRepository;
import es.alumno.uned.model.repository.CursoRepository;
import es.alumno.uned.model.repository.CursoValoracionRepository;
import es.alumno.uned.model.repository.UsuarioRepository;
import es.alumno.uned.model.util.Paginacion;
import jakarta.annotation.PostConstruct;

@Service
public class  CursoServiceImpl implements CursoService{

	private final AppProperties appProperties;
	public CursoServiceImpl(AppProperties appProperties){
		this.appProperties = appProperties;
	}

	
	@Autowired
	CursoRepository cursoRepository;
	
	@Autowired
	AreaTematicaRepository areaTematicaRepository;
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	CursoValoracionRepository cursoValoracionRepository;
	
	@Autowired
	CursoMapper cursoMapper;
	
	
	@Override
	public CursoDTO getCurso(Long id) {
		return cursoMapper.toDTO(getValidCurso(id));
	}
	/**
	 * Obtenemos un curso válido.
	 * <ul>
	 * <li>Si el id es null -> Nuevo Curso</li>
	 * <li>Si el id no es nulo</li>
	 * <ul><li>Si se encuentra -> Curso de la BD</li>
	 * <li>Si no se encuentra -> Nuevo Curso</li>
	 * </ul>
	 * </ul>
	 * @param id Id del curso a buscar
	 * @return {@code Curso}
	 */
	private Curso getValidCurso(Long id) {
		Curso curso = (id != null)
				? cursoRepository.findById(id).orElse(new Curso())
				: new Curso();
		return curso;
	}
	@Override
	public CursoDTO grabar(CursoDTO dto, MultipartFile imagen, String usuario) throws IOException {
		var curso = getValidCurso(dto.getId());
	    cursoMapper.toEntity(dto, curso);
	    if (curso.getfIns() == null) {
	    	curso.setfIns(LocalDateTime.now());
	    	curso.setUserIns(usuario);
	    }
	    // Subida de imagen
	    if (imagen != null && !imagen.isEmpty()) {

	        curso.setUriImagen(saveFile(imagen));
	    }

	    return cursoMapper.toDTO(cursoRepository.save(curso));
		
	}
	private String saveFile(MultipartFile imagen) throws IOException {
		String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();

//		Path ruta = Paths.get("src/main/resources/static/images/curso")
//		        .resolve(nombreArchivo)
//		        .toAbsolutePath();

        Path ruta = Paths.get(appProperties.getUploadDir());
        if (!Files.exists(ruta)) {
        	Files.createDirectories(ruta);
        }
                
        
		Files.copy(imagen.getInputStream(), ruta.resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
		return nombreArchivo;
	}
	@Override
	public Paginacion<Curso, CursoDTO> listadoPaginado(String url,  Pageable pageable){
		
		return construirPaginacion(url, cursoRepository.findAll(pageable));
	}
	
    @Override
    public Paginacion<Curso, CursoDTO> listadoPaginadoPorResponsable(
            String url, Pageable pageable, Long responsableId) {

        Page<Curso> page = cursoRepository.findByResponsableId(responsableId, pageable);
        return construirPaginacion(url, page);
    }
    @Override
    public Paginacion<Curso, CursoDTO> listadoPaginadoPorArea(
            String url, Pageable pageable, Long areaId) {

        Page<Curso> page = cursoRepository.findByAreaTematicaId(areaId, pageable);
        return construirPaginacion(url, page);
    }
    
    @Override
    public Paginacion<Curso, CursoDTO> listadoPaginadoPorNivel(
            String url, Pageable pageable, int nivel) {

        Page<Curso> page = cursoRepository.findByNivel(nivel, pageable);
        return construirPaginacion(url, page);
    }
    
    @Override
    public Paginacion<Curso, CursoDTO> listadoPaginadoPorTitulo(
            String url, Pageable pageable, String titulo) {

        Page<Curso> page = cursoRepository.findByTituloContainingIgnoreCase(titulo, pageable);
        return construirPaginacion(url, page);
    }
    
    @Override
    public Paginacion<Curso, CursoDTO> listadoPaginadoPorFechaInicio(
            String url, Pageable pageable, LocalDateTime desde, LocalDateTime hasta) {

        Page<Curso> page = cursoRepository.findByFIniBetween(desde, hasta, pageable);
        return construirPaginacion(url, page);
    }
    
	private Paginacion<Curso, CursoDTO> construirPaginacion(String url, Page<Curso> page) {
	        return new Paginacion.Builder<Curso, CursoDTO>()
	                .url(url)
	                .pagina(page)
	                .mapper(cursoMapper::toDTO)
	                .build();
	    }
	@Override
	public BigDecimal guardarValoracion(Long cursoId, Integer valoracion, String usuario) {
       
		Curso curso = cursoRepository.findById(cursoId).orElse(null);
		
		BigDecimal media =BigDecimal.ZERO;
		Boolean addValoracion = true;
		if (curso != null) {
			media=curso.getValoracion();
			if (usuario !=null) {
				addValoracion = (cursoValoracionRepository.findByCursoIdAndUsuario(cursoId, usuario) == null); 
			}
	        if (addValoracion) {
	        	cursoValoracionRepository.save(new CursoValoracion(curso,usuario,valoracion));
	    		// Una vez añadida la valoración calculamos el valor de la media.
	            BigDecimal suma = curso.getValoraciones().stream()
	                    .map(v -> BigDecimal.valueOf(v.getValoracion()))
	                    .reduce(BigDecimal.ZERO, BigDecimal::add);

	            int total = curso.getValoraciones().size();

	            try {
	                media = suma.divide(
	                    BigDecimal.valueOf(total),
	                    1, // un decimal
	                    RoundingMode.HALF_UP
	            );

	            }
	            catch(ArithmeticException e) {
	            	media =BigDecimal.ZERO;
	            }
	        }
		}
		return media;
	}
	@Override
	public List<CursoDTO> listadoHome() {
		return cursoRepository.findAll().stream()
		.filter(c -> c.getUriImagen().length() != 0)
		.map(cursoMapper :: toDTO)
		.toList();
	}
	
}
