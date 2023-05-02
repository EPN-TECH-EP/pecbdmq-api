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


import epntech.cbdmq.pe.dominio.admin.TipoSancion;


import epntech.cbdmq.pe.repositorio.admin.TipoSancionRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TipoSancionTest {

	@Autowired
	private TipoSancionRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
		
        
		TipoSancion obj = new TipoSancion();
		obj.setCod_tipo_sancion(1);
		
		obj.setSancion("texto");
		obj.setEstado("activo");

		TipoSancion datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("texto", datos.getSancion());
		
		assertEquals("activo", datos.getEstado());
	}
	
	@Test
	@Order(2)
	public void testBuscar() {
		

        
        TipoSancion obj = new TipoSancion();
		obj.setCod_tipo_sancion(1);
		
		obj.setSancion("texto");
		obj.setEstado("activo");

		repo.save(obj);

		Optional<TipoSancion> obj1 = repo.findBySancionIgnoreCase("texto");

		assertThat(obj1.get().getSancion()).isEqualTo("texto");
	}
	
	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		TipoSancion obj = new TipoSancion();
		obj.setCod_tipo_sancion(5);		
		obj.setSancion("NombreNuevo");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<TipoSancion> obj1 = repo.findBySancionIgnoreCase("NombreNuevo");

		String datoNuevo = "NombreNuevo";

		obj.setSancion(datoNuevo);
		obj.setCod_tipo_sancion(obj1.get().getCod_tipo_sancion());

		Optional<TipoSancion> objModificado = repo.findBySancionIgnoreCase("NombreNuevo");
		assertThat(objModificado.get().getSancion()).isEqualTo(datoNuevo);
	}
	
	@Test
	@Order(4)
	public void testListar() {
		List<TipoSancion> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}
	
	
	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		TipoSancion obj = new TipoSancion();
		obj.setCod_tipo_sancion(5);		
		obj.setSancion("texto");
		obj.setEstado("activo");

		repo.save(obj);

		int id = repo.findBySancionIgnoreCase("texto").get().getCod_tipo_sancion();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
	
}
