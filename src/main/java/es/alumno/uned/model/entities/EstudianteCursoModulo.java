package es.alumno.uned.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "estudiante_curso_modulo")
public class EstudianteCursoModulo {

    @EmbeddedId
    private EstudianteCursoModuloId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("estudianteCursoId")
    private EstudianteCurso estudianteCurso;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("moduloId")
    private Modulo modulo;
    
    @Column(name="titulo")
    private String titulo;
    
    @Column(name = "orden")
    private Integer orden;

    @Column(name = "peso")
    private Integer peso; 
    private Boolean completado;
    
    @Enumerated(EnumType.STRING)
    private EstadoCursoModulo estado; // ACTIVO, COMPLETADO, BLOQUEADO, BAJA

    @Column(name = "FECHA_COMPLETADO")
    private LocalDateTime fechaCompletado;

    @Column(name = "FECHA_ULTIMO_ACCESO")
    private LocalDateTime fechaUltimoAcceso;
    
    @Column(name = "FICHERO_ENTREGADO")
    private String urlEntrega; 

    @Column(name = "FECHA_ENTREGA")   
    private LocalDateTime fechaEntrega;

    @Column(name = "FECHA_REVISION")
    
    private LocalDateTime fechaRevision;
    @Column(name = "CALIFICACION", precision = 2, scale = 1)
	private BigDecimal calificacion; // si el módulo tiene nota

    @Column(name = "OBSERVACIONES_CALIFICACION", length = 256)
    private String notasCalificacion;
    

    // Constructor vacío
    protected EstudianteCursoModulo() {}

    public EstudianteCursoModulo(EstudianteCurso ecurso, Modulo mod) {
    	this.id = new EstudianteCursoModuloId(ecurso.getId(), mod.getId());
    	this.estudianteCurso = ecurso;
    	this.modulo = mod;
    	this.fechaUltimoAcceso = LocalDateTime.now();
    }
	public EstudianteCursoModuloId getId() {
		return id;
	}

	public void setId(EstudianteCursoModuloId id) {
		this.id = id;
	}

	public EstudianteCurso getEstudianteCurso() {
		return estudianteCurso;
	}

	public void setEstudianteCurso(EstudianteCurso estudianteCurso) {
		this.estudianteCurso = estudianteCurso;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public Boolean getCompletado() {
		return completado;
	}

	public void setCompletado(Boolean completado) {
		this.completado = completado;
	}

	public LocalDateTime getFechaCompletado() {
		return fechaCompletado;
	}

	public void setFechaCompletado(LocalDateTime fechaCompletado) {
		this.fechaCompletado = fechaCompletado;
	}

	public LocalDateTime getFechaUltimoAcceso() {
		return fechaUltimoAcceso;
	}

	public void setFechaUltimoAcceso(LocalDateTime fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}


	public String getUrlEntrega() {
		return urlEntrega;
	}


	public void setUrlEntrega(String urlEntrega) {
		this.urlEntrega = urlEntrega;
	}


	public LocalDateTime getFechaEntrega() {
		return fechaEntrega;
	}


	public void setFechaEntrega(LocalDateTime fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}


	public BigDecimal getCalificacion() {
		return calificacion;
	}


	public void setCalificacion(BigDecimal calificacion) {
		this.calificacion = calificacion;
	}


	public String getNotasCalificacion() {
		return notasCalificacion;
	}


	public void setNotasCalificacion(String notasCalificacion) {
		this.notasCalificacion = notasCalificacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public EstadoCursoModulo getEstado() {
		return estado;
	}

	public void setEstado(EstadoCursoModulo estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaRevision() {
		return fechaRevision;
	}

	public void setFechaRevision(LocalDateTime fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	
    
}
