package epntech.cbdmq.pe.test;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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


import epntech.cbdmq.pe.dominio.admin.TipoPrueba;

import epntech.cbdmq.pe.repositorio.admin.TipoPruebaRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TipoPruebaTest {

	
	@Autowired
	private TipoPruebaRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
	 
		TipoPrueba obj = new TipoPrueba();
		obj.setCod_tipo_prueba(5);		
		obj.setPrueba("texto");
		obj.setEstado("activo");

		TipoPrueba datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("texto", datos.getPrueba());
		
		assertEquals("activo", datos.getEstado());
	}
	
	@Test
	@Order(2)
	public void testBuscar() {
		
		
		TipoPrueba obj = new TipoPrueba();
		obj.setCod_tipo_prueba(5);		
		obj.setPrueba("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<TipoPrueba> obj1 = repo.findByPruebaIgnoreCase("texto");

		assertThat(obj1.get().getPrueba()).isEqualTo("texto");
	}
	
	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		TipoPrueba obj = new TipoPrueba();
		obj.setCod_tipo_prueba(5);		
		obj.setPrueba("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<TipoPrueba> obj1 = repo.findByPruebaIgnoreCase("Pedro");

		String datoNuevo = "NombreNuevo";

		obj.setPrueba(datoNuevo);
		obj.setCod_tipo_prueba(obj1.get().getCod_tipo_prueba());

		Optional<TipoPrueba> objModificado = repo.findByPruebaIgnoreCase("Pedro");
		assertThat(objModificado.get().getPrueba()).isEqualTo(datoNuevo);
	}
	
	@Test
	@Order(4)
	public void testListar() {
		List<TipoPrueba> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}
	
	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		TipoPrueba obj = new TipoPrueba();
		obj.setCod_tipo_prueba(5);		
		obj.setPrueba("texto");
		obj.setEstado("activo");

		repo.save(obj);

		int id = repo.findByPruebaIgnoreCase("texto").get().getCod_tipo_prueba();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
