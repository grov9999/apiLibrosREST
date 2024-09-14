package syn.capa.apiLibrosREST.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import syn.capa.apiLibrosREST.beans.LibroDetalle;
import syn.capa.apiLibrosREST.service.LibroDetalleService;

@Service
public class LibroDetalleServiceImpl implements LibroDetalleService{

	@Autowired
	private JdbcTemplate jdbcTemplate; // Inyección de dependencia para JdbcTemplate

	public List<LibroDetalle> listarLibrosDetalle() {
		String sql = "{call ListarLibrosDetalles()}";
		return jdbcTemplate.query(sql, new LibroDetalleRowMapper());
	}

	private static class LibroDetalleRowMapper implements RowMapper<LibroDetalle> {
		@Override
		public LibroDetalle mapRow(ResultSet rs, int rowNum) throws SQLException {
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
}
