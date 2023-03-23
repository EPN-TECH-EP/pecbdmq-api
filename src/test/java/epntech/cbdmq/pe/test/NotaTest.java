package epntech.cbdmq.pe.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

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


import epntech.cbdmq.pe.dominio.admin.Notas;
import epntech.cbdmq.pe.repositorio.admin.NotaRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class NotaTest {

	
	@Autowired
	private NotaRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        Date date = java.sql.Date.valueOf(LocalDate.parse("2029-01-01", formatter));
        
		Notas obj = new Notas();
		obj.setCod_nota_formacion(1);
		obj.setFechacreanota(date);
		obj.setUsuariocreanota("texto");
		obj.setEstado("activo");

		Notas datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("texto", datos.getUsuariocreanota());
		assertEquals(date, datos.getFechacreanota());
		assertEquals("activo", datos.getEstado());
	}
	
	@Test
	@Order(2)
	public void testBuscar() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse("2029-01-01", formatter));

        
        Notas obj = new Notas();
		obj.setCod_nota_formacion(1);
		obj.setFechacreanota(date);
		obj.setUsuariocreanota("texto");
		obj.setUsuariomodnota("amigo");
		obj.setEstado("activo");

		repo.save(obj);

		Optional<Notas> obj1 = repo.findByusuariocreanota("texto");

		assertThat(obj1.get().getUsuariocreanota()).isEqualTo("texto");
	}
	
	
	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		Notas obj = new Notas();

		obj.setUsuariocreanota("texto");
		obj.setUsuariomodnota("amigo");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<Notas> obj1 = repo.findByusuariocreanota("texto");

		String datoNuevo = "NombreNuevo";

		obj.setUsuariomodnota(datoNuevo);
		obj.setCod_nota_formacion(obj1.get().getCod_nota_formacion());

		Optional<Notas> objModificado = repo.findByusuariocreanota("texto");
		assertThat(objModificado.get().getUsuariomodnota()).isEqualTo(datoNuevo);
	}
	
	@Test
	@Order(4)
	public void testListar() {
		List<Notas> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}
	
	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		Notas obj = new Notas();
		obj.setCod_nota_formacion(1);
		obj.setUsuariocreanota("texto");
		obj.setUsuariomodnota("amigo");
		obj.setEstado("activo");

		repo.save(obj);

		int id = repo.findByusuariocreanota("texto").get().getCod_nota_formacion();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
	
}
