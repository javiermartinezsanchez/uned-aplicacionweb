package es.alumno.uned.dto;

import jakarta.validation.constraints.NotBlank;

public class UserPasswordAdminChangeDTO {

    @NotBlank(message = "{validations.password.new.mandatory}")
    private String newPassword;

    @NotBlank(message = "{validations.password.confirm.mandatory}")
    private String confirmPassword;

    public UserPasswordAdminChangeDTO() {}
    public UserPasswordAdminChangeDTO(String nuevaPass, String confirmPass) {
    	this.newPassword = nuevaPass;
    	this.confirmPassword = confirmPass;
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
