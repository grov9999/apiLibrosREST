package syn.capa.apiLibrosREST.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import syn.capa.apiLibrosREST.beans.Libro;

public class LibroServiceImplTest {

	@Mock
	private JdbcTemplate jdbcTemplate;

	@Mock
	private Connection connection;

	@Mock
	private CallableStatement callableStatement;

	@Mock
	private ResultSet resultSet;

	@InjectMocks
	private LibroServiceImpl libroServiceImpl;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testMapearUsuario() throws SQLException {
		// Simula los datos que devolvería el ResultSet
		when(resultSet.getInt("id")).thenReturn(1);
		when(resultSet.getString("titulo")).thenReturn("Daniel");
		when(resultSet.getInt("autorId")).thenReturn(11);
		when(resultSet.getInt("generoId")).thenReturn(22);
		when(resultSet.getInt("anioPublicacion")).thenReturn(2);
		when(resultSet.getString("isbn")).thenReturn("33");
		when(resultSet.getString("descripcion")).thenReturn("44");
		// when(resultSet.getBoolean("estado")).thenReturn(false);

		// llama al método bajo prueba
		Libro result = libroServiceImpl.mapearLibro(resultSet);

		// Verifica que el resultado no sea null
		assertNotNull(result);

		// Verifica que los datos mapeados sean los esperados
		assertEquals(1, result.getId());
		assertEquals("Daniel", result.getTitulo());
		assertEquals(11, result.getAutorId());
		assertEquals(22, result.getGeneroId());
		assertEquals(2, result.getAnioPublicacion());
		assertEquals("33", result.getIsbn());
		assertEquals("44", result.getDescripcion());

		// Verifica que se llamaron los métodos correctos en el resultSet
		verify(resultSet).getInt("id");
		verify(resultSet).getString("titulo");
		verify(resultSet).getInt("autorId");
		verify(resultSet).getInt("generoId");
		verify(resultSet).getInt("anioPublicacion");
		verify(resultSet).getString("isbn");
		verify(resultSet).getString("descripcion");
	}

	@Test
	void testSearchById_LibroExists() throws SQLException {
		Integer libroId = 2;
		
		// Simula los datos que devolvería el ResultSet
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getInt("id")).thenReturn(libroId);
		when(resultSet.getString("titulo")).thenReturn("El Quijote");
		when(resultSet.getInt("autorId")).thenReturn(1);
		when(resultSet.getInt("generoId")).thenReturn(1);
		when(resultSet.getInt("anioPublicacion")).thenReturn(1605);
		when(resultSet.getString("isbn")).thenReturn("978-3-16-148410-0");
		when(resultSet.getString("descripcion")).thenReturn("Una novela clásica de la literatura española.");
		//when(resultSet.getBoolean("estado")).thenReturn(true);

		// Configura la simulación del CallableStatement y Connection
		when(connection.prepareCall("{call SP_BuscarLibroPorId(?)}")).thenReturn(callableStatement);
		when(callableStatement.executeQuery()).thenReturn(resultSet);

		// Simula la ejecución del jdbcTemplate.execute()
		when(jdbcTemplate.execute(any(ConnectionCallback.class))).thenAnswer(invocation -> {
			ConnectionCallback<Libro> action = invocation.getArgument(0);
			return action.doInConnection(connection);
		});

		// Llama al método bajo prueba
		Libro result = libroServiceImpl.searchById(libroId);

		// Verifica que se hayan llamado los métodos correctos
		verify(callableStatement).setInt(1, libroId);
		assertNotNull(result);
		assertEquals("El Quijote", result.getTitulo());
		assertEquals(1, result.getAutorId());
		assertEquals(1, result.getGeneroId());
		assertEquals(1605, result.getAnioPublicacion());
		assertEquals("978-3-16-148410-0", result.getIsbn());
		assertEquals("Una novela clásica de la literatura española.", result.getDescripcion());
		//assertEquals(true,result.getEstado());
		
	}
}
