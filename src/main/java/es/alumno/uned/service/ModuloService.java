package es.alumno.uned.service;

import java.util.List;
import java.util.Map;


import es.alumno.uned.dto.ModuloDTO;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.records.PageParams;
import es.alumno.uned.model.util.Paginacion;

public interface ModuloService {

	ModuloDTO grabar(ModuloDTO moduloDTO, String UserIns);

	Paginacion<Modulo, ModuloDTO> listadoPaginado( PageParams pageData, Map<String, String> paramBusca);
	
	/**
	 * Devuelve el {@code ModuloDTO} buscado por su Id.
	 * @param idModulo Id del módulo a buscar.
	 * @return MóduloDTO encontrado.
	 */
	ModuloDTO get(Long idModulo);
	
	List<ModuloDTO> listAll();
}
