package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Delegado;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public interface DelegadoService {

	Delegado save(Delegado obj) throws DataException;
	
	List<Delegado> getAll();
	
	void delete(int codUsuario, int codPeriodoAcademico) throws DataException;
}
