package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.TipoFuncionario;


public interface TipoFuncionarioService {
	TipoFuncionario save(TipoFuncionario obj);
	
	List<TipoFuncionario> getAll();
	
	Optional<TipoFuncionario> getById(Integer codigo);
	
	TipoFuncionario update(TipoFuncionario objActualizado);
	
	void delete(int id);

}
