package epntech.cbdmq.pe.servicio.fichaPersonal.fichaPersonalImpl;

import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionEstudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.profesionalizacion.ProfesionalizacionInstructor;
import epntech.cbdmq.pe.repositorio.fichaPersonal.EstudianteRepository;
import epntech.cbdmq.pe.repositorio.fichaPersonal.InstructorRepository;
import epntech.cbdmq.pe.servicio.fichaPersonal.ProfesionalizacionHistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProfesionalizacionHistoricoServiceImpl implements ProfesionalizacionHistoricoService {
    @Autowired
    private EstudianteRepository repoE;
    @Autowired
    private InstructorRepository repo;
    @Override
    public List<ProfesionalizacionEstudiante> getProfesionalizacionHistoricos(String codEstudiante, Pageable pageable) {
        return this.repoE.getProfHistoricos(codEstudiante,pageable);
    }

    @Override
    public List<ProfesionalizacionInstructor> getProfesionalizacionHistoricos(Integer codInstructor, Pageable pageable) {
        return repo.getProfHistoricos(codInstructor, pageable);
    }
}
