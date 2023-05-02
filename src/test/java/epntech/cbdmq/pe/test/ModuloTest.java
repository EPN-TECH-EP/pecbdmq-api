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

import epntech.cbdmq.pe.dominio.admin.Modulo;
import epntech.cbdmq.pe.repositorio.admin.ModuloRepository;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ModuloTest {

	@Autowired
	private  ModuloRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
	 
		Modulo obj = new Modulo();
		obj.setCod_modulo(1);		
		obj.setDescripcion("texto");
		obj.setEstado("activo");

		Modulo datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("texto", datos.getDescripcion());
		
		assertEquals("activo", datos.getEstado());
	}
	
	@Test
	@Order(2)
	public void testBuscar() {
		
		
		Modulo obj = new Modulo();
		obj.setCod_modulo(1);	
		obj.setEtiqueta("texto");
		obj.setDescripcion("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<Modulo> obj1 = repo.findByEtiquetaIgnoreCase("texto");

		assertThat(obj1.get().getDescripcion()).isEqualTo("texto");
	}
	
	@Test
	@Order(3)
	public void testActualizar() {

		Modulo obj = new Modulo();
		obj.setCod_modulo(1);	
		obj.setEtiqueta("texto");
		obj.setDescripcion("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<Modulo> obj1 = repo.findByEtiquetaIgnoreCase("texto");

		String datoNuevo = "texto";

		obj.setEtiqueta(datoNuevo);
		obj.setCod_modulo(obj1.get().getCod_modulo());

		Optional<Modulo> objModificado = repo.findByEtiquetaIgnoreCase("texto");
		assertThat(objModificado.get().getEtiqueta()).isEqualTo(datoNuevo);
	}
	
	@Test
	@Order(4)
	public void testListar() {
		List<Modulo> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}
	
	
	@Test
	@Order(5)
	public void testEliminar() {

		Modulo obj = new Modulo();
		obj.setCod_modulo(1);	
		obj.setEtiqueta("texto");
		obj.setDescripcion("texto");
		obj.setEstado("activo");

		repo.save(obj);

		int id = repo.findByEtiquetaIgnoreCase("texto").get().getCod_modulo();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
