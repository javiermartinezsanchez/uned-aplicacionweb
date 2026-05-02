package es.alumno.uned.model.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "curso_valoracion")
public class CursoValoracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Curso
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @Column(name = "usuario_valoracion", nullable = true, length=255)
    private String usuario;
    // Valoración individual (1–5)
    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Integer valoracion;

    // Fecha de la valoración
    @Column(name = "fecha_valoracion", nullable = false)
    private LocalDateTime fechaValoracion;

    // Constructor vacío
    public CursoValoracion() {}

    // Constructor útil
    public CursoValoracion(Curso curso, String usuario, Integer valoracion) {
        this.curso = curso;
        this.usuario = usuario;
        this.valoracion = valoracion;
        this.fechaValoracion = LocalDateTime.now();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Integer getValoracion() {
		return valoracion;
	}

	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}

	public LocalDateTime getFechaValoracion() {
		return fechaValoracion;
	}

	public void setFechaValoracion(LocalDateTime fechaValoracion) {
		this.fechaValoracion = fechaValoracion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

   
}

