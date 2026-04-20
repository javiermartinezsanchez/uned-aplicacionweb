package es.alumno.uned.dto;

import jakarta.validation.constraints.NotBlank;

public class UserPasswordChangeDTO {

    @NotBlank(message = "{validations.password.current.mandatory}")
    private String oldPassword;

    @NotBlank(message = "{validations.password.new.mandatory}")
    private String newPassword;

    @NotBlank(message = "{validations.password.confirm.mandatory}")
    private String confirmPassword;

	public UserPasswordChangeDTO() {
		
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}

