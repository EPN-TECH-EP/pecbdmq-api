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
import epntech.cbdmq.pe.dominio.util.EstudianteDto;
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
    @Autowired
    private EstudianteService estudianteService;


    @Override
    public List<EstudianteMateriaParalelo> getEstudiantesMateriaParalelo() {
        return repoObj.findAll();
    }

    @Override
    public EstudianteMateriaParalelo save(EstudianteMateriaParalelo newObj) {
        return repoObj.save(newObj);
    }

    @Override
    public Boolean asignarEstudiantesParalelo(List<EstudianteDto> estudiantes, Integer codParalelo) throws DataException {
        List<MateriaParalelo> materiaParaleloL =serviceMP.getAllByCodParalelo(codParalelo);
        if(materiaParaleloL.isEmpty()){
            throw new DataException("No se encontraron materias para el paralelo");
        }

            List<EstudianteMateriaParalelo> estudianteMateriasParalelo = materiaParaleloL.stream()
                    .flatMap(materiaParalelo -> estudiantes.stream()
                            .map(estudianteDto -> {
                                Estudiante estudiante=estudianteService.getEstudianteByCodigoUnico(estudianteDto.getCodUnico());
                                EstudianteMateriaParalelo estudianteMateriaParaleloObj = new EstudianteMateriaParalelo();
                                estudianteMateriaParaleloObj.setCodMateriaParalelo(materiaParalelo.getCodMateriaParalelo());
                                estudianteMateriaParaleloObj.setCodEstudiante(estudiante.getCodEstudiante());
                                estudianteMateriaParaleloObj.setEstado("ACTIVO");
                                this.save(estudianteMateriaParaleloObj);
                                return estudianteMateriaParaleloObj;
                            }))
                    .collect(Collectors.toList());
            System.out.println(estudianteMateriasParalelo);
            return true;

    }

    public Boolean asignarEstudianteMateriaParalelo(Integer codMateria, Integer codParalelo, Estudiante[] estudiantes) {
        return null;
    }

    @Override
    public Optional<EstudianteMateriaParalelo> findByNotaFormacion(Integer codNotaFormacion) {
        return repoObj.findByNotaFormacion(codNotaFormacion);
    }


}
