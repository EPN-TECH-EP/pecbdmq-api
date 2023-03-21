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

import epntech.cbdmq.pe.dominio.admin.Paralelo;
import epntech.cbdmq.pe.repositorio.admin.ParaleloRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ParaleloTest {

	@Autowired
	private ParaleloRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse("2029-01-01", formatter));
        
        Paralelo obj = new Paralelo();
		obj.setNombreParalelo("Test");
		obj.setEstado("activo");

		Paralelo datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("Test", datos.getNombreParalelo());
		assertEquals("activo", datos.getEstado());
	}

	@Test
	@Order(2)
	public void testBuscar() {
		String nombre = "Test";

		Paralelo obj = new Paralelo();
		obj.setNombreParalelo(nombre);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<Paralelo> obj1 = repo.findByNombreParalelo(nombre);

		assertThat(obj1.get().getNombreParalelo()).isEqualTo(nombre);
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		Paralelo obj = new Paralelo();
		obj.setNombreParalelo(nombre);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<Paralelo> obj1 = repo.findByNombreParalelo("Test");

		String datoNuevo = "NombreNuevo";

		obj.setNombreParalelo(datoNuevo);
		obj.setCodParalelo(obj1.get().getCodParalelo());

		Optional<Paralelo> objModificado = repo.findByNombreParalelo(datoNuevo);
		assertThat(objModificado.get().getNombreParalelo()).isEqualTo(datoNuevo);
	}

	@Test
	@Order(4)
	public void testListar() {
		List<Paralelo> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		Paralelo obj = new Paralelo();
		obj.setNombreParalelo(nombre);
		obj.setEstado("activo");
		repo.save(obj);

		int id = repo.findByNombreParalelo("Test").get().getCodParalelo();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
