package es.alumno.uned.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import es.alumno.uned.config.AppProperties;
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

        Path ruta = Paths.get(basePath);
        if (!Files.exists(ruta)) {
        	Files.createDirectories(ruta);
        }

        Files.copy(new ByteArrayInputStream(file.contenido()), ruta.resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);

        return nombreArchivo;
    }
}
