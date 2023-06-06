package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.Baja;


public interface BajaRepository extends JpaRepository<Baja, Integer>{

	//Optional<Baja> findByNombreIgnoreCase(String Nombre);
	
}
