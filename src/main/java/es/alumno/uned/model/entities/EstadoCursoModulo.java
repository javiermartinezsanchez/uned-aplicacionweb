package es.alumno.uned.model.entities;

/**
 * Enumeración común para el estado de un curso y sus módulos.
 */
public enum EstadoCursoModulo {
    BLOQUEADO(Ambito.CURSO, Ambito.MODULO),
    ACTIVO(Ambito.CURSO, Ambito.MODULO),
    PENDIENTE_REVISION(Ambito.MODULO), // Solo para módulos
    REVISADO(Ambito.MODULO),           // Solo para módulos
    COMPLETADO(Ambito.CURSO, Ambito.MODULO),
    BAJA(Ambito.CURSO);                // Solo para cursos

    public enum Ambito {
        CURSO, MODULO
    }

    private final Ambito[] ambitos;

    EstadoCursoModulo(Ambito... ambitos) {
        this.ambitos = ambitos;
    }

    public boolean esValidoParaCurso() {
        return tieneAmbito(Ambito.CURSO);
    }

    public boolean esValidoParaModulo() {
        return tieneAmbito(Ambito.MODULO);
    }

    private boolean tieneAmbito(Ambito ambitoBuscado) {
        for (Ambito a : ambitos) {
            if (a == ambitoBuscado) return true;
        }
        return false;
    }
}


