package epntech.cbdmq.pe.servicio.formacion;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaParalelo;
import epntech.cbdmq.pe.dominio.util.EstudianteDto;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

import java.util.List;

public interface EstudianteMateriaParaleloService {
    public List<EstudianteMateriaParalelo> getEstudiantesMateriaParalelo();
    public EstudianteMateriaParalelo save(EstudianteMateriaParalelo newObj);
    public Boolean asignarEstudiantesParalelo(List<EstudianteDto> estudiantes, Integer codParalelo) throws DataException;
}
