package syn.capa.apiLibrosREST.service;

import java.util.List;

import syn.capa.apiLibrosREST.beans.Libro;

public interface LibroService {

	public Libro searchById(Integer id);

	public List<Libro> listarLibros();
	
	public boolean insertarLibro(Libro libro);

	public boolean actualizarLibro(Libro libro);

	public boolean borrarLibro(Integer id);

}