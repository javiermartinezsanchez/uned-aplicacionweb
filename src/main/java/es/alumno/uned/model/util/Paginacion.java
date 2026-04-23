package es.alumno.uned.model.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

public class Paginacion<E, D> {

    private String url;

    /**
     * Página original de JPA (entidades)
     */
    private Page<E> paginaEntidad;

    /**
     * Lista de DTOs ya convertidos
     */
    private List<D> contenido;

    /**
     * Lista de páginas con el control de la actual.
     */
    private List<Pagina> paginas;

    /**
     * Total de páginas
     */
    private int totalPag;

    /**
     * Número de registros por página
     */
    private int numRows;

    /**
     * Número de página actual (1‑based)
     */
    private int pagActual;

    /**
     * Constructor genérico:
     * - Recibe Page<E>
     * - Convierte E → D mediante mapper
     */
    public Paginacion(String url, Page<E> paginaEntidad, Function<E, D> mapper) {
        this.url = url;
        this.paginaEntidad = paginaEntidad;

        // Convertimos entidades a DTOs
        this.contenido = paginaEntidad.getContent()
                                      .stream()
                                      .map(mapper)
                                      .toList();

        this.numRows = paginaEntidad.getSize();
        this.totalPag = paginaEntidad.getTotalPages();
        this.pagActual = paginaEntidad.getNumber() + 1;

        creaListaPaginas();
    }

    private void creaListaPaginas() {
        paginas = new ArrayList<>();
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
            paginas.add(new Pagina(numero, pagActual == numero));
        }
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

    // Métodos auxiliares (igual que antes)

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
}

