package epntech.cbdmq.pe.repositorio.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Ponderacion;

public interface PonderacionRepository extends JpaRepository<Ponderacion, Integer> {
	
	

}
