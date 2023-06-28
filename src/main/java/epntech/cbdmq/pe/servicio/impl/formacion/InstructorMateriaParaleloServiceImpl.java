package epntech.cbdmq.pe.servicio.impl.formacion;

import epntech.cbdmq.pe.dominio.admin.Aula;
import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import epntech.cbdmq.pe.dominio.admin.Modulo;
import epntech.cbdmq.pe.dominio.admin.formacion.InformacionMateriaDto;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaReadDto;
import epntech.cbdmq.pe.dominio.util.InstructorDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.MateriaPeriodoRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.repositorio.admin.formacion.InstructorMateriaParaleloRepository;
import epntech.cbdmq.pe.repositorio.admin.formacion.MateriaParaleloRepository;
import epntech.cbdmq.pe.servicio.AulaService;
import epntech.cbdmq.pe.servicio.formacion.InstructorMateriaParaleloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private AulaService serviceAula;

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
        MateriaPeriodo objMPeII=new MateriaPeriodo();
        if (objGuardado.isPresent()&& !objGuardado.get().getCodMateriaPeriodo().equals(objMPe.getCodMateriaPeriodo())) {
            objMPeII=objGuardado.get();
        }else {
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
    public List<InstructorDatos> getInstructoresAsistentes(Long codMateriaParalelo) {
        return repoIMP.getInstructoresMateriaParaleloByTipo(codMateriaParalelo,"ASISTENTE");

    }

    @Override
    public List<InstructorDatos> getInstructores(Long codMateriaParalelo) {
        return repoIMP.getInstructoresMateriaParaleloByTipo(codMateriaParalelo,"INSTRUCTOR");
    }

    @Override
    public InstructorDatos getCoordinador(Long codMateriaParalelo) {
        return repoIMP.getInstructoresMateriaParaleloByTipo(codMateriaParalelo, "COORDINADOR").stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<InstructorMateriaReadDto> getMateriaInfoDto() {
        List<InformacionMateriaDto> listaInfo = this.getInformacionMateriaDto();
        List<InstructorMateriaReadDto> lista = new ArrayList<>();
        System.out.println("listaaaaa vale"+listaInfo);
        for(InformacionMateriaDto materiaDto :listaInfo){
            Aula objAula = serviceAula.getById(materiaDto.getCodAula().intValue()).get();
            System.out.println("aulaaaaaaaa" + objAula);
            List<InstructorDatos> instructores= this.getInstructores(materiaDto.getCodMateriaParalelo());
            List<InstructorDatos> asistentes= this.getInstructoresAsistentes(materiaDto.getCodMateriaParalelo());
            InstructorDatos coordinador= this.getCoordinador(materiaDto.getCodMateriaParalelo());
            InstructorMateriaReadDto newObj= new InstructorMateriaReadDto();
            newObj.setNombreMateria(materiaDto.getNombreMateria());
            newObj.setNombreEjeMateria(materiaDto.getNombreEjeMateria());
            newObj.setNombreParalelo(materiaDto.getNombreParalelo());
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
        System.out.println("getInformacionMateriaDtoasasasasa");
        return repoIMP.getInformacionMateria();
    }


}
