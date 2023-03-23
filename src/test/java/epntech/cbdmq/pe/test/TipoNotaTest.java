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
import epntech.cbdmq.pe.dominio.admin.TipoNota;
import epntech.cbdmq.pe.dominio.admin.TipoPrueba;
import epntech.cbdmq.pe.repositorio.admin.TipoNotaRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TipoNotaTest {
	
	@Autowired
	private TipoNotaRepository repo;

	@Test
	@Order(1)
	void testGuardar() {
	 
		TipoNota obj = new TipoNota();
		obj.setCod_tipo_nota(1);		
		obj.setNota("texto");
		obj.setEstado("activo");

		TipoNota datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("texto", datos.getNota());
		
		assertEquals("activo", datos.getEstado());
	}
	
	@Test
	@Order(2)
	public void testBuscar() {
		
		
		TipoNota obj = new TipoNota();
		obj.setCod_tipo_nota(1);		
		obj.setNota("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<TipoNota> obj1 = repo.findByNota("texto");

		assertThat(obj1.get().getNota()).isEqualTo("texto");
	}
	
	
	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		TipoNota obj = new TipoNota();
		obj.setCod_tipo_nota(1);		
		obj.setNota("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<TipoNota> obj1 = repo.findByNota("texto");

		String datoNuevo = "texto";

		obj.setNota(datoNuevo);
		obj.setCod_tipo_nota(obj1.get().getCod_tipo_nota());

		Optional<TipoNota> objModificado = repo.findByNota("texto");
		assertThat(objModificado.get().getNota()).isEqualTo(datoNuevo);
	}
	@Order(4)
	public void testListar() {
		List<TipoNota> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}
	
	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		TipoNota obj = new TipoNota();
		obj.setCod_tipo_nota(1);		
		obj.setNota("texto");
		obj.setEstado("activo");

		repo.save(obj);

		int id = repo.findByNota("texto").get().getCod_tipo_nota();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
