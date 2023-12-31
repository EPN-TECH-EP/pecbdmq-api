package epntech.cbdmq.pe.repositorio.admin;


import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.Parametrizacion;

public interface ParametrizacionRepository extends JpaRepository<Parametrizacion, Integer>{
	//Optional<Parametrizacion>findById(Integer codigo);
	
	Optional<Parametrizacion> findByObservacionParametriza(String observacion);

	Optional<Parametrizacion> findByFechaInicioParamAndFechaFinParam(Date fechainicioparam , Date fechafinparam);
	
	
}
