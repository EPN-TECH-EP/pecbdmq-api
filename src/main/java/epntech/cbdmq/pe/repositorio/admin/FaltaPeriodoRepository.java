package epntech.cbdmq.pe.repositorio.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.FaltaPeriodo;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FaltaPeriodoRepository extends JpaRepository<FaltaPeriodo, Integer>{

//@Query("Select f from FaltaPeriodo f where ")
	
	

}
