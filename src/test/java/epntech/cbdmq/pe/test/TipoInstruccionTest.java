package epntech.cbdmq.pe.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import epntech.cbdmq.pe.dominio.admin.TipoInstruccion;
import epntech.cbdmq.pe.repositorio.admin.TipoInstruccionRepository;



@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TipoInstruccionTest {

	@Autowired
	private TipoInstruccionRepository repo;
	
	
	@Test
	@Order(1)
	void testGuardar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse("2029-01-01", formatter));
        
        TipoInstruccion obj = new TipoInstruccion();
		obj.setTipoInstruccion("Test");
		obj.setEstado("activo");

		TipoInstruccion datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("Test", datos.getTipoInstruccion());
		assertEquals("activo", datos.getEstado());
	}

	@Test
	@Order(2)
	public void testBuscar() {
		String TipoInstruccion = "Test";

		TipoInstruccion obj = new TipoInstruccion();
		obj.setTipoInstruccion(TipoInstruccion);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<TipoInstruccion> obj1 = repo.findByTipoInstruccionIgnoreCase(TipoInstruccion);

		assertThat(obj1.get().getTipoInstruccion()).isEqualTo(TipoInstruccion);
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String TipoInstruccion = "Test";

		TipoInstruccion obj = new TipoInstruccion();
		obj.setTipoInstruccion(TipoInstruccion);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<TipoInstruccion> obj1 = repo.findByTipoInstruccionIgnoreCase("Test");

		String datoNuevo = "TipoInstruccionNuevo";

		obj.setTipoInstruccion(datoNuevo);
		obj.setCodigoTipoInstruccion(obj1.get().getCodigoTipoInstruccion());

		Optional<TipoInstruccion> objModificado = repo.findByTipoInstruccionIgnoreCase(datoNuevo);
		assertThat(objModificado.get().getTipoInstruccion()).isEqualTo(datoNuevo);
	}

	@Test
	@Order(4)
	public void testListar() {
		List<TipoInstruccion> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void testEliminar() {
		String TipoInstruccion = "Test";

		TipoInstruccion obj = new TipoInstruccion();
		obj.setTipoInstruccion(TipoInstruccion);
		obj.setEstado("activo");
		repo.save(obj);

		int id = repo.findByTipoInstruccionIgnoreCase("Test").get().getCodigoTipoInstruccion();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
	
}
