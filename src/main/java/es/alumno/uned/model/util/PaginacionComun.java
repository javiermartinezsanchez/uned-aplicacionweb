package es.alumno.uned.model.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * Clase de utilidad de Paginación para controlar el número de páginas de nuestros
 * datos.
 * 
 * Se calcula para que se puedan visualizar en footer de la tabla.
 */
public class PaginacionComun<T> {

	
	private String url;
	
	/**
	 * Página de datos de JPA
	 */
	private Page<T> pagina;
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
	 * Número de página actual para el cálculo de la actual.
	 */
	private int pagActual;
	
	public PaginacionComun(String url, Page<T> pagina) {
		this.url = url;
		this.pagina = pagina;
		
		
		numRows = pagina.getSize();
		totalPag = pagina.getTotalPages();
		pagActual = pagina.getNumber() + 1;
		
		creaListaPaginas();
	}

	private void creaListaPaginas() {
		paginas = new ArrayList<>();
		int desde = 1;
	    int hasta = 1;

	    if (totalPag <= numRows) {
			hasta = totalPag;
		}
		else {
			if (pagActual <= numRows/2 ) {
			   hasta = numRows;
			}
			else if (pagActual >= totalPag - numRows/2) {
				desde = totalPag - numRows + 1;
				hasta = numRows;
			}
			else {
				desde = pagActual - numRows/2;
				hasta = numRows;
			}
		}
		
		for (int i=1; 1<hasta;i++) {
			paginas.add(new Pagina(desde+i, pagActual == desde+1));
		}
		
	}

	public String getUrl() {
		return url;
	}

	public Page<T> getPagina() {
		return pagina;
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

    /**
     * Método para conocer si estoy en la primera página	
     */
	public boolean isFirst() {
		return pagina.isFirst();
	}
	/**
	 * Método para conocer si estoy en la última
	 */
	public boolean isLast() {
		return pagina.isLast();
	}
	/**
	 * Método para conocer si tengo más páginas
	 */
	public boolean isHasNext() {
		return pagina.hasNext();
	}
	/**
	 * Método para conocer si tengo una página anterior
	 */
	public boolean isHasPrevious() {
		return pagina.hasPrevious();
	}
}
