package epntech.cbdmq.pe.servicio.impl.formacion;

import epntech.cbdmq.pe.dominio.admin.*;
import epntech.cbdmq.pe.dominio.admin.formacion.InformacionMateriaDto;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaReadDto;
import epntech.cbdmq.pe.dominio.util.InstructorDatos;
import epntech.cbdmq.pe.repositorio.admin.InstructorDatosRepository;
import epntech.cbdmq.pe.repositorio.admin.MateriaPeriodoRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.repositorio.admin.formacion.InstructorMateriaParaleloRepository;
import epntech.cbdmq.pe.repositorio.admin.formacion.MateriaParaleloRepository;
import epntech.cbdmq.pe.servicio.AulaService;
import epntech.cbdmq.pe.servicio.MateriaPeriodoService;
import epntech.cbdmq.pe.servicio.MateriaService;
import epntech.cbdmq.pe.servicio.ParaleloService;
import epntech.cbdmq.pe.servicio.formacion.InstructorMateriaParaleloService;
import epntech.cbdmq.pe.servicio.formacion.MateriaParaleloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;


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
    @Autowired
    private InstructorDatosRepository repoIdr;
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
        Optional<MateriaPeriodo> objGuardado = repoMPe.findByCodMateriaAndCodPeriodoAcademico(objMPe.getCodMateria(), objMPe.getCodPeriodoAcademico());
        MateriaPeriodo objMPeII = new MateriaPeriodo();
        if (objGuardado.isPresent() && !objGuardado.get().getCodMateriaPeriodo().equals(objMPe.getCodMateriaPeriodo())) {
            objMPeII = objGuardado.get();
        } else {
            objMPeII = repoMPe.save(objMPe);
        }

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

    @Override
    public List<InstructorDatos> getInstructoresAsistentes(Integer codMateriaParalelo) {
        return repoIdr.getInstructoresMateriaParaleloByTipo(codMateriaParalelo, "ASISTENTE");

    }

    @Override
    public List<InstructorDatos> getInstructores(Integer codMateriaParalelo) {
        return repoIdr.getInstructoresMateriaParaleloByTipo(codMateriaParalelo, "INSTRUCTOR");
    }

    @Override
    public InstructorDatos getCoordinador(Integer codMateriaParalelo) {
        return repoIdr.getInstructoresMateriaParaleloByTipo(codMateriaParalelo, "COORDINADOR").stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<InstructorMateriaReadDto> getMateriaInfoDto() {
        Set<Integer> codigosUnicos = new HashSet<>();
        List<InstructorMateriaReadDto> lista = new ArrayList<>();

        for (InstructorMateriaParalelo item : this.getInstructoresMateriaParalelo()) {
            codigosUnicos.add(item.getCodMateriaParalelo());
        }
        for (Integer codigo : codigosUnicos) {
            MateriaParalelo objMP = serviceMP.getById(codigo).get();
            MateriaPeriodo objPe = serviceMPe.getById(objMP.getCodMateriaPeriodo()).get();
            Aula objAula = serviceAula.getById(objPe.getCodAula()).get();
            Paralelo objParalelo = serviceParalelo.getById(objMP.getCodParalelo()).get();
            Materia objMateria = serviceMateria.getById(objPe.getCodMateria()).get();
            List<InstructorDatos> instructores = this.getInstructores(objMP.getCodMateriaParalelo());
            List<InstructorDatos> asistentes = this.getInstructoresAsistentes(objMP.getCodMateriaParalelo());
            InstructorDatos coordinador = this.getCoordinador(objMP.getCodMateriaParalelo());
            InstructorMateriaReadDto newObj = new InstructorMateriaReadDto();
            newObj.setNombreMateria(objMateria.getNombre());
            newObj.setNombreEjeMateria(objMateria.getCodEjeMateria().toString());
            newObj.setNombreParalelo(objParalelo.getNombreParalelo());
            newObj.setAula(objAula);
            newObj.setCoordinador(coordinador);
            newObj.setAsistentes(asistentes);
            newObj.setInstructores(instructores);
            lista.add(newObj);
        }
        return lista;

    }

    @Override
    public List<InformacionMateriaDto> getInformacionMateriaDto() {
        /*
        System.out.println("getInformacionMateriaDtoasasasasa");
        return repoIMP.getInformacionMateria();*/
        return null;


    }


}
