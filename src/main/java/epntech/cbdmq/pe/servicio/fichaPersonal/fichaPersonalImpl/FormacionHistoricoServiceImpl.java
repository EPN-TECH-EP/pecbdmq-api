package epntech.cbdmq.pe.servicio.fichaPersonal.fichaPersonalImpl;

import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.formacion.FormacionInstructor;
import epntech.cbdmq.pe.repositorio.admin.NotasFormacionRepository;
import epntech.cbdmq.pe.repositorio.fichaPersonal.EstudianteRepository;
import epntech.cbdmq.pe.repositorio.fichaPersonal.InstructorRepository;
import epntech.cbdmq.pe.servicio.fichaPersonal.FormacionHistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FormacionHistoricoServiceImpl implements FormacionHistoricoService {
    @Autowired
    private EstudianteRepository repoE;
    @Autowired
    private InstructorRepository repo;
    @Autowired
    private NotasFormacionRepository notasFormacionRepository;
    @Override
    public List<FormacionEstudiante> getHistoricos(String codEstudiante, Pageable pageable) {
        return this.repoE.getForHistoricos(codEstudiante, pageable);
    }

    @Override
    public List<FormacionInstructor> getFormHistoricos(Integer codEstudiante, Pageable pageable) {
        return repo.getForHistoricos(codEstudiante, pageable);
    }
    @Override
    public List<?> listaMateriasHistorico() {
        return notasFormacionRepository.listaMateriasHistorico();
    }
}
