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

import epntech.cbdmq.pe.dominio.admin.Semestre;
import epntech.cbdmq.pe.repositorio.admin.SemestreRepository;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class SemestreTest {

	@Autowired
	private SemestreRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse("2029-01-01", formatter));
        
        Semestre obj = new Semestre();
		obj.setSemestre("Test");
		obj.setEstado("activo");

		Semestre datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("Test", datos.getSemestre());
		assertEquals("activo", datos.getEstado());
	}

	@Test
	@Order(2)
	public void testBuscar() {
		String nombre = "Test";

		Semestre obj = new Semestre();
		obj.setSemestre(nombre);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<Semestre> obj1 = repo.findBySemestreIgnoreCase(nombre);

		assertThat(obj1.get().getSemestre()).isEqualTo(nombre);
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		Semestre obj = new Semestre();
		obj.setSemestre(nombre);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<Semestre> obj1 = repo.findBySemestreIgnoreCase("Test");

		String datoNuevo = "NombreNuevo";

		obj.setSemestre(datoNuevo);
		obj.setCodSemestre(obj1.get().getCodSemestre());

		Optional<Semestre> objModificado = repo.findBySemestreIgnoreCase(datoNuevo);
		assertThat(objModificado.get().getSemestre()).isEqualTo(datoNuevo);
	}

	@Test
	@Order(4)
	public void testListar() {
		List<Semestre> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		Semestre obj = new Semestre();
		obj.setSemestre(nombre);
		obj.setEstado("activo");
		repo.save(obj);

		int id = repo.findBySemestreIgnoreCase("Test").get().getCodSemestre();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
