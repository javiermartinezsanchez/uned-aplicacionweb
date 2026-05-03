package es.alumno.uned.model.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "modulo")
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Lob
    private String contenido;

    @Column(nullable = false)
    private int peso; // porcentaje dentro del curso

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoModulo tipo;

    @Column(name = "requiere_entrega", nullable = false)
    private boolean requiereEntrega;

    @Column(name = "finalizacion_automatica", nullable = false)
    private boolean finalizacionAutomatica;

    @Column(name = "fecha_ins")
    private LocalDateTime fIns;

    @Column(name = "user_ins", length = 50)
    private String userIns;

    // RELACIÓN CON CURSO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private Curso curso;

    @OneToMany(mappedBy = "modulo")
    private List<EstudianteCursoModulo> estudianteCursoModulos;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public TipoModulo getTipo() {
		return tipo;
	}

	public void setTipo(TipoModulo tipo) {
		this.tipo = tipo;
	}

	public boolean isRequiereEntrega() {
		return requiereEntrega;
	}

	public void setRequiereEntrega(boolean requiereEntrega) {
		this.requiereEntrega = requiereEntrega;
	}

	public boolean isFinalizacionAutomatica() {
		return finalizacionAutomatica;
	}

	public void setFinalizacionAutomatica(boolean finalizacionAutomatica) {
		this.finalizacionAutomatica = finalizacionAutomatica;
	}

	public LocalDateTime getfIns() {
		return fIns;
	}

	public void setfIns(LocalDateTime fIns) {
		this.fIns = fIns;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

   
}

