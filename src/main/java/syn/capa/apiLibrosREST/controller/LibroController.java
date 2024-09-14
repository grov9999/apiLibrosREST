package syn.capa.apiLibrosREST.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import syn.capa.apiLibrosREST.beans.Libro;
import syn.capa.apiLibrosREST.beans.LibroDetalle;
import syn.capa.apiLibrosREST.service.impl.LibroServiceImpl;

@RestController // Indica que esta clase es un controlador REST
public class LibroController {
	@Autowired
	private LibroServiceImpl libroService; // Inyección de dependencia para el servicio de libros

	/* Endpoint para obtener un libro por su ID */
	@GetMapping("/libros/{id}")
	public Libro getLibroById(@PathVariable Integer id) {
		return libroService.searchById(id);
	}

	/* Endpoint para listar todos los libros */
	@GetMapping("/libros")
	public List<Libro> listarLibros() {
		return libroService.listarLibros();
	}

	/* Endpoint para listar todos los libros activos */
	@GetMapping("/librosNew")
	public ResponseEntity<List<Libro>> listarLibrosActivos() {
		List<Libro> libros = libroService.listarLibros();
		return ResponseEntity.ok(libros);
	}

	/* Endpoint para insertar un nuevo libro */
	@PostMapping("/libros1")
	@ResponseStatus(HttpStatus.CREATED) // Indica que la respuesta será 201 Created si la función es exitosa
	public void insertarLibro(@RequestBody Libro libro) {
		boolean insertado = libroService.insertarLibro(libro);
		if (!insertado) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al insertar el libro");
		}
	}

	/* End point para actualizar un libro existente */
	@PutMapping("/libros2/{id}")
	public ResponseEntity<Void> actualizarLibro(@PathVariable Integer id, @RequestBody Libro libro) {
		libro.setId(id);
		boolean actualizado = libroService.actualizarLibro(libro);
		if (actualizado) {
			return ResponseEntity.ok().build(); // Retorna 200 OK si la actualización es exitosa
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Errro
																					// si falla
		}
	}

	/* Endpoint para actualizar el estado de un libro por su ID */
	@DeleteMapping("/libros3/{id}")
	public ResponseEntity<Void> borrarLibro(@PathVariable Integer id) {
		boolean actualizado = libroService.borrarLibro(id);
		if (actualizado) {
			return ResponseEntity.ok().build(); // Retorna 200 OK si la actualización es exitosa
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 Internal Server Error
																					// si falla
		}
	}

	@GetMapping("/libros-detalles")
	public ResponseEntity<List<LibroDetalle>> ListarLibrosDetalles() {
		List<LibroDetalle> libros = libroService.ListarLibrosDetalles();
		return ResponseEntity.ok(libros);
	}
}
