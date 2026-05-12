package es.alumno.uned.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "curso_contenido_extra")
public class ContenidoExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String uri; // Ruta del archivo o URL externa

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contenido", nullable = false)
    private TipoContenido tipoContenido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public ContenidoExtra() {};
    
	public ContenidoExtra(String descripcion, String uri, TipoContenido tipoContenido) {
		super();
		this.descripcion = descripcion;
		this.uri = uri;
		this.tipoContenido = tipoContenido;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public TipoContenido getTipoContenido() {
		return tipoContenido;
	}

	public void setTipoContenido(TipoContenido tipoContenido) {
		this.tipoContenido = tipoContenido;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((curso == null) ? 0 : curso.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipoContenido == null) ? 0 : tipoContenido.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
		ContenidoExtra other = (ContenidoExtra) obj;
		if (curso == null) {
			if (other.curso != null)
				return false;
		} else if (!curso.equals(other.curso))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipoContenido != other.tipoContenido)
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}

    
}
