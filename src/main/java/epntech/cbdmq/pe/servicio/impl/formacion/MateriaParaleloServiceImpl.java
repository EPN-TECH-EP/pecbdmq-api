package epntech.cbdmq.pe.servicio.impl.formacion;

import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import epntech.cbdmq.pe.dominio.admin.Paralelo;
import epntech.cbdmq.pe.dominio.util.MateriaAulaUtil;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.AntiguedadesRepository;
import epntech.cbdmq.pe.repositorio.admin.formacion.MateriaParaleloRepository;
import epntech.cbdmq.pe.servicio.MateriaPeriodoService;
import epntech.cbdmq.pe.servicio.ParaleloService;
import epntech.cbdmq.pe.servicio.PeriodoAcademicoService;
import epntech.cbdmq.pe.servicio.formacion.MateriaParaleloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class MateriaParaleloServiceImpl implements MateriaParaleloService {
    @Autowired
    private MateriaParaleloRepository repo;
    @Autowired
    private PeriodoAcademicoService periodoAcademicoService;

    @Override
    public List<MateriaParalelo> getMateriasParalelo() throws DataException {
        return repo.findAll();
    }

    @Override
    public Optional<MateriaParalelo> getById(Integer codigo) {
        return repo.findById(codigo);
    }

    @Override
    @Transactional
    public boolean asignarMateriaParalelo(List<MateriaAulaUtil> materiasAula, List<Paralelo> paralelos) {
        List<MateriaPeriodo> materiasMPe = materiasAula.stream()
                .map(materiaStream -> {
                    MateriaPeriodo objMPe = new MateriaPeriodo();
                    objMPe.setCodPeriodoAcademico(periodoAcademicoService.getPAActivo());
                    objMPe.setCodMateria(materiaStream.getCodMateria());
                    objMPe.setCodAula(materiaStream.getCodAula());
                    //replace
                    return objMPe;
                })
                .collect(Collectors.toList());

        List<MateriaParalelo> materiasParalelo = materiasMPe.stream()
                .flatMap(materiaPA -> paralelos.stream()
                        .map(paralelo -> {
                            MateriaParalelo materiaParalelo = new MateriaParalelo();
                            materiaParalelo.setCodMateriaPeriodo(materiaPA.getCodMateria());
                            materiaParalelo.setCodParalelo(paralelo.getCodParalelo());
                            try {
                                this.saveMateriaInParalelo(materiaParalelo);
                            } catch (DataException e) {
                                throw new RuntimeException(e);
                            }
                            return materiaParalelo;
                        }))
                .collect(Collectors.toList());
        return true;
    }


    @Override
    public MateriaParalelo saveMateriaInParalelo(MateriaParalelo obj) throws DataException {
        return repo.save(obj);
    }
}
