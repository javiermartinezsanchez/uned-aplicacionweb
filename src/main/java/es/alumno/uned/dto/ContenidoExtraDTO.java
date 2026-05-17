package es.alumno.uned.dto;

import es.alumno.uned.model.entities.TipoContenido;

/**
 * Clase DTO envolvente de la entidad {@link ContenidoExtra}
 */
public class ContenidoExtraDTO {
    private Long id;

    private String descripcion;

    private String uri; 
    private String nombreReal;
    private String contentType;
 
	private TipoContenido tipoContenido;

    public ContenidoExtraDTO() {};
	public ContenidoExtraDTO(Long id, String descripcion, String uri, 
			TipoContenido tipoContenido,
			String nombreReal,
			String contentType) {
		this.id = id;
		this.descripcion = descripcion;
		this.uri = uri;
		this.tipoContenido = tipoContenido;
		this.nombreReal = nombreReal;
		this.contentType = contentType;		
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
    
	   public void setId(Long id) {
			this.id = id;
		}
		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}
		public void setUri(String uri) {
			this.uri = uri;
		}
		public void setTipoContenido(TipoContenido tipoContenido) {
			this.tipoContenido = tipoContenido;
		}
		public String getNombreReal() {
			return nombreReal;
		}
		public void setNombreReal(String nombreReal) {
			this.nombreReal = nombreReal;
		}
		public String getContentType() {
			return contentType;
		}
		public void setContentType(String contentType) {
			this.contentType = contentType;
		}


}
