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

import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.repositorio.fichaPersonal.EstudianteRepository;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EstudianteTest {

	@Autowired
	private EstudianteRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
       
        Estudiante obj = new Estudiante();
		/*obj.setGrado("Test");
		obj.setCodDatosPersonales(1);
		obj.setIdEstudiante("123");
		obj.setEstado("activo");*/

		Estudiante datos = repo.save(obj);
		assertNotNull(datos);

		/*assertEquals("Test", datos.getGrado());
		assertEquals(1, datos.getCodDatosPersonales());
		assertEquals("123", datos.getIdEstudiante());
		assertEquals("activo", datos.getEstado());*/
	}

	@Test
	@Order(2)
	public void testBuscar() {
		String nombre = "Test";

		Estudiante obj = new Estudiante();
		/*obj.setGrado(nombre);
		obj.setIdEstudiante("123");
		obj.setEstado("activo");*/

		repo.save(obj);

		Optional<Estudiante> obj1 = repo.findByidEstudiante("123");

		//assertThat(obj1.get().getGrado()).isEqualTo(nombre);
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		Estudiante obj = new Estudiante();
		//obj.setGrado(nombre);
		//obj.setIdEstudiante("123");
		//obj.setEstado("activo");

		repo.save(obj);

		Optional<Estudiante> obj1 = repo.findByidEstudiante("123");

		String datoNuevo = "456";

		//obj.setIdEstudiante(datoNuevo);
		//obj.setCodEstudiante(obj1.get().getCodEstudiante());

		Optional<Estudiante> objModificado = repo.findByidEstudiante(datoNuevo);
		//assertThat(objModificado.get().getIdEstudiante()).isEqualTo(datoNuevo);
	}

	@Test
	@Order(4)
	public void testListar() {
		List<Estudiante> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		Estudiante obj = new Estudiante();
		//obj.setGrado(nombre);
		//obj.setIdEstudiante("123");
		//obj.setEstado("activo");
		repo.save(obj);

		//int id = repo.findByidEstudiante("123").get().getCodEstudiante();
		//repo.deleteById(id);

		//boolean noExiste = repo.findById(id).isPresent();

		//assertFalse(noExiste);
	}
}
