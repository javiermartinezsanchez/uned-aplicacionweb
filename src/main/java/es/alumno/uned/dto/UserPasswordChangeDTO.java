package es.alumno.uned.dto;

import jakarta.validation.constraints.NotBlank;
/**
 * Clase contenedora para el cambio de password el usuario
 * 
 * Extiende de UserPasswordAdminChangeDTO
 */
public class UserPasswordChangeDTO extends UserPasswordAdminChangeDTO{

    @NotBlank(message = "{validations.password.current.mandatory}")
    private String oldPassword;


	public UserPasswordChangeDTO() {
		super();
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}

