package epntech.cbdmq.pe.servicio;

import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;

import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface MateriaPeriodoService {

	
	
	MateriaPeriodo save(MateriaPeriodo obj) throws DataException;
	MateriaPeriodo savePeriodoActivo(MateriaPeriodo obj) throws DataException;

    List<MateriaPeriodo> getAll();

    Optional<MateriaPeriodo> getById(Integer codigo);

    MateriaPeriodo update(MateriaPeriodo objActualizado) throws DataException;

    void delete(Integer codigo);
    Optional<MateriaPeriodo> findByCodMateriaAndCodPeriodoAcademico(Integer codMateria, Integer codPeriodoAcademico);
	
	
	
}
