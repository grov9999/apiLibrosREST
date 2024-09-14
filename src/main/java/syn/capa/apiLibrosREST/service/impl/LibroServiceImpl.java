package syn.capa.apiLibrosREST.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import syn.capa.apiLibrosREST.beans.Libro;
import syn.capa.apiLibrosREST.beans.LibroDetalle;
import syn.capa.apiLibrosREST.service.LibroService;

@Service
public class LibroServiceImpl implements LibroService {

	@Autowired
	private JdbcTemplate jdbcTemplate; // Inyección de dependencia para JdbcTemplate

	/*
	 * Este méotodo devuelve un saludo concatenando "Auto: " con el nombre del autor
	 */
	public String saludo(String autor) {
		return "Autor: ".concat(autor);
	}

	/* Este méotod busca un libro en la base de datos por su ID */
	public Libro searchById(Integer id) {
		return jdbcTemplate.execute((Connection conn) -> {
			try (CallableStatement cs = conn.prepareCall("{call SP_BuscarLibroPorId(?)}")) {
				cs.setInt(1, id); // Establece el parámetro ID en el procedimiento almacenado
				try (ResultSet rs = cs.executeQuery()) {
					if (rs.next()) {
						return mapearLibro(rs); // Mapea el resultado a un objeto Libro
					} else {
						return null; // Retorna null si no se encuentra el libro
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		});
	}

	public List<Libro> listarLibros() {
		return jdbcTemplate.execute((Connection conn) -> {
			List<Libro> libros = new ArrayList<>();
			try (CallableStatement cs = conn.prepareCall("{call SP_ListarLibros()}");
					ResultSet rs = cs.executeQuery()) {
				while (rs.next()) {
					libros.add(mapearLibro(rs));
				}
			} catch (SQLException e) {
				System.err.println("Error al listar los libros: " + e.getMessage());
			}
			return libros;
		});
	}


	/* Este método inserta un nuevo libro en la base de datos */
	public boolean insertarLibro(Libro libro) {
		return jdbcTemplate.execute((Connection conn) -> {
			try (CallableStatement cs = conn.prepareCall("{call SP_InsertarLibro(?, ?, ?, ?, ?, ?, ?)}")) {
				cs.setInt(1, libro.getId());
				cs.setString(2, libro.getTitulo());
				cs.setInt(3, libro.getAutorId());
				cs.setInt(4, libro.getGeneroId());
				cs.setInt(5, libro.getAnioPublicacion());
				cs.setString(6, libro.getIsbn());
				cs.setString(7, libro.getDescripcion());
				cs.execute(); // Ejecuta el procedimiento almacenado
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		});
	}

	/* Este método actualiza un libro existente en la base de datos */
	public boolean actualizarLibro(Libro libro) {
		return jdbcTemplate.execute((Connection conn) -> {
			try (CallableStatement cs = conn.prepareCall("{call SP_ActualizarLibro(?, ?, ?, ?, ?, ?, ?, ?)}")) {
				cs.setInt(1, libro.getId());
				cs.setString(2, libro.getTitulo());
				cs.setInt(3, libro.getAutorId());
				cs.setInt(4, libro.getGeneroId());
				cs.setInt(5, libro.getAnioPublicacion());
				cs.setString(6, libro.getIsbn());
				cs.setString(7, libro.getDescripcion());
				cs.setBoolean(8, libro.getEstado());
				cs.execute(); // Ejecuta el procedimiento almacenado
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		});
	}

	/* Este método borra un libro de la base de datos por su ID */
	public boolean borrarLibro(Integer id) {
		return jdbcTemplate.execute((Connection conn) -> {
			try (CallableStatement cs = conn.prepareCall("{call SP_BorrarLibro(?)}")) {
				cs.setInt(1, id); // Establece el parámetro ID en el procedimiento almacenado
				cs.execute(); // Ejecuta el procedimiento almacenado
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		});
	}
	
	/* Este método Lista un Inner Join de las tablas autores,generos y libros*/
	public List<LibroDetalle> ListarLibrosDetalles() {
		return jdbcTemplate.execute((Connection conn) -> {
			List<LibroDetalle> libros = new ArrayList<>();
			try (CallableStatement cs = conn.prepareCall("{call ListarLibrosDetalles()}")) {
				ResultSet rs = cs.executeQuery();
				while (rs.next()) {
					libros.add(mapearLibroDetalle(rs)); // Agrega cada libro a la lista
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return libros;
		});
	}

	/* Este método privado mapea un ResultSet a un objeto Libro. */
	public Libro mapearLibro(ResultSet rs) throws SQLException {
		Libro libros = new Libro();
		libros.setId(rs.getInt("id"));
		libros.setTitulo(rs.getString("titulo"));
		libros.setAutorId(rs.getInt("autorId"));
		libros.setGeneroId(rs.getInt("generoId"));
		libros.setAnioPublicacion(rs.getInt("anioPublicacion"));
		libros.setIsbn(rs.getString("isbn"));
		libros.setDescripcion(rs.getString("descripcion"));
		return libros;
	}
	
	/* Este método mapea un ResultSet a un objeto LibroDetalle*/
	public LibroDetalle mapearLibroDetalle(ResultSet rs) throws SQLException {
		LibroDetalle libro = new LibroDetalle();
		libro.setId(rs.getInt("id"));
		libro.setTitulo(rs.getString("titulo"));
		libro.setAutor(rs.getString("autor"));
		libro.setGenero(rs.getString("genero"));
		libro.setAnioPublicacion(rs.getInt("anioPublicacion"));
		libro.setIsbn(rs.getString("isbn"));
		libro.setDescripcion(rs.getString("descripcion"));
		return libro;
	}
}
