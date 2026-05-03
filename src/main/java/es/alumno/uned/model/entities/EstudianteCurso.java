package es.alumno.uned.model.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "estudiante_curso")
public class EstudianteCurso {

    @EmbeddedId
    private EstudianteCursoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("estudianteId")
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cursoId")
    private Curso curso;

    @Column(name="FECHA_SUBSCRIPCION", nullable = false)
    private LocalDateTime fechaSubscripcion;
    
    @Column(name="FECHA_ULTIMO_ACCESO", nullable = false)
    private LocalDateTime fechaUltimoAcceso;

    private Integer progreso; // 0-100 %

    @Column(name="FECHA_COMPLETADO")
    private LocalDateTime fechaCompletado;
    
    private Double calificacionFinal; // 0-10 o 0-100

    @Enumerated(EnumType.STRING)
    private EstadoCurso estado; // ACTIVO, COMPLETADO, BLOQUEADO, BAJA

    @OneToMany(mappedBy = "estudianteCurso")
    private List<EstudianteCursoModulo> modulos;
    
    protected EstudianteCurso() {}

    public EstudianteCurso(Estudiante estudiante, Curso curso) {
        this.id = new EstudianteCursoId(estudiante.getId(), curso.getId());
        this.estudiante = estudiante;
        this.curso = curso;
        this.fechaSubscripcion = LocalDateTime.now();
        this.fechaUltimoAcceso = LocalDateTime.now();
        this.estado = EstadoCurso.ACTIVO;
        this.progreso = 0;
    }

	public EstudianteCursoId getId() {
		return id;
	}

	public void setId(EstudianteCursoId id) {
		this.id = id;
	}

	public Estudiante getEstudiante() {
		return estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public LocalDateTime getFechaSubscripcion() {
		return fechaSubscripcion;
	}

	public void setFechaSubscripcion(LocalDateTime fechaSubscripcion) {
		this.fechaSubscripcion = fechaSubscripcion;
	}

	public LocalDateTime getFechaUltimoAcceso() {
		return fechaUltimoAcceso;
	}

	public void setFechaUltimoAcceso(LocalDateTime fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	public Integer getProgreso() {
		return progreso;
	}

	public void setProgreso(Integer progreso) {
		this.progreso = progreso;
	}

	public Double getCalificacionFinal() {
		return calificacionFinal;
	}

	public void setCalificacionFinal(Double calificacionFinal) {
		this.calificacionFinal = calificacionFinal;
	}

	public EstadoCurso getEstado() {
		return estado;
	}

	public void setEstado(EstadoCurso estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaCompletado() {
		return fechaCompletado;
	}

	public void setFechaCompletado(LocalDateTime fechaCompletado) {
		this.fechaCompletado = fechaCompletado;
	}
    
    
    
}

