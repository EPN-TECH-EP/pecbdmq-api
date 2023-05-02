package epntech.cbdmq.pe.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.repositorio.admin.AulaRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class AulaTest {

	@Autowired
	private AulaRepository repo;

	@Test
	@Order(1)
	void testGuardar() {
		Aula aula = new Aula();
		aula.setNombre("Test");
		aula.setCapacidad(5);
		aula.setImpresoras(3);
		aula.setInstructor("test");
		aula.setInternet("wifi");
		aula.setPcs(3);
		aula.setProyectores(1);
		aula.setSalaOcupada("no");
		aula.setTipo("teatro");
		aula.setEstado("activo");

		Aula datos = repo.save(aula);
		assertNotNull(datos);

		assertEquals("Test", datos.getNombre());
		assertEquals(5, datos.getCapacidad());
		assertEquals(1, datos.getProyectores());
		assertEquals("activo", datos.getEstado());
	}

	@Test
	@Order(2)
	public void testBuscar() {
		String nombre = "Test";

		Aula aula = new Aula();
		aula.setNombre(nombre);
		aula.setEstado("activo");

		Aula datos = repo.save(aula);

		Optional<Aula> aula1 = repo.findByNombreIgnoreCase(nombre);

		assertThat(aula1.get().getNombre()).isEqualTo(nombre);
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		Aula aula = new Aula();
		aula.setNombre(nombre);
		aula.setEstado("activo");

		Aula datos = repo.save(aula);

		Optional<Aula> aula1 = repo.findByNombreIgnoreCase("Test");

		String nombreNuevo = "NombreNuevo";
		// Aula aula = new Aula();
		aula.setNombre(nombreNuevo);
		aula.setCodigo(aula1.get().getCodigo());

		Optional<Aula> aulaModificada = repo.findByNombreIgnoreCase(nombreNuevo);
		assertThat(aulaModificada.get().getNombre()).isEqualTo(nombreNuevo);
	}

	@Test
	@Order(4)
	public void testListar() {
		List<Aula> aulas = repo.findAll();
		assertThat(aulas).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		Aula aula = new Aula();
		aula.setNombre(nombre);
		aula.setEstado("activo");

		Aula datos = repo.save(aula);
		int id = repo.findByNombreIgnoreCase("Test").get().getCodigo();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
