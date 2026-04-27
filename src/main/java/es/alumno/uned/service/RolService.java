package es.alumno.uned.service;

import java.util.List;

import es.alumno.uned.dto.RolDTO;

/**
 * Clase service del modelo de negocio para la entidad {@code Rol}
 */
public interface RolService {
	/**
	 * Devolvemos el listado de rol {@code RolDTO}
	 * @return Listado
	 */
	List<RolDTO> getList();
}
