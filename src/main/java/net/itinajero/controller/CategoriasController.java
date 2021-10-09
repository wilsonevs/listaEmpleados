package net.itinajero.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.itinajero.model.Categoria;
import net.itinajero.service.ICategoriaService;

@Controller
@RequestMapping(value="/categorias")
public class CategoriasController {
	
	@Autowired
	ICategoriaService categoriaService;
		
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Categoria> listaCarga=categoriaService.buscarTodas();
		model.addAttribute("listaCarga", listaCarga);
		return "categorias/listCategorias";
	}
	
	
	@GetMapping("/create")
	public String crear(Categoria categoria, Model model) {
		return "categorias/formCategoria";
	}
	
	
	@PostMapping("/save")
	public String guardar(Categoria categoria ,BindingResult resultError ,RedirectAttributes redirect) {
		
		if(resultError.hasErrors()) {
			for (ObjectError mostrarError : resultError.getAllErrors()) {
				System.out.println("Error es: "+mostrarError.getDefaultMessage());
			}
			return "categorias/formCategoria";
		}
		
		categoriaService.guardar(categoria);
		redirect.addFlashAttribute("mensaje", "Registro Guardado");
		return "redirect:/categorias/index";
	}
	
}
