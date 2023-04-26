package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.ModuloEstados;
import epntech.cbdmq.pe.dominio.util.ModuloEstadosData;

public interface ModuloEstadosService {

	ModuloEstados save(ModuloEstados obj);
	
	List<ModuloEstados> getAll();
	
	Optional<ModuloEstados> getById(int id);
	
	ModuloEstados update(ModuloEstados objActualizado);
	
	void delete(int id);
	
	List<ModuloEstados> saveAll(List<ModuloEstados> obj);
	
	List<ModuloEstadosData> getAllData();
}

