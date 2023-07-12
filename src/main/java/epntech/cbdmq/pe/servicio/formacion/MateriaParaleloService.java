package epntech.cbdmq.pe.servicio.formacion;

import epntech.cbdmq.pe.dominio.Usuario;
import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.Paralelo;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelosDto;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaReadDto;
import epntech.cbdmq.pe.dominio.util.MateriaAulaUtil;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MateriaParaleloService {
    MateriaParalelo saveMateriaInParalelo(MateriaParalelo obj) throws DataException;
    List<MateriaParalelo> getMateriasParalelo()throws DataException ;
    public Optional<MateriaParalelo> getById(Integer codigo);
    public boolean asignarMateriaParalelo(List<MateriaAulaUtil> materiasAula, List<Paralelo> paralelos);
    Optional<MateriaParalelo> findByCodMateriaPeriodoAndCodParalelo(Integer materiaParalelo, Integer paralelo);
    List<InstructorMateriaReadDto> getMateriaNombres(Integer codPeriodoAcademico);
}
