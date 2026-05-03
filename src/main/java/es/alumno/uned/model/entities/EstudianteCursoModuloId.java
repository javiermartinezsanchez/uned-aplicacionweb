package es.alumno.uned.model.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class EstudianteCursoModuloId implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EstudianteCursoId estudianteCursoId;
    private Long moduloId;
	public EstudianteCursoModuloId(EstudianteCursoId estudianteCursoId, Long moduloId) {
		super();
		this.estudianteCursoId = estudianteCursoId;
		this.moduloId = moduloId;
	}
	public EstudianteCursoId getEstudianteCursoId() {
		return estudianteCursoId;
	}
	public void setEstudianteCursoId(EstudianteCursoId estudianteCursoId) {
		this.estudianteCursoId = estudianteCursoId;
	}
	public Long getModuloId() {
		return moduloId;
	}
	public void setModuloId(Long moduloId) {
		this.moduloId = moduloId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estudianteCursoId == null) ? 0 : estudianteCursoId.hashCode());
		result = prime * result + ((moduloId == null) ? 0 : moduloId.hashCode());
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
		EstudianteCursoModuloId other = (EstudianteCursoModuloId) obj;
		if (estudianteCursoId == null) {
			if (other.estudianteCursoId != null)
				return false;
		} else if (!estudianteCursoId.equals(other.estudianteCursoId))
			return false;
		if (moduloId == null) {
			if (other.moduloId != null)
				return false;
		} else if (!moduloId.equals(other.moduloId))
			return false;
		return true;
	}

    
}



