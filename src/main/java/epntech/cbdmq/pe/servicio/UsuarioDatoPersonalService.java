package epntech.cbdmq.pe.servicio;

import java.util.Set;

import epntech.cbdmq.pe.dominio.admin.UsuarioDatoPersonal;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface UsuarioDatoPersonalService {

	Set<UsuarioDatoPersonal> getUsuarios();
	
	UsuarioDatoPersonal getByCedula(String cedula) throws DataException;
}
