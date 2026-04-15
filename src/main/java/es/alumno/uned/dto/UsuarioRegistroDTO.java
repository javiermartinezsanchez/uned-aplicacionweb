package es.alumno.uned.dto;
/**
 * DTO de Usuario para no propagar "entities" de BD.
 */
public class UsuarioRegistroDTO {

	private Long id;
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String email;
	private String password;
    private String rol;

	public UsuarioRegistroDTO() {
	}

	public UsuarioRegistroDTO(Long id, String nombre, String email, String apellido1,  String apellido2, String password, String rol) {
		this.id = id;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.email = email;
		this.password = password;
		this.rol= rol;
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

	public void setApellido1(String apellido) {
		this.apellido1 = apellido;
	}
	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido) {
		this.apellido2 = apellido;
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

	public String getRol() {
		return this.rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
    public String getNombreCompleto() {
    	return (nombre!=null?nombre:"")+ 
               (apellido1 !=null?" ".concat(apellido1):"") +
    	       (apellido2 !=null?" ".concat(apellido2):"");
    }
}
