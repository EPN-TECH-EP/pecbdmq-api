package epntech.cbdmq.pe.servicio.impl.formacion;

import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelo;
import epntech.cbdmq.pe.repositorio.admin.formacion.InstructorMateriaParaleloRepository;
import epntech.cbdmq.pe.servicio.formacion.InstructorMateriaParaleloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorMateriaParaleloServiceImpl implements InstructorMateriaParaleloService {
    @Autowired
    InstructorMateriaParaleloRepository repoObj;

    @Override
    public List<InstructorMateriaParalelo> getInstructoresMateriaParalelo() {
        return repoObj.findAll();
    }

    @Override
    public InstructorMateriaParalelo save(InstructorMateriaParalelo newObj) {
        return repoObj.save(newObj);
    }
}
