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
import org.springframework.test.annotation.Rollback;

import epntech.cbdmq.pe.dominio.admin.Convocatoria;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ConvocatoriaTest {

	@Autowired
	private ConvocatoriaRepository repo;

	@Test
	@Order(1)
	void testGuardar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse("2029-01-01", formatter));
        
		Convocatoria obj = new Convocatoria();
		obj.setNombre("Test");
		obj.setFechaInicioConvocatoria(date);
		obj.setEstado("activo");

		Convocatoria datos = repo.save(obj);
		assertNotNull(datos);
		System.out.println("datos.getFechaInicioConvocatoria(): " + datos.getFechaInicioConvocatoria());
		assertEquals("Test", datos.getNombre());
		assertEquals(date, datos.getFechaInicioConvocatoria());
		assertEquals("activo", datos.getEstado());
	}

	@Test
	@Order(2)
	public void testBuscar() {
		String nombre = "Test";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse("2029-01-01", formatter));

		Convocatoria obj = new Convocatoria();
		obj.setNombre(nombre);
		obj.setFechaInicioConvocatoria(date);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<Convocatoria> obj1 = repo.findByNombreIgnoreCase(nombre);

		assertThat(obj1.get().getNombre()).isEqualTo(nombre);
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		Convocatoria obj = new Convocatoria();
		obj.setNombre(nombre);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<Convocatoria> obj1 = repo.findByNombreIgnoreCase("Test");

		String datoNuevo = "NombreNuevo";

		obj.setNombre(datoNuevo);
		obj.setCodConvocatoria(obj1.get().getCodConvocatoria());

		Optional<Convocatoria> objModificado = repo.findByNombreIgnoreCase(datoNuevo);
		assertThat(objModificado.get().getNombre()).isEqualTo(datoNuevo);
	}

	@Test
	@Order(4)
	public void testListar() {
		List<Convocatoria> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		Convocatoria obj = new Convocatoria();
		obj.setNombre(nombre);
		obj.setEstado("activo");
		repo.save(obj);

		int id = repo.findByNombreIgnoreCase("Test").get().getCodConvocatoria();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
