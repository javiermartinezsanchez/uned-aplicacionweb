package es.alumno.uned.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import es.alumno.uned.model.entities.EstadoCursoModulo;

public class EstudianteCursoModuloDTO {
	    private Long estudianteId;
	    private Long cursoId;
	    private Long moduloId;
	    private Boolean completado;
        private String titulo;
        private Integer orden;
        private Integer peso;
        private EstadoCursoModulo estado; // ACTIVO, COMPLETADO, BLOQUEADO, BAJA
        private LocalDateTime fechaCompletado;
        private LocalDateTime fechaUltimoAcceso;
        private String urlEntrega; 
        private LocalDateTime fechaEntrega;
        private LocalDateTime fechaRevision;
    	private BigDecimal calificacion; // si el módulo tiene nota
        private String notasCalificacion;
        
        public EstudianteCursoModuloDTO() {}
        
		public EstudianteCursoModuloDTO(Long estudianteId, Long cursoId, Long moduloId, Boolean completado,
				String titulo, Integer orden, Integer peso, EstadoCursoModulo estado, LocalDateTime fechaCompletado,
				LocalDateTime fechaUltimoAcceso, String urlEntrega, LocalDateTime fechaEntrega,
				LocalDateTime fechaRevision, BigDecimal calificacion, String notasCalificacion) {
			this.estudianteId = estudianteId;
			this.cursoId = cursoId;
			this.moduloId = moduloId;
			this.completado = completado;
			this.titulo = titulo;
			this.orden = orden;
			this.peso = peso;
			this.estado = estado;
			this.fechaCompletado = fechaCompletado;
			this.fechaUltimoAcceso = fechaUltimoAcceso;
			this.urlEntrega = urlEntrega;
			this.fechaEntrega = fechaEntrega;
			this.fechaRevision = fechaRevision;
			this.calificacion = calificacion;
			this.notasCalificacion = notasCalificacion;
		}
		public Long getEstudianteId() {
			return estudianteId;
		}
		public void setEstudianteId(Long estudianteId) {
			this.estudianteId = estudianteId;
		}
		public Long getCursoId() {
			return cursoId;
		}
		public void setCursoId(Long cursoId) {
			this.cursoId = cursoId;
		}
		public Long getModuloId() {
			return moduloId;
		}
		public void setModuloId(Long moduloId) {
			this.moduloId = moduloId;
		}
		public Boolean getCompletado() {
			return completado;
		}
		public void setCompletado(Boolean completado) {
			this.completado = completado;
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
		public LocalDateTime getFechaRevision() {
			return fechaRevision;
		}
		public void setFechaRevision(LocalDateTime fechaRevision) {
			this.fechaRevision = fechaRevision;
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

     
        

}

