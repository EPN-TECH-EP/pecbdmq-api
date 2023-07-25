package epntech.cbdmq.pe.servicio.impl.formacion;

import epntech.cbdmq.pe.dominio.admin.*;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaParalelosDto;
import epntech.cbdmq.pe.dominio.admin.formacion.InstructorMateriaReadDto;
import epntech.cbdmq.pe.dominio.util.InstructorDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.InstructorDatosRepository;
import epntech.cbdmq.pe.repositorio.admin.MateriaPeriodoRepository;
import epntech.cbdmq.pe.repositorio.admin.formacion.InstructorMateriaParaleloRepository;
import epntech.cbdmq.pe.repositorio.admin.formacion.MateriaParaleloRepository;
import epntech.cbdmq.pe.servicio.*;
import epntech.cbdmq.pe.servicio.AulaService;
import epntech.cbdmq.pe.servicio.MateriaPeriodoService;
import epntech.cbdmq.pe.servicio.MateriaService;
import epntech.cbdmq.pe.servicio.ParaleloService;
import epntech.cbdmq.pe.servicio.EjeMateriaService;
import epntech.cbdmq.pe.servicio.formacion.InstructorMateriaParaleloService;
import epntech.cbdmq.pe.servicio.formacion.MateriaParaleloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class InstructorMateriaParaleloServiceImpl implements InstructorMateriaParaleloService {
    @Autowired
    InstructorMateriaParaleloRepository repoIMP;
    @Autowired
    private MateriaPeriodoRepository repoMPe;
    @Autowired
    private MateriaParaleloRepository repoMPa;
    @Autowired
    private InstructorDatosRepository repoIdr;
    @Autowired
    private AulaService serviceAula;
    @Autowired
    private MateriaParaleloService serviceMPa;
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
    public List<InstructorMateriaParalelo> getInstructoresMateriaParalelo() {
        return repoIMP.findAll();
    }

    @Override
    public InstructorMateriaParalelo save(InstructorMateriaParalelo newObj) {
        return repoIMP.save(newObj);
    }

    @Override
    @Transactional
    public Boolean asignarInstructorMateriaParaleloAll(Integer codMateria, Integer codCoordinador, Integer codAula, Integer[] codAsistentes, Integer[] codInstructores, Integer codParalelo) throws DataException {
        MateriaPeriodo objMPe = new MateriaPeriodo();
        objMPe.setCodPeriodoAcademico(servicePeriodo.getPAActivo());
        objMPe.setCodMateria(codMateria);
        objMPe.setCodAula(codAula);
        Optional<MateriaPeriodo> objGuardado = serviceMPe.findByCodMateriaAndCodPeriodoAcademico(objMPe.getCodMateria(), objMPe.getCodPeriodoAcademico());
        MateriaPeriodo objMPeII = new MateriaPeriodo();
        if (objGuardado.isPresent() && !objGuardado.get().getCodMateriaPeriodo().equals(objMPe.getCodMateriaPeriodo())) {
            objMPeII = objGuardado.get();
        } else {
            objMPeII = serviceMPe.save(objMPe);
        }

        MateriaParalelo objMpa = new MateriaParalelo();
        objMpa.setCodMateriaPeriodo(objMPeII.getCodMateriaPeriodo());
        objMpa.setCodParalelo(codParalelo);
        MateriaParalelo objMPaII = serviceMPa.saveMateriaParalelo(objMpa);

        InstructorMateriaParalelo objCoordinador = new InstructorMateriaParalelo();
        objCoordinador.setCodMateriaParalelo(objMPaII.getCodMateriaParalelo());
        objCoordinador.setCodInstructor(codCoordinador);
        objCoordinador.setCodTipoInstructor(3);
        save(objCoordinador);

        for (Integer codAsistente : codAsistentes) {
            InstructorMateriaParalelo objAsistente = new InstructorMateriaParalelo();
            objAsistente.setCodMateriaParalelo(objMPaII.getCodMateriaParalelo());
            objAsistente.setCodInstructor(codAsistente);
            objAsistente.setCodTipoInstructor(1);
            save(objAsistente);
        }

        for (Integer codInstructor : codInstructores) {
            InstructorMateriaParalelo objInstructor = new InstructorMateriaParalelo();
            objInstructor.setCodMateriaParalelo(objMPaII.getCodMateriaParalelo());
            objInstructor.setCodInstructor(codInstructor);
            objInstructor.setCodTipoInstructor(2);
            save(objInstructor);
        }

        return true;
    }

    @Override
    @Transactional
    public Boolean asignarInstructortoMateriaParalelo(Integer codMateriaPeriodo, Integer codCoordinador, Integer[] codAsistentes, Integer[] codInstructores, Integer codParalelo) {
        Optional<MateriaParalelo> objMPAGuardado = serviceMPa.findByCodMateriaPeriodoAndCodParalelo(codMateriaPeriodo,codParalelo);
        if(objMPAGuardado.isEmpty()){
            throw new RuntimeException("No se encuentra dicha materia en ese paralelo");
        }

        InstructorMateriaParalelo objCoordinador = new InstructorMateriaParalelo();
        objCoordinador.setCodMateriaParalelo(objMPAGuardado.get().getCodMateriaParalelo());
        objCoordinador.setCodInstructor(codCoordinador);
        //TODO cambiar para buscar el objeto a partir del nombre del tipo instructor
        objCoordinador.setCodTipoInstructor(3);
        repoIMP.save(objCoordinador);

        for (Integer codAsistente : codAsistentes) {
            InstructorMateriaParalelo objAsistente = new InstructorMateriaParalelo();
            objAsistente.setCodMateriaParalelo(objMPAGuardado.get().getCodMateriaParalelo());
            objAsistente.setCodInstructor(codAsistente);
            objAsistente.setCodTipoInstructor(1);
            repoIMP.save(objAsistente);
        }

        for (Integer codInstructor : codInstructores) {
            InstructorMateriaParalelo objInstructor = new InstructorMateriaParalelo();
            objInstructor.setCodMateriaParalelo(objMPAGuardado.get().getCodMateriaParalelo());
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
    @Transactional
    public Boolean actualizarInstructorMateriaParalelo(Integer codMateriaPeriodo, Integer codCoordinador, Integer[] codAsistentes, Integer[] codInstructores, Integer codParalelo) throws DataException {
        Optional<MateriaPeriodo> objGuardado = repoMPe.findById(codMateriaPeriodo);
        if (objGuardado.isEmpty()) {
            throw new RuntimeException();
        }
        MateriaPeriodo objMPeII = objGuardado.get();
        Optional<MateriaParalelo> objGuardadoMPa = repoMPa.findByCodMateriaPeriodoAndCodParalelo(objMPeII.getCodMateriaPeriodo(), codParalelo);
        if (objGuardadoMPa.isEmpty()) {
            throw new RuntimeException();
        }
        MateriaParalelo objMPaII = objGuardadoMPa.get();
        InstructorDatos coordinador = this.getCoordinador(objMPaII.getCodMateriaParalelo());
        if (coordinador != null) {
            repoIMP.deleteByCodInstructorAndCodTipoInstructorAndCodMateriaParalelo(coordinador.getCodInstructor().intValue(), 3, objMPaII.getCodMateriaParalelo());
    }

        // Check and save coordinador (codTipoInstructor = 3)
        saveOrUpdateInstructor(objMPaII.getCodMateriaParalelo(), codCoordinador, 3);

        // Delete instructors that are no longer needed
        deleteInstructorsNotNeeded(objMPaII.getCodMateriaParalelo(), codInstructores, 2);

        // Add new instructors if they don't already exist
        for (Integer codInstructorNuevo : codInstructores) {
            saveOrUpdateInstructor(objMPaII.getCodMateriaParalelo(), codInstructorNuevo, 2);
    }

        // Delete asistentes that are no longer needed
        deleteInstructorsNotNeeded(objMPaII.getCodMateriaParalelo(), codAsistentes, 1);

        // Add new asistentes if they don't already exist
        for (Integer codAsistenteNuevo : codAsistentes) {
            saveOrUpdateInstructor(objMPaII.getCodMateriaParalelo(), codAsistenteNuevo, 1);
        }

        return true;
        }

    private void saveOrUpdateInstructor(Integer codMateriaParalelo, Integer codInstructor, Integer codTipoInstructor) {
        Optional<InstructorMateriaParalelo> existingInstructor = repoIMP.findByCodInstructorAndCodTipoInstructorAndCodMateriaParalelo(codInstructor, codTipoInstructor, codMateriaParalelo);
        if (existingInstructor.isEmpty()) {
            InstructorMateriaParalelo objInstructor = new InstructorMateriaParalelo();
            objInstructor.setCodMateriaParalelo(codMateriaParalelo);
            objInstructor.setCodInstructor(codInstructor);
            objInstructor.setCodTipoInstructor(codTipoInstructor);
            repoIMP.save(objInstructor);
        }
    }

// ... Your other methods ...

    private void deleteInstructorsNotNeeded(Integer codMateriaParalelo, Integer[] codInstructors, Integer codTipoInstructor) {
        List<InstructorMateriaParalelo> instructors = repoIMP.findAllByCodTipoInstructorAndCodMateriaParalelo(codTipoInstructor, codMateriaParalelo);

        for (InstructorMateriaParalelo instructor : instructors) {
            Integer idInstructor = instructor.getCodInstructor();
            if (!Arrays.asList(codInstructors).contains(idInstructor)) {
                repoIMP.deleteByCodInstructorAndCodTipoInstructorAndCodMateriaParalelo(idInstructor, codTipoInstructor, codMateriaParalelo);
            }
    }
    }


    @Override
    public InstructorMateriaParalelosDto getMateriaPAParaleloNombres() {
        InstructorMateriaParalelosDto obj = new InstructorMateriaParalelosDto();
        List<InstructorMateriaReadDto> materias= serviceMPa.getMateriaNombres(servicePeriodo.getPAActivo());
        List<Paralelo> paralelos= serviceParalelo.getParalelosPA();
        obj.setParalelos(paralelos);
        for (InstructorMateriaReadDto codigo : materias) {
            MateriaParalelo objMP = serviceMPa.findByCodMateriaPeriodoAndCodParalelo(codigo.getCodMateriaPeriodo(),codigo.getCodParalelo()).get();
            List<InstructorDatos> instructores = this.getInstructores(objMP.getCodMateriaParalelo());
            List<InstructorDatos> asistentes = this.getInstructoresAsistentes(objMP.getCodMateriaParalelo());
            InstructorDatos coordinador = this.getCoordinador(objMP.getCodMateriaParalelo());
            codigo.setInstructores(instructores.toArray(new InstructorDatos[instructores.size()]));
            codigo.setAsistentes(asistentes.toArray(new InstructorDatos[asistentes.size()]));
            codigo.setCoordinador(coordinador);
        }
        obj.setMaterias(materias);

        return obj;
    }




}
