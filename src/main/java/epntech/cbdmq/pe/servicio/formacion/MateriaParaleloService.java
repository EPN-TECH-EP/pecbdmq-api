package epntech.cbdmq.pe.servicio.formacion;

import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import java.util.List;

public interface MateriaParaleloService {
    MateriaParalelo saveMateriaInParalelo(MateriaParalelo obj) throws DataException;
    List<MateriaParalelo> getMateriasParalelo()throws DataException ;
}
