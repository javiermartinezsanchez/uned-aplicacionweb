package es.alumno.uned.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PerfilEstudianteDTO extends UsuarioRegistroDTO {

    // Estudiante
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "La población es obligatoria")
    private String poblacion;

    @NotBlank(message = "La provincia es obligatoria")
    private String provincia;

    @Size(min = 5, max = 5, message = "El código postal debe tener 5 dígitos")
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

