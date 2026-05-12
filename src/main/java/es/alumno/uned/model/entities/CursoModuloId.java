package es.alumno.uned.model.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
/**
 * Clave compuesta de Curso Modulo.
 */
@Embeddable
public class CursoModuloId implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6941052456735724460L;
	private Long cursoId;
    private Long moduloId;

    public CursoModuloId() {}

    public CursoModuloId(Long cursoId, Long moduloId) {
        this.cursoId = cursoId;
        this.moduloId = moduloId;
    }

    public Long getCursoId() {
    	return cursoId;
    }
    public Long getModuloId() {
    	return moduloId;
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cursoId == null) ? 0 : cursoId.hashCode());
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
		CursoModuloId other = (CursoModuloId) obj;
		if (cursoId == null) {
			if (other.cursoId != null)
				return false;
		} else if (!cursoId.equals(other.cursoId))
			return false;
		if (moduloId == null) {
			if (other.moduloId != null)
				return false;
		} else if (!moduloId.equals(other.moduloId))
			return false;
		return true;
	}

    
}
