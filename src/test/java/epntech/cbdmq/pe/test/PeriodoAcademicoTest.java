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

import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PeriodoAcademicoTest {

	@Autowired
	private PeriodoAcademicoRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse("2029-01-01", formatter));
        
        PeriodoAcademico obj = new PeriodoAcademico();
		obj.setDescripcion("Test");
		obj.setModuloEstados(1);
		obj.setFechaInicio(date);
		obj.setEstado("activo");

		PeriodoAcademico datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("Test", datos.getDescripcion());
		assertEquals(1, datos.getModuloEstados());
		assertEquals(date, datos.getFechaInicio());
		assertEquals("activo", datos.getEstado());
	}

	@Test
	@Order(2)
	public void testBuscar() {
		String nombre = "Test";

		PeriodoAcademico obj = new PeriodoAcademico();
		obj.setDescripcion("Test");
		obj.setModuloEstados(1);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<PeriodoAcademico> obj1 = repo.findByDescripcion(nombre);

		assertThat(obj1.get().getDescripcion()).isEqualTo(nombre);
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		PeriodoAcademico obj = new PeriodoAcademico();
		obj.setDescripcion("Test");
		obj.setModuloEstados(1);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<PeriodoAcademico> obj1 = repo.findByDescripcion("Test");

		String datoNuevo = "NombreNuevo";

		obj.setDescripcion(datoNuevo);
		obj.setCodigo(obj1.get().getCodigo());

		Optional<PeriodoAcademico> objModificado = repo.findByDescripcion(datoNuevo);
		assertThat(objModificado.get().getDescripcion()).isEqualTo(datoNuevo);
	}

	@Test
	@Order(4)
	public void testListar() {
		List<PeriodoAcademico> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		PeriodoAcademico obj = new PeriodoAcademico();
		obj.setDescripcion("Test");
		obj.setModuloEstados(1);
		obj.setEstado("activo");
		repo.save(obj);

		int id = repo.findByDescripcion("Test").get().getCodigo();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
