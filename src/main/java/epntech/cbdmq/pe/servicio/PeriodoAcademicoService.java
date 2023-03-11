package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoSemestreModulo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface PeriodoAcademicoService {
	
	PeriodoAcademico save(PeriodoAcademico obj) throws DataException;
	
	List<PeriodoAcademico> getAll();
	
	Optional<PeriodoAcademico> getById(int id);
	
	PeriodoAcademico update(PeriodoAcademico objActualizado) throws DataException;
	
	void deleteById(int id) throws DataException;
	
	List<PeriodoAcademicoSemestreModulo> getAllPeriodoAcademico();
}
