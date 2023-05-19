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
		obj.setCodTipoPrueba(5);		
		obj.setTipoPrueba("texto");
		obj.setEstado("activo");

		TipoPrueba datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("texto", datos.getTipoPrueba());
		
		assertEquals("activo", datos.getEstado());
	}
	
	@Test
	@Order(2)
	public void testBuscar() {
		
		
		TipoPrueba obj = new TipoPrueba();
		obj.setCodTipoPrueba(5);		
		obj.setTipoPrueba("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<TipoPrueba> obj1 = repo.findByTipoPruebaIgnoreCase("texto");

		assertThat(obj1.get().getTipoPrueba()).isEqualTo("texto");
	}
	
	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		TipoPrueba obj = new TipoPrueba();
		obj.setCodTipoPrueba(5);		
		obj.setTipoPrueba("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<TipoPrueba> obj1 = repo.findByTipoPruebaIgnoreCase("Pedro");

		String datoNuevo = "NombreNuevo";

		obj.setTipoPrueba(datoNuevo);
		obj.setCodTipoPrueba(obj1.get().getCodTipoPrueba());

		Optional<TipoPrueba> objModificado = repo.findByTipoPruebaIgnoreCase("Pedro");
		assertThat(objModificado.get().getTipoPrueba()).isEqualTo(datoNuevo);
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
		obj.setCodTipoPrueba(5);		
		obj.setTipoPrueba("texto");
		obj.setEstado("activo");

		repo.save(obj);

		int id = repo.findByTipoPruebaIgnoreCase("texto").get().getCodTipoPrueba();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
