package epntech.cbdmq.pe.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.repositorio.admin.PostulanteDPRepository;
import epntech.cbdmq.pe.repositorio.admin.PostulanteRepository;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PostulanteTest {

	@Autowired
	private PostulanteRepository repo;
	
	@Autowired
	private PostulanteDPRepository repo1;
	
	@Test
	@Order(1)
	void testGuardar() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(LocalDate.parse("2029-01-01", formatter));
        
        Postulante obj = new Postulante();
		obj.setCodDatoPersonal(1);
		obj.setIdPostulante("123");
		obj.setEstado("activo");

		Postulante datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals("123", datos.getIdPostulante());
		assertEquals(1, datos.getCodDatoPersonal());
		assertEquals("activo", datos.getEstado());
	}

	@Test
	@Order(2)
	public void testBuscar() {
		String nombre = "Test";

		Postulante obj = new Postulante();
		obj.setIdPostulante("123");
		obj.setEstado("activo");

		repo.save(obj);

		Optional<?> obj1 = repo1.getByCedula("123");

		System.out.println("obj: " + obj1.get().getClass());
		//assertThat(obj1.get().getClass().get).isEqualTo("123");
	}

	
}
