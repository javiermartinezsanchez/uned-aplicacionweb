
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
import jakarta.persistence.OrderBy;
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
	@Column(name="Descripcion", length=512, nullable=false)
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
	private BigDecimal valoracion = BigDecimal.ZERO;

	@Column(name="USUARIOS_REGISTRADOS", nullable=false)
	private Integer usuariosRegistrados = 0;
		
	@Column(name="NUMERO_VISTAS", nullable=true)
	private Integer numVistas = 0;

	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orden ASC") // Esto ayuda a que siempre vengan ordenados
    private List<CursoModulo> cursoModulos = new ArrayList<>();	
	
	// Dentro de la clase Curso.java
	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ContenidoExtra> contenidosExtra = new ArrayList<>();


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
		this.numVistas = 0;
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

	public List<CursoModulo> getModulos() {
		return cursoModulos;
	}
	public Integer getNumVistas() {
		return numVistas;
	}


	public void setNumVistas(Integer numVistas) {
		this.numVistas = numVistas;
	}
	
	/**
	 * Utilidad para añadir Usuarios registrados al curso
	 */
	public void addUserRegistred() {
		this.usuariosRegistrados = (this.usuariosRegistrados == null ? 0 : this.usuariosRegistrados) + 1;
	}
	/**
	 * Utilidad para añadir vistas al curso
	 */
	public void addVista() {
		this.numVistas = (this.numVistas == null ? 0: this.numVistas) + 1;
				
	}
	//
	/**
	 * Para poder añadir un módulo individual sin tener que setear toda la lista.
	 * 
	 * @param modulo Módulo a insertar
	 * @param orden Orden del módulo
	 * @param peso Peso del módulo (0-100)
	 */
	public void addModulo(Modulo modulo, Integer orden, Integer peso) {
        CursoModulo relacion = new CursoModulo(this, modulo, orden, peso);
        this.cursoModulos.add(relacion);
    }
	/**
	 * Para poder borrar un módulo individual sin tener que setear toda la lista.
	 * 
	 * @param modulo Módulo a insertar
	 */

	public void removeModulo(Modulo modulo) {
	    this.cursoModulos.removeIf(cm -> cm.getModulo().equals(modulo));
	}
	
	/**
	 * Utilidad para añadir la relación de contenidos extra al curso.
	 * @param contenido
	 */
	public void addContenidoExtra(ContenidoExtra contenido) {
		contenido.setCurso(this);
	    contenidosExtra.add(contenido);
	    
	}
	public List<ContenidoExtra> getContenidosExtra() {
		return this.contenidosExtra;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((duracion == null) ? 0 : duracion.hashCode());
		result = prime * result + ((fFin == null) ? 0 : fFin.hashCode());
		result = prime * result + ((fIni == null) ? 0 : fIni.hashCode());
		result = prime * result + ((fIns == null) ? 0 : fIns.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nivel == null) ? 0 : nivel.hashCode());
		result = prime * result + ((numVistas == null) ? 0 : numVistas.hashCode());
		result = prime * result + ((responsable == null) ? 0 : responsable.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + ((uriImagen == null) ? 0 : uriImagen.hashCode());
		result = prime * result + ((userIns == null) ? 0 : userIns.hashCode());
		result = prime * result + ((usuariosRegistrados == null) ? 0 : usuariosRegistrados.hashCode());
		result = prime * result + ((valoracion == null) ? 0 : valoracion.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (duracion == null) {
			if (other.duracion != null)
				return false;
		} else if (!duracion.equals(other.duracion))
			return false;
		if (fFin == null) {
			if (other.fFin != null)
				return false;
		} else if (!fFin.equals(other.fFin))
			return false;
		if (fIni == null) {
			if (other.fIni != null)
				return false;
		} else if (!fIni.equals(other.fIni))
			return false;
		if (fIns == null) {
			if (other.fIns != null)
				return false;
		} else if (!fIns.equals(other.fIns))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nivel == null) {
			if (other.nivel != null)
				return false;
		} else if (!nivel.equals(other.nivel))
			return false;
		if (numVistas == null) {
			if (other.numVistas != null)
				return false;
		} else if (!numVistas.equals(other.numVistas))
			return false;
		if (responsable == null) {
			if (other.responsable != null)
				return false;
		} else if (!responsable.equals(other.responsable))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (uriImagen == null) {
			if (other.uriImagen != null)
				return false;
		} else if (!uriImagen.equals(other.uriImagen))
			return false;
		if (userIns == null) {
			if (other.userIns != null)
				return false;
		} else if (!userIns.equals(other.userIns))
			return false;
		if (usuariosRegistrados == null) {
			if (other.usuariosRegistrados != null)
				return false;
		} else if (!usuariosRegistrados.equals(other.usuariosRegistrados))
			return false;
		if (valoracion == null) {
			if (other.valoracion != null)
				return false;
		} else if (!valoracion.equals(other.valoracion))
			return false;
		return true;
	}


	
	
}
