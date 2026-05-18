package es.alumno.uned.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import es.alumno.uned.model.entities.TipoContenido;
import es.alumno.uned.model.entities.TipoFichero;
import es.alumno.uned.model.records.ArchivoFisicoDTO;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.repository.ContenidoExtraRepository;
import es.alumno.uned.model.repository.CursoRepository;
import es.alumno.uned.model.util.PaginacionComun;
/**
 * Servicio de mantenimiento de ficheros para Administrador.
 * 
 * 
 */
@Service
public class MantenimientoFicherosServiceImpl implements MantenimientoFicherosService {

	@Autowired
	CursoRepository cursoRepository;
	
	@Autowired
	ContenidoExtraRepository contenidoExtraRepository;
	
	@Autowired
	FileStorageService fileStorageService;
	@Override
	public PaginacionComun<ArchivoFisicoDTO> listar(Map<String, String> params, PageParams pageData) {
		
			List<ArchivoFisicoDTO> listadoFinal = new ArrayList<>();

	        Set<String> imgsBD = cursoRepository.findAll().stream()
	                .map(c -> c.getUriImagen()).filter(uri -> uri != null).collect(Collectors.toSet());

	        Set<String> docsBD = contenidoExtraRepository.findAll().stream()
	        			.filter(c -> c.getTipoContenido() == TipoContenido.PROPIO)
	        			.map(c-> c.getUri())
	        			.filter(uri -> uri != null)
	        			.collect(Collectors.toSet());
	        var docsFiles = fileStorageService.listarArchivosFisicos(TipoFichero.DOCUMENTO);
	        var imgsFiles = fileStorageService.listarArchivosFisicos(TipoFichero.IMAGEN);
	        
	        for (var doc : docsFiles) {
	        	boolean esHuerfano = !docsBD.contains(doc.nombre());
	        	listadoFinal.add(new ArchivoFisicoDTO(
	        			doc.nombre(), doc.nombre().substring(doc.nombre().indexOf("_") + 1),
	        			formatearEspacio(doc.bytes()), esHuerfano, TipoFichero.DOCUMENTO));
	        }
	        for (var doc : imgsFiles) {
	        	boolean esHuerfano = !imgsBD.contains(doc.nombre());
	        	listadoFinal.add(new ArchivoFisicoDTO(
	        			doc.nombre(), doc.nombre().substring(doc.nombre().indexOf("_") + 1),
	        			formatearEspacio(doc.bytes()), esHuerfano, TipoFichero.IMAGEN));
	        }
	        Page<ArchivoFisicoDTO> pagina = new PageImpl<>(
	        		listadoFinal.stream()
	        		.skip(pageData.page() * pageData.size())
	        		.limit(pageData.size())
	        		.map( fichero -> new ArchivoFisicoDTO(
	        					fichero.nombre(),
	        				    fichero.nombreReal(),
	        				    fichero.sizeKB(),
	        				    fichero.esHuerfano(),
	        				    fichero.tipoFichero()
	        				    
	        				))
	        		.toList(), PageRequest.of(pageData.page(), pageData.size()),
	        		listadoFinal.size()
	        		);
	        return new PaginacionComun<ArchivoFisicoDTO>("",pagina);
	}

	public boolean deleteFile(String nombre, TipoFichero tipo) {
        return fileStorageService.deleteFile(nombre, tipo);
    }
	/**
	 * Formateamos el tamaño en bytes del fichero para mejor visualización.
	 * 
	 * @param bytes Número de bytes del fichero.
	 * @return String con formato de Kb
	 */
	private String formatearEspacio(long bytes) {
        if (bytes < 1024) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(1024));
        char pre = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %sB", bytes / Math.pow(1024, exp), pre);
    }
}
