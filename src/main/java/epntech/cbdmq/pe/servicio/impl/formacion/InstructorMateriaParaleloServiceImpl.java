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
    //TODO no se usa, limpiar o optimizar
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
    //TODO tampoco se utiliza
    public List<InstructorMateriaReadDto> getMateriaInfoDto() {
        Set<Integer> codigosUnicos = new HashSet<>();
        List<InstructorMateriaReadDto> lista = new ArrayList<>();

        for (InstructorMateriaParalelo item : this.getInstructoresMateriaParalelo()) {
            codigosUnicos.add(item.getCodMateriaParalelo());
        }
        for (Integer codigo : codigosUnicos) {
            MateriaParalelo objMP = serviceMPa.getById(codigo).get();
            MateriaPeriodo objPe = serviceMPe.getById(objMP.getCodMateriaPeriodo()).get();
            Aula objAula = serviceAula.getById(objPe.getCodAula()).get();
            Paralelo objParalelo = serviceParalelo.getById(objMP.getCodParalelo()).get();
            Materia objMateria = serviceMateria.getById(objPe.getCodMateria()).get();
            EjeMateria objEjeMateria = serviceEje.getByIdEje(objMateria.getCodEjeMateria().longValue()).get();
            List<InstructorDatos> instructores = this.getInstructores(objMP.getCodMateriaParalelo());
            List<InstructorDatos> asistentes = this.getInstructoresAsistentes(objMP.getCodMateriaParalelo());
            InstructorDatos coordinador = this.getCoordinador(objMP.getCodMateriaParalelo());

        }
        return lista;

    }
    @Override
    @Transactional
    public Boolean actualizarInstructorMateriaParalelo(Integer codMateriaPeriodo, Integer codCoordinador,  Integer[] codAsistentes, Integer[] codInstructores, Integer codParalelo) throws DataException {
        Optional<MateriaPeriodo> objGuardado = repoMPe.findById(codMateriaPeriodo);
        if(objGuardado.isEmpty()){
            throw new RuntimeException();
        }
        MateriaPeriodo objMPeII = objGuardado.get();
        Optional<MateriaParalelo> objGuardadoMPa = repoMPa.findByCodMateriaPeriodoAndCodParalelo(objMPeII.getCodMateriaPeriodo(), codParalelo);
        if(objGuardadoMPa.isEmpty()){
            throw new RuntimeException();
        }
        MateriaParalelo objMPaII = objGuardadoMPa.get();
        InstructorDatos coordinador= this.getCoordinador(objMPaII.getCodMateriaParalelo());
        if (coordinador!=null) {
            repoIMP.deleteByCodInstructorAndCodTipoInstructorAndCodMateriaParalelo(coordinador.getCodInstructor().intValue(), 3, objMPaII.getCodMateriaParalelo());
        }
        InstructorMateriaParalelo objCoordinador = new InstructorMateriaParalelo();
        objCoordinador.setCodMateriaParalelo(objMPaII.getCodMateriaParalelo());
        objCoordinador.setCodInstructor(codCoordinador);
        //TODO cambiar para buscar el objeto a partir del nombre del tipo instructor
        objCoordinador.setCodTipoInstructor(3);
        repoIMP.save(objCoordinador);

        List<InstructorDatos> instructores = this.getInstructores(objMPaII.getCodMateriaParalelo());
        List<Long> idsInstructoresActuales = instructores.stream()
                .map(fiscalizador -> fiscalizador.getCodInstructor())
                .collect(Collectors.toList());

        // si es que los actuales instructores no se encuentra en la lista de nuevos, entonces se elimina
        for (InstructorDatos instructor : instructores) {
            Integer idInstructor = instructor.getCodInstructor().intValue();
            if (!Arrays.asList(codInstructores).contains(idInstructor)) {
                repoIMP.deleteByCodInstructorAndCodTipoInstructorAndCodMateriaParalelo(idInstructor, 2,objMPaII.getCodMateriaParalelo());
            }
        }

        //si es que los nuevos instructores no se encuentra en la lista de actuales, entonces se agrega
        for (Integer codInstructorNuevo : codInstructores) {
            if (!idsInstructoresActuales.contains(codInstructorNuevo)) {
                    InstructorMateriaParalelo objInstructor = new InstructorMateriaParalelo();
                    objInstructor.setCodMateriaParalelo(objMPaII.getCodMateriaParalelo());
                    objInstructor.setCodInstructor(codInstructorNuevo);
                    //TODO cambiar para buscar el objeto a partir del nombre del tipo instructor
                    objInstructor.setCodTipoInstructor(2);
                    repoIMP.save(objInstructor);
            }
        }

        List<InstructorDatos> asistentes = this.getInstructoresAsistentes(objMPaII.getCodMateriaParalelo());
        List<Long> idsAsistentesActuales = asistentes.stream()
                .map(fiscalizador -> fiscalizador.getCodInstructor())
                .collect(Collectors.toList());

        // si es que los actuales instructores no se encuentra en la lista de nuevos, entonces se elimina
        for (InstructorDatos asistente : asistentes) {
            Integer idAsistentes = asistente.getCodInstructor().intValue();
            if (!Arrays.asList(codAsistentes).contains(idAsistentes)) {
                repoIMP.deleteByCodInstructorAndCodTipoInstructorAndCodMateriaParalelo(idAsistentes, 1,objMPaII.getCodMateriaParalelo());
            }
        }

        //si es que los nuevos instructores no se encuentra en la lista de actuales, entonces se agrega
        for (Integer codAsistenteNuevo : codAsistentes) {
            if (!idsAsistentesActuales.contains(codAsistenteNuevo)) {
                InstructorMateriaParalelo objInstructor = new InstructorMateriaParalelo();
                objInstructor.setCodMateriaParalelo(objMPaII.getCodMateriaParalelo());
                objInstructor.setCodInstructor(codAsistenteNuevo);
                //TODO cambiar para buscar el objeto a partir del nombre del tipo instructor
                objInstructor.setCodTipoInstructor(1);
                repoIMP.save(objInstructor);
            }
        }

        return true;
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
