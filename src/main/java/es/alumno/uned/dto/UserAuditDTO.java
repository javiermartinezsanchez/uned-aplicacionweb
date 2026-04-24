package es.alumno.uned.dto;

import java.time.LocalDateTime;


public class UserAuditDTO {

	private Long id;
	private String nombreUsuario;
	private String mensaje;
	private LocalDateTime fechaAudit;
	
	public UserAuditDTO() {}
	
	public UserAuditDTO(Long id, String nombreUsuario, String mensaje, LocalDateTime fechaAudit) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.mensaje = mensaje;
		this.fechaAudit = fechaAudit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public LocalDateTime getFechaAudit() {
		return fechaAudit;
	}

	public void setFechaAudit(LocalDateTime fechaAudit) {
		this.fechaAudit = fechaAudit;
	}
	
	
}
