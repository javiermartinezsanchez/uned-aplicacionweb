package es.alumno.uned.controller;

import java.util.Date;

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
