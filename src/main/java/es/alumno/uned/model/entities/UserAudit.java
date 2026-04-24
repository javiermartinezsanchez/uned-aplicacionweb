package es.alumno.uned.model.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
/**
 * Entidad auxiliar para grabar la auditoría de accesos en la aplicación.
 * 
 * 
 */
import jakarta.persistence.Table;
@Entity
@Table(name = "auditoria_accesos")
public class UserAudit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="NOMBRE_USUARIO", length=50)
	private String nombreUsuario;
	@Column(name="MENSAJE", length=255)
	private String mensaje;
	@Column(name="FECHA_AUDITORIA", nullable=false)
	private LocalDateTime fechaAudit;
	
	public UserAudit() {
		super();
	}
	
	public UserAudit(String nombreUsuario, String mensaje, LocalDateTime timeStamp) {
		this.nombreUsuario = nombreUsuario;
		this.mensaje = mensaje;
		this.fechaAudit = timeStamp;
	}
    public void setId(Long id) {this.id=id;}
	public Long getId() {
		return id;
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
