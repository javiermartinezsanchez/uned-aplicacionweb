package es.alumno.uned.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EstudianteDTO extends UsuarioRegistroDTO {
	public EstudianteDTO(String rol) {
		super.setRol(rol);
	}
    public EstudianteDTO() {
    	super();
    }
	// Estudiante
    @NotBlank(message = "{validations.message.direccion.mandatory}")
    private String direccion;

    @NotBlank(message = "{validations.message.poblacion.mandatory}")
    private String poblacion;

    @NotBlank(message = "{validations.message.provincia.mandatory}")
    private String provincia;

    @Size(min = 5, max = 5, message = "{validations.message.codpostal.format}")
    private String codPostal;

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

  
}

