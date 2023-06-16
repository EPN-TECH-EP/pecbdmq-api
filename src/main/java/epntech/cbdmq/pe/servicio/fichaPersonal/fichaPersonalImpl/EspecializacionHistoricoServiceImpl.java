package epntech.cbdmq.pe.servicio.fichaPersonal.fichaPersonalImpl;

import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.EspecializacionInstructor;
import epntech.cbdmq.pe.repositorio.fichaPersonal.EstudianteRepository;
import epntech.cbdmq.pe.repositorio.fichaPersonal.InstructorRepository;
import epntech.cbdmq.pe.servicio.fichaPersonal.EspecializacionHistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecializacionHistoricoServiceImpl implements EspecializacionHistoricoService {
    @Autowired
    private EstudianteRepository repoE;
    @Autowired
    private InstructorRepository repo;

    @Override
    public List<EspecializacionEstudiante> getEspecializacionHistoricos(String codEstudiante, Pageable pageable) {
        return this.repoE.getEspHistoricos(codEstudiante, pageable);
    }

    @Override
    public List<EspecializacionInstructor> getHistoricoInstructor(Integer codInstructor, Pageable pageable) {
        return repo.getEspHistoricos(codInstructor, pageable);
    }
}
