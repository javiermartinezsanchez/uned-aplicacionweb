package es.alumno.uned.dto;


/**
 *   DTO o POJO de la Entidad {@code Rol}
 */
public class RolDTO {

	private String nombre;

	private String descripcion;

	public RolDTO(String nombre, String descripcion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
}
