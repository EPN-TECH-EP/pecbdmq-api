package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Set;

import epntech.cbdmq.pe.dominio.admin.ComponenteNota;
import epntech.cbdmq.pe.dominio.admin.EncuestaFormulario;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface EncuestaFormularioService {

	EncuestaFormulario save(EncuestaFormulario obj) throws DataException;

    List<EncuestaFormulario> getAll();
	
    
    //Set<EncuestaFormulario> set();
    
}
