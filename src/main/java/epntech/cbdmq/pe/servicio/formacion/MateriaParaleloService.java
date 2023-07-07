package epntech.cbdmq.pe.servicio.formacion;

import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaParalelo;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MateriaParaleloService {
    MateriaParalelo saveMateriaParalelo(MateriaParalelo obj) throws DataException;
    List<MateriaParalelo> getMateriasParalelo()throws DataException ;
    public Optional<MateriaParalelo> getById(Integer codigo);
    Optional<MateriaParalelo> findByEstudianteMateriaParalelo(Integer codigoEstudianteMateria);
}
