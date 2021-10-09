package net.itinajero.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
import net.itinajero.model.Vacante;
import net.itinajero.service.ICategoriaService;
import net.itinajero.service.IVacantesService;
import net.itinajero.util.Utileria;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Value("${empleosapp.ruta.imagenes}")
	private String ruta;
	
	@Autowired
	private IVacantesService serviceVacantes;
	
	@Autowired
	private ICategoriaService serviceCategorias;
	
	@GetMapping("/index")
	public String mostarIndex(Model model) {
		 List<Vacante> listaVacantes=serviceVacantes.buscarTodas();
		 model.addAttribute("listaVacantes", listaVacantes);
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/create")
	public String crear(Vacante vacante, Model model ) {
		 
		model.addAttribute("listaCategorias", serviceCategorias.buscarTodas());
		return "vacantes/formVacante";
	}
	
	
	@GetMapping("/delete")
	public String eliminar(@RequestParam("id") int idVacante, Model model) {
		System.out.println("Borrando vacante con id: " + idVacante);
		model.addAttribute("id", idVacante);
		return "mensaje";
	}
	
	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante, Model model) {	
		
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		
		System.out.println("Vacante: " + vacante);
		model.addAttribute("vacante", vacante);
		
		// Buscar los detalles de la vacante en la BD...		
		return "detalle";
	}
	
	
	@PostMapping("/save")
	public String guardar( Vacante vacante,  
			final BindingResult resultError,  
			RedirectAttributes redirectAttributes, 
			@RequestParam("archivoImagen") MultipartFile multiPart) {
		
			if(resultError.hasErrors()) {
				for (ObjectError mostarError : resultError.getAllErrors()) {
					System.out.println("Error es: "+mostarError.getDefaultMessage());
				}
				return "vacantes/formVacante";
			}
			
			if (!multiPart.isEmpty()) {
//				String ruta = "c:/xampp/htdocs/0.1 SpringBoot/empleos/src/main/resources/static/img/"; // Windows
				String nombreImagen = Utileria.guardarArchivo(multiPart, ruta);
				if (nombreImagen != null){ // La imagen si se subio
				// Procesamos la variable nombreImagen
					vacante.setImagen(nombreImagen);
				}
			}
			
			serviceVacantes.gurdar(vacante);
			redirectAttributes.addFlashAttribute("msg", "Registro guardado");
			return "redirect:/vacantes/index";
	}

	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}


}
