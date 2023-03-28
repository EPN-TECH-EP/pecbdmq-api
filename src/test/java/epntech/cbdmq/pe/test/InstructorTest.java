package epntech.cbdmq.pe.test;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;



import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import epntech.cbdmq.pe.dominio.admin.Instructor;

import epntech.cbdmq.pe.repositorio.admin.InstructorRepository;



@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class InstructorTest {


	@Autowired
	private InstructorRepository repo;
	
	@Test
	@Order(1)
	void testGuardar() {
	 
		Instructor obj = new Instructor();
		obj.setCod_instructor(5);		
		obj.setCod_periodo_academico(1);
		obj.setCod_tipo_instructor(1);

		Instructor datos = repo.save(obj);
		assertNotNull(datos);

		assertEquals(1, datos.getCod_periodo_academico());
		
		assertEquals(1, datos.getCod_tipo_instructor());
	}
	
	
	
	
}
