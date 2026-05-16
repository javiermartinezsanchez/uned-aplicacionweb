package es.alumno.uned.dto;

import java.io.Serializable;

import es.alumno.uned.model.entities.TipoModulo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CursoModuloDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	// @NotNull(message = "{validations.cursomodulo.modulo.requerido}")
	private Long cursoId;
	@NotNull(message = "{validations.cursomodulo.modulo.requerido}")
	private Long moduloId;
	private String nombreModulo;
	private String descripcion;
	@NotNull(message = "{validations.cursomodulo.orden.requerido}")
	@Min(value = 1, message = "{validations.cursomodulo.orden.min}")
	private Integer orden;

	@NotNull(message = "{validations.cursomodulo.peso.requerido}")
	@Min(value = 10, message = "{validations.cursomodulo.peso.rango}")
	@Max(value = 100, message = "{validations.cursomodulo.peso.rango}")
	private Integer peso;

	private String contenido;
	private TipoModulo tipoModulo;
	public CursoModuloDTO() {
	}

	public Long getCursoId() {
		return cursoId;
	}

	public void setCursoId(Long id) {
		this.cursoId = id;
	}

	public Long getModuloId() {
		return moduloId;
	}

	public void setModuloId(Long moduloId) {
		this.moduloId = moduloId;
	}

	public String getNombreModulo() {
		return nombreModulo;
	}

	public void setNombreModulo(String nombreModulo) {
		this.nombreModulo = nombreModulo;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public TipoModulo getTipoModulo() {
		return tipoModulo;
	}

	public void setTipoModulo(TipoModulo tipo) {
		this.tipoModulo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
