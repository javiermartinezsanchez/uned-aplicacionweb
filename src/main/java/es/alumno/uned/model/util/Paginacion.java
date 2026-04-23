package es.alumno.uned.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.data.domain.Page;

/**
 * Clase de utilidad para gestionar la paginación de valores
 * en nuestra vistas de datos.
 * 
 * Utilizamos el patrón Builder para generar un código más legible evitando 
 * constructores complejos.
 * 
 * @param <E> Tipo de dato de la Entidad JPA
 * @param <D> Tipo de dato del DTO 
 */
public class Paginacion<E, D> {

    private final String url;
    private final Page<E> paginaEntidad;
    private final List<D> contenido;
    private final List<Pagina> paginas;
    private final int totalPag;
    private final int numRows;
    private final int pagActual;

    private Paginacion(Builder<E, D> builder) {
        this.url = builder.url;
        this.paginaEntidad = builder.paginaEntidad;

        // Convertimos entidades a DTOs
        this.contenido = builder.paginaEntidad.getContent()
                .stream()
                .map(builder.mapper)
                .toList();

        this.numRows = paginaEntidad.getSize();
        this.totalPag = paginaEntidad.getTotalPages();
        this.pagActual = paginaEntidad.getNumber() + 1;

        this.paginas = crearListaPaginas();
    }

    /**
     * 
     * Método que nos genera la lista de páginas y define cual es la actual.
     * 
     * @return Lista de páginas según los datos obtenidos
     */
    private List<Pagina> crearListaPaginas() {
        List<Pagina> lista = new ArrayList<>();

        int desde = 1;
        int hasta = 1;

        if (totalPag <= numRows) {
            hasta = totalPag;
        } else {
            if (pagActual <= numRows / 2) {
                hasta = numRows;
            } else if (pagActual >= totalPag - numRows / 2) {
                desde = totalPag - numRows + 1;
                hasta = numRows;
            } else {
                desde = pagActual - numRows / 2;
                hasta = numRows;
            }
        }

        for (int i = 0; i < hasta; i++) {
            int numero = desde + i;
            lista.add(new Pagina(numero, pagActual == numero));
        }

        return lista;
    }

    // GETTERS

    public String getUrl() {
        return url;
    }

    public List<D> getContenido() {
        return contenido;
    }

    public List<Pagina> getPaginas() {
        return paginas;
    }

    public int getTotalPag() {
        return totalPag;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getPagActual() {
        return pagActual;
    }

    public boolean isFirst() {
        return paginaEntidad.isFirst();
    }

    public boolean isLast() {
        return paginaEntidad.isLast();
    }

    public boolean isHasNext() {
        return paginaEntidad.hasNext();
    }

    public boolean isHasPrevious() {
        return paginaEntidad.hasPrevious();
    }

    // ============================
    //         BUILDER
    // ============================

    public static class Builder<E, D> {

        private String url;
        private Page<E> paginaEntidad;
        private Function<E, D> mapper;

        public Builder<E, D> url(String url) {
            this.url = url;
            return this;
        }

        public Builder<E, D> pagina(Page<E> pagina) {
            this.paginaEntidad = pagina;
            return this;
        }

        public Builder<E, D> mapper(Function<E, D> mapper) {
            this.mapper = mapper;
            return this;
        }

        public Paginacion<E, D> build() {
            Objects.requireNonNull(url, "url no puede ser null");
            Objects.requireNonNull(paginaEntidad, "paginaEntidad no puede ser null");
            Objects.requireNonNull(mapper, "mapper no puede ser null");

            return new Paginacion<>(this);
        }
    }
}

