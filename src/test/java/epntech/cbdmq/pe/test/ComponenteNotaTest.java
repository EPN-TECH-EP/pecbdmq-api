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
		obj.setCodComponenteNota(2);		
		obj.setNombre("texto");
		obj.setEstado("activo");

		ComponenteNota datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("texto", datos.getNombre());
		
		assertEquals("activo", datos.getEstado());
	}

	
	@Test
	@Order(2)
	public void testBuscar() {
		
		
		ComponenteNota obj = new ComponenteNota();
		obj.setCodComponenteNota(2);		
		obj.setNombre("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<ComponenteNota> obj1 = repo.findByNombreIgnoreCase("texto");

		assertThat(obj1.get().getNombre()).isEqualTo("texto");
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		ComponenteNota obj = new ComponenteNota();
		obj.setCodComponenteNota(2);		
		obj.setNombre("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<ComponenteNota> obj1 = repo.findByNombreIgnoreCase("texto");

		String datoNuevo = "texto";

		obj.setNombre(datoNuevo);
		obj.setCodComponenteNota(obj1.get().getCodComponenteNota());

		Optional<ComponenteNota> objModificado = repo.findByNombreIgnoreCase("texto");
		assertThat(objModificado.get().getNombre()).isEqualTo(datoNuevo);
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
		obj.setCodComponenteNota(2);		
		obj.setNombre("texto");
		obj.setEstado("activo");

		repo.save(obj);

		int id = repo.findByNombreIgnoreCase("texto").get().getCodComponenteNota();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
	
}
