package es.alumno.uned.dto;

import es.alumno.uned.model.entities.TipoContenido;

/**
 * Clase DTO envolvente de la entidad {@link ContenidoExtra}
 */
public class ContenidoExtraDTO {
    private Long id;

    private String descripcion;

    private String uri; 

    private TipoContenido tipoContenido;

	public ContenidoExtraDTO(Long id, String descripcion, String uri, TipoContenido tipoContenido) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.uri = uri;
		this.tipoContenido = tipoContenido;
	}

	public Long getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getUri() {
		return uri;
	}

	public TipoContenido getTipoContenido() {
		return tipoContenido;
	}
    
    

}
