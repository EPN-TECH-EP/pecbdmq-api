package epntech.cbdmq.pe.servicio.impl.formacion;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaParalelo;
import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.repositorio.admin.formacion.EstudianteMateriaParaleloRepository;
import epntech.cbdmq.pe.servicio.formacion.EstudianteMateriaParaleloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteMateriaParaleloServiceImpl implements EstudianteMateriaParaleloService {


    @Autowired
    EstudianteMateriaParaleloRepository repoObj;
    @Override
    public List<EstudianteMateriaParalelo> getEstudiantesMateriaParalelo() {
        return repoObj.findAll();
    }

    @Override
    public EstudianteMateriaParalelo save(EstudianteMateriaParalelo newObj) {
        return repoObj.save(newObj);
    }

    @Override
    public Boolean asignarEstudianteMateriaParalelo(Integer codMateria, Integer codParalelo, Estudiante[] estudiantes) {
        return null;
    }

}
