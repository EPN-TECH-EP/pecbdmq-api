package epntech.cbdmq.pe.repositorio.admin;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import epntech.cbdmq.pe.dominio.admin.ParametrizaPruebaDetalle;

public interface ParametrizaPruebaDetalleRepository extends JpaRepository<ParametrizaPruebaDetalle, Integer>{

	public List<ParametrizaPruebaDetalle> findAllByCodParametrizaPruebaResumenOrderByEdadInicioMesesAndSexo(Integer codResumen);

	
	
}
