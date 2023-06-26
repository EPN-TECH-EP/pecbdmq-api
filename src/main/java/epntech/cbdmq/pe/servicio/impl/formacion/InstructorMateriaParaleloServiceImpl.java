package epntech.cbdmq.pe.servicio.impl.formacion;

import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelo;
import epntech.cbdmq.pe.repositorio.admin.MateriaPeriodoRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.repositorio.admin.formacion.InstructorMateriaParaleloRepository;
import epntech.cbdmq.pe.repositorio.admin.formacion.MateriaParaleloRepository;
import epntech.cbdmq.pe.servicio.formacion.InstructorMateriaParaleloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InstructorMateriaParaleloServiceImpl implements InstructorMateriaParaleloService {
    @Autowired
    InstructorMateriaParaleloRepository repoIMP;
    @Autowired
    private PeriodoAcademicoRepository periodoAcademicoRepository;
    @Autowired
    private MateriaPeriodoRepository repoMPe;
    @Autowired
    private MateriaParaleloRepository repoMPa;

    @Override
    public List<InstructorMateriaParalelo> getInstructoresMateriaParalelo() {
        return repoIMP.findAll();
    }

    @Override
    public InstructorMateriaParalelo save(InstructorMateriaParalelo newObj) {
        return repoIMP.save(newObj);
    }

    @Override
    @Transactional
    public Boolean asignarInstructorMateriaParalelo(Integer codMateria, Integer codCoordinador, Integer codAula, Integer[] codAsistentes, Integer[] codInstructores, Integer codParalelo) {
        MateriaPeriodo objMPe = new MateriaPeriodo();
        objMPe.setCodPeriodoAcademico(periodoAcademicoRepository.getPAActive());
        objMPe.setCodMateria(codMateria);
        objMPe.setCodAula(codAula);
        MateriaPeriodo objMPeII = repoMPe.save(objMPe);

        MateriaParalelo objMpa = new MateriaParalelo();
        objMpa.setCodMateriaPeriodo(objMPeII.getCodMateriaPeriodo());
        objMpa.setCodParalelo(codParalelo);
        MateriaParalelo objMPaII = repoMPa.save(objMpa);

        InstructorMateriaParalelo objCoordinador = new InstructorMateriaParalelo();
        objCoordinador.setCodMateriaParalelo(objMPaII.getCodMateriaParalelo());
        objCoordinador.setCodInstructor(codCoordinador);
        objCoordinador.setCodTipoInstructor(3);
        repoIMP.save(objCoordinador);

        for (Integer codAsistente : codAsistentes) {
            InstructorMateriaParalelo objAsistente = new InstructorMateriaParalelo();
            objAsistente.setCodMateriaParalelo(objMPaII.getCodMateriaParalelo());
            objAsistente.setCodInstructor(codAsistente);
            objAsistente.setCodTipoInstructor(1);
            repoIMP.save(objAsistente);
        }

        for (Integer codInstructor : codInstructores) {
            InstructorMateriaParalelo objInstructor = new InstructorMateriaParalelo();
            objInstructor.setCodMateriaParalelo(objMPaII.getCodMateriaParalelo());
            objInstructor.setCodInstructor(codInstructor);
            objInstructor.setCodTipoInstructor(2);
            repoIMP.save(objInstructor);
        }

        return true;
    }

}
