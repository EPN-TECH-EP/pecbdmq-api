package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.admin.EjeMateria;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

import java.util.List;
import java.util.Optional;

public interface EjeMateriaService {
    EjeMateria saveEje(EjeMateria obj) throws DataException;

    List<EjeMateria> getAllEje();

    Optional<EjeMateria> getByIdEje(Long id);

    EjeMateria updateEje(EjeMateria objActualizado)throws DataException ;

    void deleteEje(Long id) throws DataException;
}
