package es.alumno.uned.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import es.alumno.uned.dto.ContenidoExtraDTO;
import es.alumno.uned.model.entities.TipoContenido;
import es.alumno.uned.service.ContenidoExtraService;

@Controller
public class CursoContenidoController {

	@Autowired
	ContenidoExtraService contenidoExtraService;
	
	@GetMapping("/contenido-extra/{idCurso}/{idContenido}")
	public ResponseEntity<?> consultarContenido(@PathVariable Long idCurso,
			@PathVariable Long idContenido) {
		
		
		ContenidoExtraDTO contenido = contenidoExtraService.getContenido(idCurso, idContenido);
		if (contenido.getTipoContenido() == TipoContenido.EXTERNO) {
			
			return ResponseEntity.status(HttpStatus.FOUND) // HTTP 302
                    .location(URI.create(contenido.getUri()))
                    .build();
		}
		Resource resource = contenidoExtraService.getResource(contenido.getUri());
		if (resource == null) {
			throw new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND, "El archivo físico no se encuentra en el servidor");
		}
		String contentType = contenido.getContentType() != null ? contenido.getContentType() : "application/pdf";
		
		
		
		return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + contenido.getNombreReal() + "\"")
                .body(resource);
	}
}
