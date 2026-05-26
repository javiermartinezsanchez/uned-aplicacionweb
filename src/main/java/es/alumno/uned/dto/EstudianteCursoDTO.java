
package es.alumno.uned.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import es.alumno.uned.model.entities.EstadoCursoModulo;

public class EstudianteCursoDTO {
        private EstudianteDTO estudiante;
        private CursoDTO curso;
        private LocalDateTime fechaSubscripcion;
        private LocalDateTime fechaUltimoAcceso;
        private LocalDateTime fechaCompletado;
        private Integer progreso;
        private Double calificacionFinal;
        private EstadoCursoModulo estado;
        private List<EstudianteCursoModuloDTO> modulos;
        private List<ContenidoExtraDTO> contenidosExtra = new ArrayList<>();
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
		
		public LocalDateTime getFechaCompletado() {
			return fechaCompletado;
		}
		public void setFechaCompletado(LocalDateTime fechaCompletado) {
			this.fechaCompletado = fechaCompletado;
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
        // Para obtener los datos del curso
		public Long getId() {
			return this.curso.getId();
		}
		public String getTitulo() {
			return this.curso.getTitulo();
		}
		public String getDescripcion() {
			return this.curso.getDescripcion();
		}
		public String getUriImagen() {
			return this.curso.getUriImagen();
		}
		public Integer getNivel() {
			return this.curso.getNivel();
		}
		public Long getAreaTematicaId() {
			return this.curso.getAreaTematicaId();
		}
		public String getAreaTematicaText() {
			return this.curso.getAreaTematicaText();
		}

		public Long getResponsableId() {
			return this.curso.getResponsableId();
		}
		
		public String getNombreResponsable() {
			return this.curso.getNombreResponsable();
		}

	    public Integer getDuracion() {
			return this.curso.getDuracion();
		}

		public LocalDate getFIni() {
			return this.curso.getFIni();
		}

		public LocalDate getFFin() {
			return this.curso.getfFin();
		}

		public BigDecimal getValoracion() {
			return this.curso.getValoracion();
		}

		public Integer getUsuariosRegistrados() {
			return this.curso.getUsuariosRegistrados();
		}

		public List<ContenidoExtraDTO> getContenidosExtra() {
			return this.curso.getContenidosExtra();
		}
		public Integer getNumVistas() {
			return this.curso.getNumVistas();
		}
		public Integer getNumValoraciones() {
			return this.curso.getNumValoraciones();
		}
		public void setContenidosExtra(List<ContenidoExtraDTO> contenidosExtra) {
			this.contenidosExtra = contenidosExtra;
		}

        
}




