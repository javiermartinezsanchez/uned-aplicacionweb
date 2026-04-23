package es.alumno.uned.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.model.entities.AreaTematica;
import es.alumno.uned.model.entities.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CursoDTO {

    private Long id;

    @NotBlank(message = "{validations.curso.titulo.mandatory}")
    private String titulo;

    @NotBlank(message = "{validations.curso.descripcion.mandatory}")
    private String descripcion;

    // Nombre del fichero guardado en /static/images/curso
    private String uriImagen;

    // Archivo subido desde el formulario
    private MultipartFile imagen;

    @NotNull(message = "{validations.curso.nivel.mandatory}")
    private Integer nivel;

    @NotNull(message = "{validations.curso.area.mandatory}")
    private Long areaTematicaId;

    @NotNull(message = "{validations.curso.responsable.mandatory}")
    private Long responsableId;

    @NotNull(message = "{validations.curso.fechaIni.mandatory}")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fIni;

    @NotNull(message = "{validations.curso.fechaFin.mandatory}")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fFin;

    private LocalDateTime fIns;
    private String userIns;

    public CursoDTO() {}
	
	public CursoDTO(Long id, String titulo,
			String descripcion, String uriImagen,
			int nivel, Long areaTematicaID,
			Long responsableID, LocalDateTime fIni,
			LocalDateTime fFin, LocalDateTime fIns,
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
	public int getNivel() {
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
	public Long getResponsableId() {
		return responsableId;
	}
	public void setResponsableId(Long responsableID) {
		this.responsableId = responsableID;
	}

	public MultipartFile getImagen() {
		return imagen;
	}

	public void setImagen(MultipartFile imagen) {
		this.imagen = imagen;
	}

	public LocalDateTime getfIni() {
		return fIni;
	}

	public void setfIni(LocalDateTime fIni) {
		this.fIni = fIni;
	}

	public LocalDateTime getfFin() {
		return fFin;
	}

	public void setfFin(LocalDateTime fFin) {
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

}
