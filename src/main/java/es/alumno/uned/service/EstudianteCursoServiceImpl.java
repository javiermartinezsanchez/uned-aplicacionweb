package es.alumno.uned.service;

import org.springframework.web.multipart.MultipartFile;

import es.alumno.uned.exception.ModuloException;
import es.alumno.uned.model.entities.Estudiante;
import es.alumno.uned.model.entities.Modulo;
import es.alumno.uned.model.entities.TipoModulo;

public class EstudianteCursoServiceImpl implements EstudianteCursoService {

	@Override
	public void completarModulo(Modulo modulo, Estudiante estudiante, MultipartFile entregable) {
		switch (modulo.getTipo()) {

        case TipoModulo.ENTREGA_OBLIGATORIA:
            if (entregable == null || entregable.isEmpty()) {
                throw new ModuloException("Debe subir un archivo para completar este módulo");
            }
            guardarArchivo(entregable);
            marcarComoCompletado(estudiante, modulo);
            break;

        case TipoModulo.FINALIZACION_MANUAL:
            marcarComoCompletado(estudiante, modulo);
            break;
    }
		
	}

	private void guardarArchivo(MultipartFile entregable) {
		// TODO Auto-generated method stub
		
	}

	private void marcarComoCompletado(Estudiante estudiante, Modulo modulo) {
		// TODO Auto-generated method stub
		
	}

}
