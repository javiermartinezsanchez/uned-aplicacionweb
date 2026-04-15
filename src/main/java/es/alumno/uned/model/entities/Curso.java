package es.alumno.uned.model.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Cursos")
public class Curso {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="curso_id")
	private Long id;
	@Column(name="Titulo", length=50, nullable=false)
	private String titulo;
	@Column(name="Descripcion", length=255, nullable=false)
	private String descripcion;
	@Column(name="FECHA_INI")
	private LocalDateTime fIni;
	@Column(name="FECHA_FIN")
	private LocalDateTime fFin;
	@Column(name="FECHA_INS")
	private LocalDateTime fIns;
	@Column(name="USER_INS")
	private String userIns;
	
}
