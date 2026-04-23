package es.alumno.uned.model.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Cursos")
public class Curso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="curso_id")
	private Long id;
	@Column(name="Titulo", length=50, nullable=false)
	private String titulo;
	@Column(name="Descripcion", length=255, nullable=false)
	private String descripcion;

	@Column(name="Imagen_Curso", length=255, nullable=true)
	private String uriImagen;
	@Column(name="Nivel", nullable=false)
	private Integer nivel;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_tematica_id", nullable = false)
    private AreaTematica areaTematica;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsable_id", nullable = false)
	private Usuario responsable;
	
	@Column(name="DURACION")
	private Integer duracion;
	
	@Column(name="FECHA_INI")
	private LocalDateTime fIni;
	@Column(name="FECHA_FIN")
	private LocalDateTime fFin;
	@Column(name="FECHA_INS")
	private LocalDateTime fIns;
	@Column(name="USER_INS")
	private String userIns;

	public Curso() {}
	
	public Curso(Long id, String titulo, String descripcion, String uriImagen, Integer nivel, AreaTematica areaTematica, LocalDateTime fIni,
			Integer duracion, LocalDateTime fFin, LocalDateTime fIns, String userIns) {
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.uriImagen = uriImagen;
		this.nivel = nivel;
		this.areaTematica = areaTematica;
		this.duracion= duracion;
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

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public AreaTematica getAreaTematica() {
		return areaTematica;
	}

	public void setAreaTematica(AreaTematica areaTematica) {
		this.areaTematica = areaTematica;
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

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getUriImagen() {
		return uriImagen;
	}

	public void setUriImagen(String uriImagen) {
		this.uriImagen = uriImagen;
	}

	public void setResponsable(Usuario responsable2) {
		this.responsable = responsable2;
		
	}

	public Usuario getResponsable() {
		return responsable;
	}
	
}
