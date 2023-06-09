package epntech.cbdmq.pe.test;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;



import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import epntech.cbdmq.pe.dominio.fichaPersonal.Instructor;

import epntech.cbdmq.pe.repositorio.fichaPersonal.InstructorRepository;



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
		obj.setCodInstructor(5);
		

		Instructor datos = repo.save(obj);
		assertNotNull(datos);

		
	}
	
	
	
	
}
