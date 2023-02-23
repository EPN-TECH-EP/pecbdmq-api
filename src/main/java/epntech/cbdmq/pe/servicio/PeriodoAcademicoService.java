package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;

public interface PeriodoAcademicoService {
	
	PeriodoAcademico save(PeriodoAcademico obj);
	
	List<PeriodoAcademico> getAll();
	
	Optional<PeriodoAcademico> getById(int id);
	
	PeriodoAcademico update(PeriodoAcademico objActualizado);
	
	void deleteById(int id);
}
