package epntech.cbdmq.pe.servicio.especializacion;

import epntech.cbdmq.pe.dominio.admin.especializacion.DelegadoEsp;
import epntech.cbdmq.pe.dominio.admin.especializacion.DelegadoUtilEsp;;

import java.util.List;

public interface DelegadoEspService {

	DelegadoEsp save(DelegadoEsp obj);
	
	List<DelegadoEsp> getAll();
	
	void delete(Long codDelegado);
	
	DelegadoEsp getByIdUsuario(int codUsuario);

	Boolean isUsuarioDelegado(int codUsuario);

	List<DelegadoUtilEsp> delegado();
}
