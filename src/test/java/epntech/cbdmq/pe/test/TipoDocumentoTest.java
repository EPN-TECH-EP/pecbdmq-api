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

import epntech.cbdmq.pe.dominio.admin.TipoDocumento;
import epntech.cbdmq.pe.repositorio.admin.TipoDocumentoRepository;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TipoDocumentoTest {

	@Autowired
	private TipoDocumentoRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse("2029-01-01", formatter));
        
        TipoDocumento obj = new TipoDocumento();
		obj.setTipoDocumento("Test");
		obj.setEstado("activo");

		TipoDocumento datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("Test", datos.getTipoDocumento());
		assertEquals("activo", datos.getEstado());
	}

	@Test
	@Order(2)
	public void testBuscar() {
		String nombre = "Test";

		TipoDocumento obj = new TipoDocumento();
		obj.setTipoDocumento(nombre);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<TipoDocumento> obj1 = repo.findByTipoDocumentoIgnoreCase(nombre);

		assertThat(obj1.get().getTipoDocumento()).isEqualTo(nombre);
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		TipoDocumento obj = new TipoDocumento();
		obj.setTipoDocumento(nombre);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<TipoDocumento> obj1 = repo.findByTipoDocumentoIgnoreCase("Test");

		String datoNuevo = "NombreNuevo";

		obj.setTipoDocumento(datoNuevo);
		obj.setCodigoDocumento(obj1.get().getCodigoDocumento());

		Optional<TipoDocumento> objModificado = repo.findByTipoDocumentoIgnoreCase(datoNuevo);
		assertThat(objModificado.get().getTipoDocumento()).isEqualTo(datoNuevo);
	}

	@Test
	@Order(4)
	public void testListar() {
		List<TipoDocumento> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		TipoDocumento obj = new TipoDocumento();
		obj.setTipoDocumento(nombre);
		obj.setEstado("activo");
		repo.save(obj);

		int id = repo.findByTipoDocumentoIgnoreCase("Test").get().getCodigoDocumento();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
