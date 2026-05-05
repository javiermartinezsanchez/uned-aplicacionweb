package es.alumno.uned.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.config.AppProperties;
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
    public String saveImagen(MultipartFile file) throws IOException {
        return saveFile(file, appProperties.getUploadImgDir());
    }
    /**
     * Guarda una documento imagen en el directorio configurado para ello.
     * @param file Documento a guardar
     * @return Nombre del fichero guardado
     * @throws IOException
     */

    public String saveDocumento(MultipartFile file) throws IOException {
        return saveFile(file, appProperties.getUploadDocDir());
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
    private String saveFile(MultipartFile file, String basePath) throws IOException {

        String nombreArchivo = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path ruta = Paths.get(basePath);
        if (!Files.exists(ruta)) {
        	Files.createDirectories(ruta);
        }

        Files.copy(file.getInputStream(), ruta.resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);

        return nombreArchivo;
    }
}
