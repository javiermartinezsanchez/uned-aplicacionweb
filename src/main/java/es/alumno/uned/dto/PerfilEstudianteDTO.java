package es.alumno.uned.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PerfilEstudianteDTO {

    // Usuario
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellido1;

    private String apellido2;
    
    @Email(message = "Debe introducir un email válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    // Estudiante
    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "La población es obligatoria")
    private String poblacion;

    @NotBlank(message = "La provincia es obligatoria")
    private String provincia;

    @Size(min = 5, max = 5, message = "El código postal debe tener 5 dígitos")
    private String codPostal;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido1 = apellido1;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

