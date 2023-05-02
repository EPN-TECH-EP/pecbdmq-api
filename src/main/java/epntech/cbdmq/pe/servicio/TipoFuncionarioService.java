package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoFuncionario;
import epntech.cbdmq.pe.excepcion.dominio.DataException;


public interface TipoFuncionarioService {
	TipoFuncionario save(TipoFuncionario obj) throws DataException;
	
	List<TipoFuncionario> getAll();
	
	Optional<TipoFuncionario> getById(Integer codigo);
	
	TipoFuncionario update(TipoFuncionario objActualizado) throws DataException;
	
	void delete(int id) throws DataException;

}
