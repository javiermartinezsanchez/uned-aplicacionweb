package es.alumno.uned.model.entities;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
/**
 * Entidad de usuario para la persistencia de datos.
 * 
 * <p>Mapea la tabla de usuarios de la BD.
 * 
 * <p>Genera la PK (id) y el índice único por "EMAIL"
 */
@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(name="INDEX_USUARIO_EMAIL", columnNames = "email"))
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "nombre", nullable=false)
	private String nombre;

	@Column(name = "EMAIL", nullable= false)
	private String email;
	
	@Column(name = "APELLIDO_1", length=50, nullable=false)
	private String apellido1;

	@Column(name="APELLIDO_2", length=50, nullable=true)
	private String apellido2;

	@Column(nullable=false)
	private String password;

	@Column(nullable=false)
	private String rol;
	
	@Column(name="activo")
	private boolean activo;

	@Column(name="FECHA_ALTA")
	private LocalDateTime fAlta;

	@Column(name="USUARIO_ALTA")
	private String usuarioAlta;
	
    @OneToOne(mappedBy="usuario",cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Estudiante estudiante;

	@OneToMany(mappedBy = "responsable", cascade = CascadeType.ALL, orphanRemoval = false)
	private List<Curso> cursos = new ArrayList<>();
    
    public Usuario() {
	}

	public Usuario(String nombre, String email, String apellido1, String apellido2,  String password, String roles, Boolean activo,
			LocalDateTime fAlta, String userAlta) {
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.email = email;
		this.password = password;
		this.rol = roles;
		this.activo = activo;	
		this.fAlta = fAlta;
		this.usuarioAlta = userAlta;
	}

	public Usuario(Long id, String nombre, String apellido1, String apellido2
			,String email, String password, String roles, Boolean activo, LocalDateTime fAlta, String userAlta) {
		this(nombre, email, apellido1, apellido2, password, roles, activo, fAlta, userAlta);
		this.id = id;
	}	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
		this.apellido2 = apellido2;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String roles) {
		this.rol = roles;
	}

	public LocalDateTime getfAlta() {
		return fAlta;
	}

	public void setfAlta(LocalDateTime fAlta) {
		this.fAlta = fAlta;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String user) {
		this.usuarioAlta = user;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activo ? 1231 : 1237);
		result = prime * result + ((apellido1 == null) ? 0 : apellido1.hashCode());
		result = prime * result + ((apellido2 == null) ? 0 : apellido2.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((rol == null) ? 0 : rol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (activo != other.activo)
			return false;
		if (apellido1 == null) {
			if (other.apellido1 != null)
				return false;
		} else if (!apellido1.equals(other.apellido1))
			return false;
		if (apellido2 == null) {
			if (other.apellido2 != null)
				return false;
		} else if (!apellido2.equals(other.apellido2))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (rol == null) {
			if (other.rol != null)
				return false;
		} else if (!rol.equals(other.rol))
			return false;
		return true;
	}
	
}

