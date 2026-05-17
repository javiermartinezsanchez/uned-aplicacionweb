package es.alumno.uned.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.alumno.uned.model.entities.ContenidoExtra;

public interface ContenidoExtraRepository extends JpaRepository<ContenidoExtra, Long>{
	
	ContenidoExtra findByIdAndCursoId(Long id, Long idCurso);

}
