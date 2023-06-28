package epntech.cbdmq.pe.servicio.formacion;

import epntech.cbdmq.pe.dominio.Usuario;
import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import java.util.List;
import java.util.Optional;

public interface MateriaParaleloService {
    MateriaParalelo saveMateriaInParalelo(MateriaParalelo obj) throws DataException;
    List<MateriaParalelo> getMateriasParalelo()throws DataException ;
    public Optional<MateriaParalelo> getById(Integer codigo);
}
