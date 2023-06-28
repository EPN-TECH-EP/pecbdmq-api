package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.SubTipoPrueba;
import epntech.cbdmq.pe.dominio.util.SubTipoPruebaDatos;

public interface SubTipoPruebaService {

	Optional<SubTipoPrueba> findByTipoPrueba(Integer id); 
	
	List<SubTipoPruebaDatos> listarTodosConDatosTipoPrueba();
}

