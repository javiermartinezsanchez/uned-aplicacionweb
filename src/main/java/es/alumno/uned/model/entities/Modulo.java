package es.alumno.uned.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "modulos")
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Lob
    private String contenido;

//    @Column(nullable = false)
//    private int peso; // porcentaje dentro del curso

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoModulo tipo;

    @Column(name = "fecha_ins")
    private LocalDateTime fIns;

    @Column(name = "user_ins", length = 50)
    private String userIns;

    // RELACIÓN CON CURSO_MODULO
    @ManyToMany(mappedBy = "modulos") 
    private List<Curso> cursos = new ArrayList<>();
    
    @OneToMany(mappedBy = "modulo")
    private List<EstudianteCursoModulo> estudianteCursoModulos;


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

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

//	public int getPeso() {
//		return peso;
//	}
//
//	public void setPeso(int peso) {
//		this.peso = peso;
//	}

	public TipoModulo getTipo() {
		return tipo;
	}

	public void setTipo(TipoModulo tipo) {
		this.tipo = tipo;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contenido == null) ? 0 : contenido.hashCode());
		result = prime * result + ((cursos == null) ? 0 : cursos.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((fIns == null) ? 0 : fIns.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + ((userIns == null) ? 0 : userIns.hashCode());
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
		Modulo other = (Modulo) obj;
		if (contenido == null) {
			if (other.contenido != null)
				return false;
		} else if (!contenido.equals(other.contenido))
			return false;
		if (cursos == null) {
			if (other.cursos != null)
				return false;
		} else if (!cursos.equals(other.cursos))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
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
		if (tipo != other.tipo)
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (userIns == null) {
			if (other.userIns != null)
				return false;
		} else if (!userIns.equals(other.userIns))
			return false;
		return true;
	}

	/*
	 * public Curso getCurso() { return curso; }
	 * 
	 * public void setCurso(Curso curso) { this.curso = curso; }
	 */
   
}

