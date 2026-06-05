package es.alumno.uned.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import es.alumno.uned.config.AppProperties;
import es.alumno.uned.model.entities.TipoFichero;
import es.alumno.uned.model.records.FicheroData;
/**
 * Servicio para guardar fichros.
 * 
 * <b>Tenemos dos métodos:</b>
 * <ul>
 * <li>saveImagen: Guarda las imágenes de los cursos</li>
 * <li>saveDocumento: Guarda recursos de cursos y entregables de los estudiantes</li>
 * </ul>
 */
@Service
public class FileStorageService {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FileStorageService.class);
    private final AppProperties appProperties;

    public FileStorageService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }
	/**
	 * Guarda una imagen en el directorio configurado para imágenes.
	 * @param file Imagen a guardar
	 * @return Nombre del fichero guardado
	 * @throws IOException
	 */
    public String saveImagen(FicheroData file) throws IOException {
        return saveFile(file, appProperties.getUploadImgDir());
    }
    /**
     * Guarda una documento imagen en el directorio configurado para ello.
     * @param file Documento a guardar
     * @return Nombre del fichero guardado
     * @throws IOException
     */

    public String saveDocumento(FicheroData file) throws IOException {
        return saveFile(file, appProperties.getUploadDocDir());
    }
    /**
     * Método para la descarga de un documento guardado en nuestro disco.
     * 
     * @param fichero Nombre del fichero a descargar.
     * @return Resource del fichero (contenido)
     */
    public Resource getDocumento(String fichero) {
    	try {
    	Path dirDoc = Paths.get(appProperties.getUploadDocDir());
    	Path file = dirDoc.resolve(fichero);
    	Resource resource = new UrlResource(file.toUri());
    	if (resource.exists() && resource.isReadable()) {
    		return resource;
    	}
    	
    	
    	} catch (MalformedURLException e) {
            // Manejo de error físico
        }
    	return null;
    }
    /**
     * Guardamos el fichero enviado desde la aplicación {@code MultipartFile} al directorio deseado.
    <ul>
    <li>Crea el directorio si no existe</li>
    <li>Genera un nombre único</li>
    <li>Copia el archivo al sistema de ficheros</li>
    <li>Devuelve el nombre final</li>
    </ul>
     * @param file  Fichero a guardar
     * @param basePath Directorio final
     * @return Nombre del fichero generado
     * @throws IOException
     */
    private String saveFile(FicheroData file, String basePath) throws IOException {

        String nombreArchivo = UUID.randomUUID() + "_" + file.nombreOriginal();
        log.info("[FileStorageService] fichero : '{}'", file.nombreOriginal());
        log.info("[FileStorageService] nombre : '{}'", nombreArchivo);
        log.info("[FileStorageService] basePath : '{}'", basePath);
       
        Path ruta = Paths.get(basePath);
        if (!Files.exists(ruta)) {
        	Files.createDirectories(ruta);
        }

        Files.copy(new ByteArrayInputStream(file.contenido()), ruta.resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
        log.info("[FileStorageService] basePath : '{}'", ruta.resolve(nombreArchivo));
        return nombreArchivo;
    }
    
    /**
     * Estructura utilitaria para obtener los ficheros existentes en el disco.
     * @param nombre Nombre del archivo.
     * @param bytes Número de bytes del tamaño del fichero.
     */
    public record ArchivoDiscoInfo(String nombre, long bytes) {}
    
    
    /**
     * Recorre un directorio y devuelve los datos de los archivos encontrados.
     */
    public List<ArchivoDiscoInfo> listarArchivosFisicos(TipoFichero tipo) {
        List<ArchivoDiscoInfo> archivos = new ArrayList<>();

        String rutaDirectorio = (tipo == TipoFichero.IMAGEN ? appProperties.getUploadImgDir(): appProperties.getUploadDocDir());
        Path rootPath = Paths.get(rutaDirectorio);
        if (!Files.exists(rootPath)) return archivos;

        try (Stream<Path> stream = Files.walk(rootPath, 1)) {
            List<Path> ficheros = stream.filter(Files::isRegularFile).toList();
            for (Path f : ficheros) {
                archivos.add(new ArchivoDiscoInfo(f.getFileName().toString(), Files.size(f)));
            }
        } catch (IOException e) {
            throw new RuntimeException("Error físico leyendo la carpeta: " + rutaDirectorio, e);
        }
        return archivos;
    }
    /**
     * Borrado de fichero físico a petición.
     * 
     * @param nombreFichero Nombre del fichero.
     * @param tipo TipoFichero (Imagen/doc).
     * @return Si se borra "true", si existe alguna exception "false".
     */
    public boolean deleteFile(String nombreFichero, TipoFichero tipo) {
    	
    	String basePath = (tipo == TipoFichero.IMAGEN ? appProperties.getUploadImgDir(): appProperties.getUploadDocDir());
        try {
            Path archivo = Paths.get(basePath).resolve(nombreFichero);
            return Files.deleteIfExists(archivo);
        } catch (IOException e) {
            return false;
        }
    }
}
