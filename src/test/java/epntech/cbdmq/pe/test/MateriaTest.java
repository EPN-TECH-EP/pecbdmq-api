package epntech.cbdmq.pe.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
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

import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.dominio.admin.Paralelo;
import epntech.cbdmq.pe.repositorio.admin.MateriaRepository;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class MateriaTest {

	@Autowired
	private MateriaRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
       
        Materia obj = new Materia();
        Paralelo paralelo = new Paralelo();
        List<Paralelo> paralelos = new ArrayList<>();
        paralelo.setNombreParalelo("XYZ");
        paralelo.setEstado("ACTIVO");
        paralelos.add(paralelo);
        	
		obj.setNombre("Test");
		obj.setNotaMinima(10);
		obj.setObservacionMateria("pruebas unitarias");
		obj.setParalelos(paralelos);
		obj.setEstado("activo");

		Materia datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("Test", datos.getNombre());
		assertEquals(10, datos.getNotaMinima());
		assertEquals("pruebas unitarias", datos.getObservacionMateria());
		assertEquals("activo", datos.getEstado());
	}

	@Test
	@Order(2)
	public void testBuscar() {
		String nombre = "Test";

		Materia obj = new Materia();
		obj.setNombre(nombre);
		obj.setNotaMinima(10);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<Materia> obj1 = repo.findByNombreIgnoreCase("Test");

		assertThat(obj1.get().getNombre()).isEqualTo(nombre);
	}

	@Test
	@Order(3)
	public void testActualizar() {
		String nombre = "Test";

		Materia obj = new Materia();
		obj.setNombre(nombre);
		obj.setNotaMinima(10);
		obj.setEstado("activo");

		repo.save(obj);

		Optional<Materia> obj1 = repo.findByNombreIgnoreCase("Test");

		String datoNuevo = "Abc";

		obj.setNombre(datoNuevo);
		obj.setCodMateria(obj1.get().getCodMateria());

		Optional<Materia> objModificado = repo.findByNombreIgnoreCase(datoNuevo);
		assertThat(objModificado.get().getNombre()).isEqualTo(datoNuevo);
	}

	@Test
	@Order(4)
	public void testListar() {
		List<Materia> lista = repo.findAll();
		assertThat(lista).size().isGreaterThan(0);
	}

	@Test
	@Order(5)
	public void testEliminar() {
		String nombre = "Test";

		Materia obj = new Materia();
		obj.setNombre(nombre);
		obj.setNotaMinima(10);
		obj.setEstado("activo");
		repo.save(obj);

		int id = repo.findByNombreIgnoreCase("Test").get().getCodMateria();
		repo.deleteById(id);

		boolean noExiste = repo.findById(id).isPresent();

		assertFalse(noExiste);
	}
}
