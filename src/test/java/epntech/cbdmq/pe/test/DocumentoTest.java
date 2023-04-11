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

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DocumentoTest {

	@Autowired
	private DocumentoRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse("2029-01-01", formatter));
        
        Documento obj = new Documento();
		obj.setNombre("Test");
		obj.setAutorizacion("Autorizacion");
		obj.setDescripcion("test-abc");
		obj.setNombre("Test");
		obj.setRuta("c:\\documentos");
		obj.setEstado("activo");

		Documento datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("Test", datos.getNombre());

		assertEquals("activo", datos.getEstado());
	}

	@Test
	@Order(2)
	public void testBuscar() {
		String nombre = "Test";

        Documento obj = new Documento();
		obj.setNombre(nombre);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<Documento> obj1 = repo.findByNombre(nombre);

		assertThat(obj1.get().getNombre()).isEqualTo(nombre);
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		Documento obj = new Documento();
		obj.setNombre(nombre);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<Documento> obj1 = repo.findByNombre("Test");

		String datoNuevo = "NombreNuevo";

		obj.setNombre(datoNuevo);
		obj.setCodigo(obj1.get().getCodigo());

		Optional<Documento> objModificado = repo.findByNombre(datoNuevo);
		assertThat(objModificado.get().getNombre()).isEqualTo(datoNuevo);
	}

	@Test
	@Order(4)
	public void testListar() {
		List<Documento> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		Documento obj = new Documento();
		obj.setNombre(nombre);
		obj.setEstado("activo");
		repo.save(obj);

		int id = repo.findByNombre("Test").get().getCodigo();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
