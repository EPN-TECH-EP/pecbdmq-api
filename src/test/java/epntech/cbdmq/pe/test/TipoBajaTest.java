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


import epntech.cbdmq.pe.dominio.admin.TipoBaja;

import epntech.cbdmq.pe.repositorio.admin.TipoBajaRepository;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TipoBajaTest {

	@Autowired
	private TipoBajaRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
	 
		TipoBaja obj = new TipoBaja();
		obj.setCod_tipo_baja(1);		
		obj.setBaja("texto");
		obj.setEstado("activo");

		TipoBaja datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("texto", datos.getBaja());
		
		assertEquals("activo", datos.getEstado());
	}
	
	
	@Test
	@Order(2)
	public void testBuscar() {
		
		
		TipoBaja obj = new TipoBaja();
		obj.setCod_tipo_baja(1);		
		obj.setBaja("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<TipoBaja> obj1 = repo.findByBaja("texto");

		assertThat(obj1.get().getBaja()).isEqualTo("texto");
	}
	
	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		TipoBaja obj = new TipoBaja();
		obj.setCod_tipo_baja(1);		
		obj.setBaja("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<TipoBaja> obj1 = repo.findByBaja("texto");

		String datoNuevo = "texto";

		obj.setBaja(datoNuevo);
		obj.setCod_tipo_baja(obj1.get().getCod_tipo_baja());

		Optional<TipoBaja> objModificado = repo.findByBaja("texto");
		assertThat(objModificado.get().getBaja()).isEqualTo(datoNuevo);
	}
	
	@Order(4)
	public void testListar() {
		List<TipoBaja> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}
	
	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		TipoBaja obj = new TipoBaja();
		obj.setCod_tipo_baja(1);		
		obj.setBaja("texto");
		obj.setEstado("activo");

		repo.save(obj);

		int id = repo.findByBaja("texto").get().getCod_tipo_baja();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
	
}
