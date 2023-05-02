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

import epntech.cbdmq.pe.dominio.admin.Apelacion;
import epntech.cbdmq.pe.repositorio.admin.ApelacionRepository;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ApelacionTest {

	@Autowired
	private ApelacionRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
	 
		Apelacion apelacion = new Apelacion();
		apelacion.setCod_apelacion(5);		
		apelacion.setObservacionaspirante("texto");
		apelacion.setEstado("activo");

		Apelacion datos = repo.save(apelacion);
		assertNotNull(datos);

		assertEquals("texto", datos.getObservacionaspirante());
		
		assertEquals("activo", datos.getEstado());
	}
	
	
	
	@Test
	@Order(2)
	public void testBuscar() {
		
		
		Apelacion obj = new Apelacion();
		obj.setCod_apelacion(5);		
		obj.setObservacionaspirante("texto");
		obj.setAprobacion("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<Apelacion> obj1 = repo.findByNombreIgnoreCase("texto");

		assertThat(obj1.get().getAprobacion()).isEqualTo("texto");
	}
	
	@Test
	@Order(3)
	public void testActualizar() {

		Apelacion obj = new Apelacion();
		obj.setCod_apelacion(5);		
		obj.setObservacionaspirante("texto");
		obj.setAprobacion("texto");
		obj.setEstado("activo");


		repo.save(obj);

		Optional<Apelacion> obj1 = repo.findByNombreIgnoreCase("texto");

		String datoNuevo = "texto";

		obj.setAprobacion(datoNuevo);
		obj.setCod_apelacion(obj1.get().getCod_apelacion());

		Optional<Apelacion> objModificado = repo.findByNombreIgnoreCase("texto");
		assertThat(objModificado.get().getAprobacion()).isEqualTo(datoNuevo);
	}
	@Test
	@Order(4)
	public void testListar() {
		List<Apelacion> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}
	
	@Test
	@Order(5)
	public void testEliminar() {

		Apelacion obj = new Apelacion();
		obj.setCod_apelacion(5);		
		obj.setObservacionaspirante("texto");
		obj.setAprobacion("texto");
		obj.setEstado("activo");

		repo.save(obj);

		int id = repo.findByNombreIgnoreCase("texto").get().getCod_apelacion();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
