package es.alumno.uned.service;

import java.util.Map;

import es.alumno.uned.model.entities.TipoFichero;
import es.alumno.uned.model.records.ArchivoFisicoDTO;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.util.PaginacionComun;

/**
 * Interfaz de mantenimiento de Ficheros para el Administrador.
 * <p>Nos permitirá la comunicación entre el Controller y el Servicio de Ficheros.
 */
public interface MantenimientoFicherosService {

	/**
	 * Borrado de ficheros a petición.
	 * <p>Eliminará de la ruta física los ficheros que se le determinen.
	 * 
	 * @param nombreArchivo Nombre real del fichero.
	 * @param tipoFichero Tipo de fichero, para determinar si es una imagen o un documento.
	 * @return True si se ha borrado correctamente.
	 */
	public boolean deleteFile(String nombreArchivo, TipoFichero tipoFichero);

	/**
	 * Listado de ficheros guardados en el disco duro.
	 * 
	 * @param params Mapa de parámetros de búsqueda.
	 * @param pageData Datos de la paginación y tamaño.
	 * @return Página de datos encontrados.
	 */
	public PaginacionComun<ArchivoFisicoDTO> listar(Map<String, String> params, PageParams pageData);
}
