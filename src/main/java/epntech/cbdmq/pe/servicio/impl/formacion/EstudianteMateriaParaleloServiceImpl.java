package epntech.cbdmq.pe.servicio.impl.formacion;

import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaParalelo;
import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.formacion.EstudianteMateriaParaleloRepository;
import epntech.cbdmq.pe.servicio.*;
import epntech.cbdmq.pe.servicio.formacion.EstudianteMateriaParaleloService;
import epntech.cbdmq.pe.servicio.formacion.MateriaParaleloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstudianteMateriaParaleloServiceImpl implements EstudianteMateriaParaleloService {


    @Autowired
    EstudianteMateriaParaleloRepository repoObj;
    @Autowired
    private AulaService serviceAula;
    @Autowired
    private MateriaParaleloService serviceMP;
    @Autowired
    private MateriaPeriodoService serviceMPe;
    @Autowired
    private MateriaService serviceMateria;
    @Autowired
    private ParaleloService serviceParalelo;
    @Autowired
    private EjeMateriaService serviceEje;
    @Autowired
    private PeriodoAcademicoService servicePeriodo;

    @Override
    public List<EstudianteMateriaParalelo> getEstudiantesMateriaParalelo() {
        return repoObj.findAll();
    }

    @Override
    public EstudianteMateriaParalelo save(EstudianteMateriaParalelo newObj) {
        return repoObj.save(newObj);
    }

    @Override
    public Boolean asignarEstudiantesParalelo(List<Estudiante> estudiantes, Integer codParalelo) {
        List<MateriaPeriodo> materias = serviceMPe.getAllByPA(servicePeriodo.getPAActivo());
        List<MateriaParalelo> materiaParaleloL = materias.stream().map(materiaPeriodo -> {
                    Optional<MateriaParalelo> objMateria = serviceMP.findByCodMateriaPeriodoAndCodParalelo(materiaPeriodo.getCodMateriaPeriodo(), codParalelo);
            if (objMateria.isPresent()) {
                return objMateria.get();
            }

                    try {
                        throw new DataException("No se encuentra data");
                    } catch (DataException e) {
                        throw new RuntimeException(e);
                    }
                }

        ).collect(Collectors.toList());

            List<EstudianteMateriaParalelo> estudianteMateriasParalelo = materiaParaleloL.stream()
                    .flatMap(materiaParalelo -> estudiantes.stream()
                            .map(estudiante -> {
                                EstudianteMateriaParalelo estudianteMateriaParaleloObj = new EstudianteMateriaParalelo();
                                estudianteMateriaParaleloObj.setCodMateriaParalelo(materiaParalelo.getCodMateriaParalelo());
                                estudianteMateriaParaleloObj.setCodEstudiante(estudiante.getCodEstudiante());
                                this.save(estudianteMateriaParaleloObj);
                                return estudianteMateriaParaleloObj;
                            }))
                    .collect(Collectors.toList());
            return true;

    }

}
