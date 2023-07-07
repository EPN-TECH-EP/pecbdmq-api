package epntech.cbdmq.pe.servicio.formacion;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelo;
import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;

import java.util.List;
import java.util.Optional;

public interface EstudianteMateriaParaleloService {
    public List<EstudianteMateriaParalelo> getEstudiantesMateriaParalelo();
    public EstudianteMateriaParalelo save(EstudianteMateriaParalelo newObj);
    public Boolean asignarEstudianteMateriaParalelo(Integer codMateria, Integer codParalelo, Estudiante[] estudiantes);
    Optional<EstudianteMateriaParalelo> findByNotaFormacion(Integer codNotaFormacion);


}
