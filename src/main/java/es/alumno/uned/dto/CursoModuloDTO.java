package es.alumno.uned.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CursoModuloDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;          // ID de la relación (CursoModulo.id)
    @NotNull(message = "{validations.cursomodulo.modulo.requerido}")
    private Long moduloId;    // ID del módulo asociado
    private String nombreModulo; // Útil para mostrar el nombre en la tabla/lista sin buscar más
    
    @NotNull(message = "{validations.cursomodulo.orden.requerido}")
    @Min(value = 1, message = "{validations.cursomodulo.orden.min}")
    private Integer orden;
  
    @NotNull(message = "{validations.cursomodulo.peso.requerido}")
    @Min(value = 10, message = "{validations.cursomodulo.peso.rango}")
    @Max(value = 100, message = "{validations.cursomodulo.peso.rango}")
    private Integer peso;

    public CursoModuloDTO() {
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getModuloId() { return moduloId; }
    public void setModuloId(Long moduloId) { this.moduloId = moduloId; }

    public String getNombreModulo() { return nombreModulo; }
    public void setNombreModulo(String nombreModulo) { this.nombreModulo = nombreModulo; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }

    public Integer getPeso() { return peso; }
    public void setPeso(Integer peso) { this.peso = peso; }
}
