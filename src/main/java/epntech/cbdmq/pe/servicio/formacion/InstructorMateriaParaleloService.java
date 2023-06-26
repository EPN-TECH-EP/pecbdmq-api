package epntech.cbdmq.pe.servicio.formacion;

import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelo;
import java.util.List;

public interface InstructorMateriaParaleloService {
    public List<InstructorMateriaParalelo> getInstructoresMateriaParalelo();
    public InstructorMateriaParalelo save(InstructorMateriaParalelo newObj);
    public Boolean asignarInstructorMateriaParalelo(Integer codMateria,Integer codCoordinador, Integer codAula, Integer[] codAsistentes, Integer[] codInstructores, Integer codParalelo);
}
