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
		aula.setNombreAula("Test");
		aula.setCapacidad(5);
		aula.setNumeroImpresoras(3);
		aula.setPcInstructor("test");
		aula.setTipoInternet("wifi");
		aula.setNumeroPcs(3);
		aula.setNumeroProyectores(1);
		aula.setSalaOcupada(true);
		aula.setTipoAula("teatro");
		aula.setEstado("activo");

		Aula datos = repo.save(aula);
		assertNotNull(datos);

		assertEquals("Test", datos.getNombreAula());
		assertEquals(5, datos.getCapacidad());
		assertEquals(1, datos.getNumeroProyectores());
		assertEquals("activo", datos.getEstado());
	}

	@Test
	@Order(2)
	public void testBuscar() {
		String nombre = "Test";

		Aula aula = new Aula();
		aula.setNombreAula(nombre);
		aula.setEstado("activo");

		Aula datos = repo.save(aula);

		Optional<Aula> aula1 = repo.findByNombreAulaIgnoreCase(nombre);

		assertThat(aula1.get().getNombreAula()).isEqualTo(nombre);
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		Aula aula = new Aula();
		aula.setNombreAula(nombre);
		aula.setEstado("activo");

		Aula datos = repo.save(aula);

		Optional<Aula> aula1 = repo.findByNombreAulaIgnoreCase("Test");

		String nombreNuevo = "NombreNuevo";
		// Aula aula = new Aula();
		aula.setNombreAula(nombreNuevo);
		aula.setCodAula(aula1.get().getCodAula());

		Optional<Aula> aulaModificada = repo.findByNombreAulaIgnoreCase(nombreNuevo);
		assertThat(aulaModificada.get().getNombreAula()).isEqualTo(nombreNuevo);
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
		aula.setNombreAula(nombre);
		aula.setEstado("activo");

		Aula datos = repo.save(aula);
		int id = repo.findByNombreAulaIgnoreCase("Test").get().getCodAula();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
