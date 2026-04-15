package es.alumno.uned.model.util;
/**
 * Clase de utilidad para el control de la paginación de los datos en tablas.
 * 
 * Nos informará del número de pagina y si es la actual o no.
 * 
 * 
 */
public class Pagina {
	private int numero;
	private boolean actual;

	public Pagina(int numero, boolean actual) {
		super();
		this.numero = numero;
		this.actual = actual;
	}
	
	public int getNumero() {
		return numero;
	}
	public boolean isActual() {
		return actual;
	}
}
