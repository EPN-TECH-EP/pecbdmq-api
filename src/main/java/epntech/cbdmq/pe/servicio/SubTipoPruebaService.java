package epntech.cbdmq.pe.servicio;

import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;

public interface SubTipoPruebaService {

	Optional<SubTipoPrueba> findByTipoPrueba(Integer id); 
}
