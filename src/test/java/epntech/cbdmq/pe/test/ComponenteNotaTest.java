package epntech.cbdmq.pe.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


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

import epntech.cbdmq.pe.dominio.admin.ComponenteNota;

import epntech.cbdmq.pe.repositorio.admin.ComponenteNotaRepository;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ComponenteNotaTest {

	
	@Autowired
	private ComponenteNotaRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
	 
		ComponenteNota obj = new ComponenteNota();
		obj.setCod_componente_nota(2);		
		obj.setComponentenota("texto");
		obj.setEstado("activo");

		ComponenteNota datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("texto", datos.getComponentenota());
		
		assertEquals("activo", datos.getEstado());
	}

	
	@Test
	@Order(2)
	public void testBuscar() {
		
		
		ComponenteNota obj = new ComponenteNota();
		obj.setCod_componente_nota(2);		
		obj.setComponentenota("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<ComponenteNota> obj1 = repo.findByComponentenota("texto");

		assertThat(obj1.get().getComponentenota()).isEqualTo("texto");
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		ComponenteNota obj = new ComponenteNota();
		obj.setCod_componente_nota(2);		
		obj.setComponentenota("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<ComponenteNota> obj1 = repo.findByComponentenota("texto");

		String datoNuevo = "texto";

		obj.setComponentenota(datoNuevo);
		obj.setCod_componente_nota(obj1.get().getCod_componente_nota());

		Optional<ComponenteNota> objModificado = repo.findByComponentenota("texto");
		assertThat(objModificado.get().getComponentenota()).isEqualTo(datoNuevo);
	}
	
	@Test
	@Order(4)
	public void testListar() {
		List<ComponenteNota> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}
	
	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		ComponenteNota obj = new ComponenteNota();
		obj.setCod_componente_nota(2);		
		obj.setComponentenota("texto");
		obj.setEstado("activo");

		repo.save(obj);

		int id = repo.findByComponentenota("texto").get().getCod_componente_nota();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
	
}
