package syn.capa.apiLibrosREST.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import syn.capa.apiLibrosREST.beans.Libro;
import syn.capa.apiLibrosREST.beans.LibroDetalle;
import syn.capa.apiLibrosREST.service.impl.LibroDetalleServiceImpl;
import syn.capa.apiLibrosREST.service.impl.LibroServiceImpl;

@Controller // Indica que esta clase es un controlador de vistas
public class LibroViewController {

	@Autowired
	private LibroServiceImpl libroService; // Inyección de depndencia para el servicio de libros
	
	@Autowired
	private LibroDetalleServiceImpl libroDetalleService;

	/* End point para obtener un libro por su ID */
	@GetMapping("/libro/{id}")
	@Operation(description = "Encuentra un libro por ID", summary = "Proporciona un ID para buscar un libro especifico en la base de datos")
	public String getLibroById(@PathVariable Integer id, Model model) {
		Libro libro = libroService.searchById(id);
		model.addAttribute("libro", libro);
		return "libroId"; // Nombre de la plantilla Thymelaf
	}

	/* Endpoint para listar todos los libros */
	@GetMapping("/libro")
	@Operation(description = "Lista todos los libros", summary = "Proporciona una lista de todos los libros en la base de datos")
	public String listarLibros(Model model) {
		List<Libro> libros = libroService.listarLibros();
		model.addAttribute("libro", libros);
		return "listarLibros"; // Nombre de la plantilla Thymeleaf
	}

	/* Endpoint para listar detalles de los libros */
	@GetMapping("/libro-detalle")
	@Operation(description = "Lista todos los libros con detalles", summary = "Proporciona una lista detallada de todos los libros en la base de datos")
	public String listarLibrosDetalle(Model model) {
		List<LibroDetalle> libros = libroDetalleService.listarLibrosDetalle();
		model.addAttribute("libros", libros);
		return "listarLibrosDetalle";
	}
	
	@DeleteMapping("/libro/borrar/{id}")
	@Operation(description = "Borra un libro por ID", summary = "Proporciona un ID para borrar un libro específico en la base de datos")
	public String borrarLibro(@PathVariable Integer id,Model model) {
		boolean resultado = libroService.borrarLibro(id);
		model.addAttribute("resultado", resultado?"Libro eliminado":"Error al eliminar libro");
		return "borrarLibro";
	}
}
