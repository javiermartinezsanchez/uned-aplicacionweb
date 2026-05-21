package es.alumno.uned.mapper;


import org.springframework.stereotype.Component;

import es.alumno.uned.dto.EstudianteCursoModuloDTO;
import es.alumno.uned.model.entities.EstudianteCursoModulo;
/**
 * Mapper de la entidad EstudianteCursoModulo a EstudianteCursoModuloDTO
 * 
 * <p>toDTO Nos devuelve el DTO de la entidad.
 * 
 */
@Component
public class EstudianteCursoModuloMapper {

	public EstudianteCursoModuloDTO toDTO(EstudianteCursoModulo entidad) {
		EstudianteCursoModuloDTO dto = new EstudianteCursoModuloDTO();
				
		dto.setEstudianteId(entidad.getEstudianteCurso().getId().getEstudianteId());
		dto.setCursoId(entidad.getEstudianteCurso().getId().getCursoId());
		dto.setModuloId(entidad.getModulo().getId()); 
		dto.setTitulo(entidad.getTitulo());
		dto.setCompletado(entidad.getCompletado()) ;
		dto.setOrden(entidad.getOrden());
		dto.setEstado(entidad.getEstado());
		dto.setPeso(entidad.getPeso());
		dto.setFechaCompletado(entidad.getFechaCompletado());
		dto.setFechaUltimoAcceso(entidad.getFechaUltimoAcceso());
		dto.setUrlEntrega(entidad.getUrlEntrega());
		dto.setFechaEntrega(entidad.getFechaEntrega());
		dto.setFechaRevision(entidad.getFechaRevision());
		dto.setCalificacion(entidad.getCalificacion());
		dto.setNotasCalificacion(entidad.getNotasCalificacion());

		return dto;

	}
	
//	public Long getCursoId() {
//	return cursoId;
//}
//public Long getModuloId() {
//	return moduloId;
//}
//public Boolean getCompletado() {
//	return completado;
//}
//public String getTitulo() {
//	return titulo;
//}
//public void setTitulo(String titulo) {
//	this.titulo = titulo;
//}
//public Integer getOrden() {
//	return orden;
//}
//public Integer getPeso() {
//	return peso;
//}
//public void setPeso(Integer peso) {
//	this.peso = peso;
//}
//public EstadoCursoModulo getEstado() {
//	return estado;
//}
//public LocalDateTime getFechaCompletado() {
//	return fechaCompletado;
//}
//public LocalDateTime getFechaUltimoAcceso() {
//	return fechaUltimoAcceso;
//}
//public String getUrlEntrega() {
//	return urlEntrega;
//}
//public void setUrlEntrega(String urlEntrega) {
//	this.urlEntrega = urlEntrega;
//}
//public LocalDateTime getFechaEntrega() {
//	return fechaEntrega;
//}
//public void setFechaEntrega(LocalDateTime fechaEntrega) {
//	this.fechaEntrega = fechaEntrega;
//}
//public LocalDateTime getFechaRevision() {
//	return fechaRevision;
//}
//public BigDecimal getCalificacion() {
//	return calificacion;
//}
//public String getNotasCalificacion() {
//	return notasCalificacion;
//}

}
