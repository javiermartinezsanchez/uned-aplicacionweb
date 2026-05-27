package es.alumno.uned.dto;

import java.util.Date;
/**
 * DTO para envolver los usuario en Sesión 
 * 
 */
public class UserSessionInfoDTO {
	private String usuario;
	private Date lastRequest;
	
	public UserSessionInfoDTO(String usuario, Date lastRequest) {
		this.usuario = usuario;
		this.lastRequest = lastRequest;
	}
	
	public String getUsuario() {
		return this.usuario;
	}
	
	public Date getLastRequest() {
		return this.lastRequest;
	}
}
