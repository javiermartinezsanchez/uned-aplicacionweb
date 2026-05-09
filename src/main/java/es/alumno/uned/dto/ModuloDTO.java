package es.alumno.uned.dto;

import java.time.LocalDateTime;

import es.alumno.uned.model.entities.TipoModulo;
import jakarta.validation.constraints.*;

public class ModuloDTO {

        Long id;

//        @NotNull(message = "El curso es obligatorio")
//        Long cursoId,

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 100, message = "El título no puede superar los 100 caracteres")
        String titulo;

        @NotBlank(message = "La descripción es obligatoria")
        @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
        String descripcion;

        @NotBlank(message = "El contenido es obligatorio")
        String contenido;
        
       
//        @NotNull(message = "El peso es obligatorio")
//        @Min(value = 1, message = "El peso mínimo es 1")
//        @Max(value = 100, message = "El peso máximo es 100")
//        Integer peso,

        @NotNull(message = "El tipo de módulo es obligatorio")
        TipoModulo tipo;
        private LocalDateTime fIns;
        private String userIns;
        
        public ModuloDTO () {};
        public ModuloDTO(Long id,
				@NotBlank(message = "El título es obligatorio") @Size(max = 100, message = "El título no puede superar los 100 caracteres") String titulo,
				@NotBlank(message = "La descripción es obligatoria") @Size(max = 500, message = "La descripción no puede superar los 500 caracteres") String descripcion,
				@NotBlank(message = "El contenido es obligatorio") String contenido,
				@NotNull(message = "El tipo de módulo es obligatorio") TipoModulo tipo,
				LocalDateTime fIns,
				String userIns
				)
				 {
			super();
			this.id = id;
			this.titulo = titulo;
			this.descripcion = descripcion;
			this.contenido = contenido;
			this.tipo = tipo;
			this.fIns = fIns;
			this.userIns = userIns;
		}
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
		public TipoModulo getTipo() {
			return tipo;
		}
		public void setTipo(TipoModulo tipo) {
			this.tipo = tipo;
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
}