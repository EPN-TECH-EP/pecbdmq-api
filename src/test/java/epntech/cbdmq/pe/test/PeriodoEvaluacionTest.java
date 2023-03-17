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

import epntech.cbdmq.pe.dominio.admin.PeriodoEvaluacion;
import epntech.cbdmq.pe.repositorio.admin.PeriodoEvaluacionRepository;



@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PeriodoEvaluacionTest {

	@Autowired
	private PeriodoEvaluacionRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse("2029-01-01", formatter));
        
        PeriodoEvaluacion obj = new PeriodoEvaluacion();
		obj.setDescripcion("Test");
		obj.setCodModulo(1);
		obj.setFechaInicio(date);
		obj.setEstado("activo");

		PeriodoEvaluacion datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("Test", datos.getDescripcion());
		assertEquals(1, datos.getCodModulo());
		assertEquals(date, datos.getFechaInicio());
		assertEquals("activo", datos.getEstado());
	}

	@Test
	@Order(2)
	public void testBuscar() {
		String nombre = "Test";

		PeriodoEvaluacion obj = new PeriodoEvaluacion();
		obj.setDescripcion("Test");
		obj.setCodModulo(1);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<PeriodoEvaluacion> obj1 = repo.findByDescripcion(nombre);

		assertThat(obj1.get().getDescripcion()).isEqualTo(nombre);
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		PeriodoEvaluacion obj = new PeriodoEvaluacion();
		obj.setDescripcion("Test");
		obj.setCodModulo(1);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<PeriodoEvaluacion> obj1 = repo.findByDescripcion("Test");

		String datoNuevo = "NombreNuevo";

		obj.setDescripcion(datoNuevo);
		obj.setCodPeriodoEvaluacion(obj1.get().getCodPeriodoEvaluacion());

		Optional<PeriodoEvaluacion> objModificado = repo.findByDescripcion(datoNuevo);
		assertThat(objModificado.get().getDescripcion()).isEqualTo(datoNuevo);
	}

	@Test
	@Order(4)
	public void testListar() {
		List<PeriodoEvaluacion> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		PeriodoEvaluacion obj = new PeriodoEvaluacion();
		obj.setDescripcion("Test");
		obj.setCodModulo(null);
		obj.setEstado("activo");
		repo.save(obj);

		int id = repo.findByDescripcion("Test").get().getCodPeriodoEvaluacion();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
