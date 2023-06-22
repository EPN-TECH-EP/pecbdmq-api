package epntech.cbdmq.pe.test;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

import epntech.cbdmq.pe.dominio.admin.Baja;
import epntech.cbdmq.pe.repositorio.admin.BajaRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BajaTest {

	@Autowired
	private BajaRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse("2029-01-01 00:00:00", formatter);
        
		Baja obj = new Baja();
		obj.setCodTipoBaja(1);
		obj.setEstado("activo");

		Baja datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals(date, datos.getFechaBajaActual());
		assertEquals("activo", datos.getEstado());
	}

	@Test
	@Order(2)
	public void testBuscar() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        
    	Baja obj = new Baja();
		obj.setEstado("activo");

		repo.save(obj);

		Optional<Baja> obj1 = repo.findById(1);

		assertThat(obj1.get().getDescripcionBaja()).isEqualTo("texto");
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		Baja obj = new Baja();
		
		obj.setDescripcionBaja("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<Baja> obj1 = repo.findById(1);

		String datoNuevo = "NombreNuevo";

		obj.setDescripcionBaja(datoNuevo);

		Optional<Baja> objModificado = repo.findById(1);
	}

	@Test
	@Order(4)
	public void testListar() {
		List<Baja> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		Baja obj = new Baja();
		
		obj.setDescripcionBaja(nombre);
		obj.setEstado("activo");
		repo.save(obj);

		int id = 1;
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
