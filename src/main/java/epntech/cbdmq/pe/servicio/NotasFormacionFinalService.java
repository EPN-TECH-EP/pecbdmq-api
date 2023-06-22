package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.NotasFormacionFinal;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface NotasFormacionFinalService {

	void cargarDisciplina(List<NotasFormacionFinal> lista) throws DataException;
	
	void calcularNotas();
	
	void cambiaEstadoRealizoEncuesta(Long codEstudiante);
	
	Boolean realizoEncuesta(Long codEstudiante);
	
	Optional<NotasFormacionFinal> getByEstudiante(Long codEstudiante);
}

