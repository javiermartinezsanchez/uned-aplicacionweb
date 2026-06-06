package es.alumno.uned.dto;

import jakarta.validation.constraints.NotBlank;

/** 
 * Clase DTO de AreaTemática para el transporte Vista-Controlador-Modelo (Service).
 */
@SuppressWarnings("javadoc")
public class AreaTematicaDTO {

	private Long id;
	
	@NotBlank(message = "{validations.areatematica.titulo.mandatory}")
	private String titulo;
	
	@NotBlank(message = "{validations.areatematica.descripcion.mandatory}")
	private String descripcion;
	
	private Integer numCursos;
	public AreaTematicaDTO() {};
	public AreaTematicaDTO(Long id, String titulo, String descripcion) {
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}
	public Integer getNumCursos() {
		return numCursos;
	}
	public void setNumCursos(Integer numCursos) {
		this.numCursos = numCursos;
	}


}
