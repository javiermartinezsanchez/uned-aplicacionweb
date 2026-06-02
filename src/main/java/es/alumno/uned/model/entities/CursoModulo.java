package es.alumno.uned.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "curso_modulos")
public class CursoModulo {

	@EmbeddedId
    private CursoModuloId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cursoId")
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("moduloId")
    @JoinColumn(name = "modulo_id")
    private Modulo modulo;

    @Column(name = "orden")
    private Integer orden;

    @Column(name = "peso")
    private Integer peso; // Valor entre 10 y 100

    
    public CursoModulo() {}

    public CursoModulo(Curso curso, Modulo modulo, Integer orden, Integer peso) {
        this.id = new CursoModuloId(curso.getId(), modulo.getId());
    	this.curso = curso;
        this.modulo = modulo;
        this.orden = orden;
        this.peso = peso;
    }

    public CursoModuloId getId() {
    	return id;
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

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public Modulo getModulo() {
		
		return this.modulo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((curso == null) ? 0 : curso.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((modulo == null) ? 0 : modulo.hashCode());
		result = prime * result + ((orden == null) ? 0 : orden.hashCode());
		result = prime * result + ((peso == null) ? 0 : peso.hashCode());
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
		CursoModulo other = (CursoModulo) obj;
		if (curso == null) {
			if (other.curso != null)
				return false;
		} else if (!curso.equals(other.curso))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (modulo == null) {
			if (other.modulo != null)
				return false;
		} else if (!modulo.equals(other.modulo))
			return false;
		if (orden == null) {
			if (other.orden != null)
				return false;
		} else if (!orden.equals(other.orden))
			return false;
		if (peso == null) {
			if (other.peso != null)
				return false;
		} else if (!peso.equals(other.peso))
			return false;
		return true;
	}
}
