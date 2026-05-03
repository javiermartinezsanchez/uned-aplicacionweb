
package es.alumno.uned.model.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	private LocalDate fIni;
	@Column(name="FECHA_FIN")
	private LocalDate fFin;
	@Column(name="FECHA_INS")
	private LocalDateTime fIns;
	@Column(name="USER_INS")
	private String userIns;
	
	@Column(precision = 2, scale = 1)
	private BigDecimal valoracion;

	@Column(name="USUARIOS_REGISTRADOS", nullable=false)
	private Integer usuariosRegistrados = 0;
	
	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Modulo> modulos = new ArrayList<>();
	
	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CursoValoracion> valoraciones = new ArrayList<>();
	
	@OneToMany(mappedBy = "curso")
	private List<EstudianteCurso> estudiantes;



	public Curso() {}
	

	public Curso(Long id, String titulo, String descripcion, String uriImagen, Integer nivel, AreaTematica areaTematica,
			Usuario responsable, Integer duracion, LocalDate fIni, LocalDate fFin, LocalDateTime fIns,
			String userIns) {
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.uriImagen = uriImagen;
		this.nivel = nivel;
		this.areaTematica = areaTematica;
		this.responsable = responsable;
		this.duracion = duracion;
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
	public List<CursoValoracion> getValoraciones() {
		return valoraciones;
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

	public List<Modulo> getModulos() {
		return modulos;
	}
	
}
