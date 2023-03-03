package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.PruebaFisica;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface PruebaFisicaService {
	 PruebaFisica save(PruebaFisica obj) throws DataException;

	    List<PruebaFisica> getAll();

	    Optional<PruebaFisica> getById(Integer codigo);

	    PruebaFisica update(PruebaFisica objActualizado);

	    void delete(Integer codigo);
}
