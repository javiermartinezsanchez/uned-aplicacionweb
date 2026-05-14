package es.alumno.uned.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CursoDTO {

    private Long id;

    @NotBlank(message = "{validations.curso.titulo.mandatory}")
    @Size(max = 50, message = "{validations.curso.titulo.validformat}")
    private String titulo;

    @NotBlank(message = "{validations.descripcion.mandatory}")
    private String descripcion;

    // Nombre del fichero guardado en /curso/images/
    private String uriImagen;

    // Archivo subido desde el formulario
    private MultipartFile imagen;

    @NotNull(message = "{validations.curso.nivel.mandatory}")
    private Integer nivel;

    @NotNull(message = "{validations.curso.area.mandatory}")
    private Long areaTematicaId;

    private String areaTematicaText;
    @NotNull(message = "{validations.curso.responsable.mandatory}")
    private Long responsableId;

    private String nombreResponsable; 
      
    
    @NotNull(message = "{validations.curso.duracion.mandatory}")
    private Integer duracion;
 	@NotNull(message = "{validations.curso.fechaIni.mandatory}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fIni;

    @NotNull(message = "{validations.curso.fechaFin.mandatory}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fFin;

    private LocalDateTime fIns;
    private String userIns;
    private BigDecimal valoracion;
    private Integer usuariosRegistrados;
    private Integer numVistas;
    @Valid
    private List<CursoModuloDTO> modulos;
    private List<ContenidoExtraDTO> contenidosExtra = new ArrayList<>();
    
    public CursoDTO() {}
    public CursoDTO(Long idResponsable) {
    	this.responsableId = idResponsable;
    	
    }
	public CursoDTO(Long id, String titulo,
			String descripcion, String uriImagen,
			int nivel, Long areaTematicaID,
			Long responsableID, LocalDate fIni,
			LocalDate fFin, LocalDateTime fIns,
			String userIns) {
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.uriImagen = uriImagen;
		this.nivel = nivel;
		this.areaTematicaId = areaTematicaID;
		this.responsableId = responsableID;
		this.fIni = fIni;
		this.fFin = fFin;
		this.fIns = fIns;
		this.userIns = userIns;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getUriImagen() {
		return uriImagen;
	}
	public void setUriImagen(String uriImagen) {
		this.uriImagen = uriImagen;
	}
	public Integer getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public Long getAreaTematicaId() {
		return areaTematicaId;
	}
	public void setAreaTematicaId(Long areaTematicaID) {
		this.areaTematicaId = areaTematicaID;
	}
	public String getAreaTematicaText() {
		return areaTematicaText;
	}
	public void setAreaTematicaText(String areaTematicaTitulo) {
		this.areaTematicaText = areaTematicaTitulo;
	}

	public Long getResponsableId() {
		return responsableId;
	}
	public void setResponsableId(Long responsableID) {
		this.responsableId = responsableID;
	}

	
	public String getNombreResponsable() {
		return nombreResponsable;
	}

	public void setNombreResponsable(String nombreResponsable) {
		this.nombreResponsable = nombreResponsable;
	}

	public MultipartFile getImagen() {
		return imagen;
	}

	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}
    public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public LocalDate getFIni() {
		return fIni;
	}

	public void setFIni(LocalDate fIni) {
		this.fIni = fIni;
	}

	public LocalDate getFFin() {
		return fFin;
	}

	public void setFFin(LocalDate fFin) {
		this.fFin = fFin;
	}

	public LocalDateTime getfIns() {
		return fIns;
	}

	public void setfIns(LocalDateTime fIns) {
		this.fIns = fIns;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getUserIns() {
		return userIns;
	}
	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public LocalDate getfIni() {
		return fIni;
	}

	public void setfIni(LocalDate fIni) {
		this.fIni = fIni;
	}

	public LocalDate getfFin() {
		return fFin;
	}

	public void setfFin(LocalDate fFin) {
		this.fFin = fFin;
	}

	public BigDecimal getValoracion() {
		return valoracion;
	}

	public void setValoracion(BigDecimal valoracion) {
		this.valoracion = valoracion;
	}

	public Integer getUsuariosRegistrados() {
		return usuariosRegistrados;
	}

	public void setUsuariosRegistrados(Integer usuariosRegistrados) {
		this.usuariosRegistrados = usuariosRegistrados;
	}

	public List<CursoModuloDTO> getModulos() {
		return modulos;
	}

	public void setModulos(List<CursoModuloDTO> modulos) {
		if (modulos == null) {
	        this.modulos = new ArrayList<>();
	    } else {
	        this.modulos = modulos;
	    }
	}

	public List<ContenidoExtraDTO> getContenidosExtra() {
		return contenidosExtra;
	}
	public void setContenidosExtra(List<ContenidoExtraDTO> contenidosExtra) {
		this.contenidosExtra = contenidosExtra;
	}
	public Integer getNumVistas() {
		return numVistas;
	}
	public void setNumVistas(Integer numVistas) {
		this.numVistas = numVistas;
	}

	
}
