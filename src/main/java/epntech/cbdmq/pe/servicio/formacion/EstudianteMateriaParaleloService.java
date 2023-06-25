package epntech.cbdmq.pe.servicio.formacion;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelo;

import java.util.List;

public interface EstudianteMateriaParaleloService {
    public List<EstudianteMateriaParalelo> getEstudiantesMateriaParalelo();
    public EstudianteMateriaParalelo save(EstudianteMateriaParalelo newObj);
}
