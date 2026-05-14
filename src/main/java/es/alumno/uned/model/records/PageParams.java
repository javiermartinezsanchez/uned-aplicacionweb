package es.alumno.uned.model.records;

/**
 * Record que define el tamaño de la página de datos para 
 * las consultas paginadas.
 * <p>Se genera con una validación para impedir números negativos.
 * 
 * 
 */
public record PageParams(int page, int size) {
    // Podemos añadir validación básica aquí mismo
    public PageParams {
        if (page < 0) page = 0;
        if (size <= 0) size = 10;
    }

}
