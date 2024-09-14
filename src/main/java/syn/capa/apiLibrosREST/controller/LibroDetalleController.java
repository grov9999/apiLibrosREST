package syn.capa.apiLibrosREST.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import syn.capa.apiLibrosREST.beans.LibroDetalle;
import syn.capa.apiLibrosREST.service.impl.LibroDetalleServiceImpl;

@RestController
@RequestMapping("/libros")
public class LibroDetalleController {
	
	@Autowired
	private LibroDetalleServiceImpl libroDetalleService;
	
	@GetMapping("/detalles")
	public List<LibroDetalle> listLibroDetalles(){
		return libroDetalleService.listarLibrosDetalle();
	}
}
