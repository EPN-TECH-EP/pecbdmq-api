package epntech.cbdmq.pe.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
		obj.setCodNotaFormacion(1);
		//obj.setFechacreanota(date);
		obj.setUsuarioCreaNota("texto");
		obj.setEstado("activo");

		Notas datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("texto", datos.getUsuarioCreaNota());
		assertEquals(date, datos.getFechaCreaNota());
		assertEquals("activo", datos.getEstado());
	}
	
	@Test
	@Order(2)
	public void testBuscar() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse("2029-01-01", formatter));

        
        Notas obj = new Notas();
		obj.setCodNotaFormacion(1);
		//obj.setFechacreanota(date);
		obj.setUsuarioCreaNota("texto");
		obj.setUsuarioModNota("amigo");
		obj.setEstado("activo");

		repo.save(obj);

		Optional<Notas> obj1 = repo.findByUsuarioCreaNota("texto");

		assertThat(obj1.get().getUsuarioCreaNota()).isEqualTo("texto");
	}
	
	
	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		Notas obj = new Notas();

		obj.setUsuarioCreaNota("texto");
		obj.setUsuarioModNota("amigo");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<Notas> obj1 = repo.findByUsuarioCreaNota("texto");

		String datoNuevo = "NombreNuevo";

		obj.setUsuarioModNota(datoNuevo);
		obj.setCodNotaFormacion(obj1.get().getCodNotaFormacion());

		Optional<Notas> objModificado = repo.findByUsuarioCreaNota("texto");
		assertThat(objModificado.get().getUsuarioModNota()).isEqualTo(datoNuevo);
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
		obj.setCodNotaFormacion(1);
		obj.setUsuarioCreaNota("texto");
		obj.setUsuarioModNota("amigo");
		obj.setEstado("activo");

		repo.save(obj);

		int id = repo.findByUsuarioCreaNota("texto").get().getCodNotaFormacion();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
	
}
