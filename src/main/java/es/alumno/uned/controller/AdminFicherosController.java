package es.alumno.uned.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.alumno.uned.model.entities.TipoFichero;
import es.alumno.uned.service.MantenimientoFicherosService;

@Controller
public class AdminFicherosController extends BaseCrudController {

	@Autowired
	MantenimientoFicherosService mantenimientoFicherosService;
	
	@GetMapping("/admin/ficheros")
	public String lista(@RequestParam Map<String, String> params,
			@RequestParam(defaultValue = "0") int page,
			Model modelo) {
		setModeloListado(modelo, "admin/ficheros/adminFicheros","","/admin/borrar-archivo","/");
		var paginacion = mantenimientoFicherosService.listar(paramsToMap(params), getParams(page));
		modelo.addAttribute("paginacion", paginacion);
		modelo.addAttribute("title", "Listado de Áreas Temáticas");
		return modelo.getAttribute("viewName").toString();
	}
	@PostMapping("/admin/borrar-archivo")
	public String borrarArchivo(@RequestParam String nombreArchivo, 
	                            @RequestParam TipoFichero tipoFichero, 
	                            RedirectAttributes redirectAttributes) {
	    
	    // Le pasamos ambos parámetros al servicio para que sepa en qué carpeta borrar
	    boolean eliminado = mantenimientoFicherosService.deleteFile(nombreArchivo, tipoFichero);
	    
	    if (eliminado) {
	        redirectAttributes.addFlashAttribute("mensajeExito", "Archivo " + tipoFichero + " eliminado correctamente.");
	    } else {
	        redirectAttributes.addFlashAttribute("mensajeError", "No se pudo eliminar el recurso.");
	    }
	    return "redirect:/admin/ficheros";
	}

}
