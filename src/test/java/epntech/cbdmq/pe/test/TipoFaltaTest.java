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


import epntech.cbdmq.pe.dominio.admin.TipoFalta;


import epntech.cbdmq.pe.repositorio.admin.TipoFaltaRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TipoFaltaTest {

	@Autowired
	private TipoFaltaRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
		
        
		TipoFalta obj = new TipoFalta();
		obj.setCodTipoFalta(1);
		
		obj.setNombreFalta("texto");
		obj.setEstado("activo");

		TipoFalta datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("texto", datos.getNombreFalta());
		
		assertEquals("activo", datos.getEstado());
	}
	
	@Test
	@Order(2)
	public void testBuscar() {
		

        
        TipoFalta obj = new TipoFalta();
		obj.setCodTipoFalta(1);
		
		obj.setNombreFalta("texto");
		obj.setEstado("activo");

		repo.save(obj);

		Optional<TipoFalta> obj1 = repo.findByNombreFaltaIgnoreCase("texto");

		assertThat(obj1.get().getNombreFalta()).isEqualTo("texto");
	}
	
	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		TipoFalta obj = new TipoFalta();
		obj.setCodTipoFalta(5);		
		obj.setNombreFalta("NombreNuevo");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<TipoFalta> obj1 = repo.findByNombreFaltaIgnoreCase("NombreNuevo");

		String datoNuevo = "NombreNuevo";

		obj.setNombreFalta(datoNuevo);
		obj.setCodTipoFalta(obj1.get().getCodTipoFalta());

		Optional<TipoFalta> objModificado = repo.findByNombreFaltaIgnoreCase("NombreNuevo");
		assertThat(objModificado.get().getNombreFalta()).isEqualTo(datoNuevo);
	}
	
	@Test
	@Order(4)
	public void testListar() {
		List<TipoFalta> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}
	
	
	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		TipoFalta obj = new TipoFalta();
		obj.setCodTipoFalta(5);		
		obj.setNombreFalta("texto");
		obj.setEstado("activo");

		repo.save(obj);

		int id = repo.findByNombreFaltaIgnoreCase("texto").get().getCodTipoFalta();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
	
}
