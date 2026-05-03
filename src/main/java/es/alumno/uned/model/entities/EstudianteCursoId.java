package es.alumno.uned.model.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class EstudianteCursoId implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6586141451583889745L;
	private Long estudianteId;
    private Long cursoId;
	public EstudianteCursoId(Long estudianteId, Long cursoId) {
		super();
		this.estudianteId = estudianteId;
		this.cursoId = cursoId;
	}
	public Long getEstudianteId() {
		return estudianteId;
	}
	public void setEstudianteId(Long estudianteId) {
		this.estudianteId = estudianteId;
	}
	public Long getCursoId() {
		return cursoId;
	}
	public void setCursoId(Long cursoId) {
		this.cursoId = cursoId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cursoId == null) ? 0 : cursoId.hashCode());
		result = prime * result + ((estudianteId == null) ? 0 : estudianteId.hashCode());
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
		EstudianteCursoId other = (EstudianteCursoId) obj;
		if (cursoId == null) {
			if (other.cursoId != null)
				return false;
		} else if (!cursoId.equals(other.cursoId))
			return false;
		if (estudianteId == null) {
			if (other.estudianteId != null)
				return false;
		} else if (!estudianteId.equals(other.estudianteId))
			return false;
		return true;
	}

   
}

