package es.alumno.uned.dto;

import java.time.LocalDateTime;
import java.util.List;

import es.alumno.uned.model.entities.EstadoCursoModulo;

public class EstudianteCursoDTO {
        private EstudianteDTO estudiante;
        private CursoDTO curso;
        private LocalDateTime fechaSubscripcion;
        private LocalDateTime fechaUltimoAcceso;
        private Integer progreso;
        private Double calificacionFinal;
        private EstadoCursoModulo estado;
        private List<EstudianteCursoModuloDTO> modulos;
		public EstudianteCursoDTO() {}
		public EstudianteDTO getEstudiante() {
			return estudiante;
		}
		public void setEstudiante(EstudianteDTO estudiante) {
			this.estudiante = estudiante;
		}
		public CursoDTO getCurso() {
			return curso;
		}
		public void setCurso(CursoDTO curso) {
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
		public EstadoCursoModulo getEstado() {
			return estado;
		}
		public void setEstado(EstadoCursoModulo estado) {
			this.estado = estado;
		}
		public List<EstudianteCursoModuloDTO> getModulos() {
			return modulos;
		}
		public void setModulos(List<EstudianteCursoModuloDTO> modulos) {
			this.modulos = modulos;
		}
        
        
}




