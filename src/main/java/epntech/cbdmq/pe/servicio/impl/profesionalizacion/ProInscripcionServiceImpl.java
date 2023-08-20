package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.DocumentoFor;
import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProInscripcion;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProInscripcionDocumento;
import epntech.cbdmq.pe.dominio.util.DatosFile;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProConvocatoriaRequisitoDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProCumpleRequisitosDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProInscripcionDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProConvocatoriaRequisitosDatosRepository;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProDocumentoForRepository;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProInscripcionDatosRepository;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProValidaCumpleRequisitoRepository;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProConvocatoriaRepository;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProConvocatoriaRequisitoRepository;
import epntech.cbdmq.pe.repositorio.fichaPersonal.EstudianteRepository;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProDocumentosRepository;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProInscripcionRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProInscripcionService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_INSCRIPCION_FOR;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROFESIONALIZACION;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

@Service
public class ProInscripcionServiceImpl extends ProfesionalizacionServiceImpl<ProInscripcion, Integer, ProInscripcionRepository> implements ProInscripcionService {
    private final ProInscripcionDatosRepository datosRepository;
    private final ProConvocatoriaRequisitoRepository convocatoriaRequisitoRepository;
    private final ProValidaCumpleRequisitoRepository validaCumpleRequisitoRepository;
    private final ProConvocatoriaRequisitosDatosRepository requisitosDatosRepository;
    private final ProDocumentosRepository proDocumentosRepository;
    private final ProDocumentoForRepository proDocumentoForRepository;
    private final EmailService emailService;
    private final ProConvocatoriaRepository proConvocatoriaRepository;
    private final EstudianteRepository estudianteRepository;

    public ProInscripcionServiceImpl(ProInscripcionRepository repository, ProInscripcionDatosRepository datosRepository, ProConvocatoriaRequisitoRepository convocatoriaRequisitoRepository, ProValidaCumpleRequisitoRepository validaCumpleRequisitoRepository, ProConvocatoriaRequisitosDatosRepository requisitosDatosRepository, ProDocumentosRepository proDocumentosRepository, ProDocumentoForRepository proDocumentoForRepository, EmailService emailService, ProConvocatoriaRepository proConvocatoriaRepository, EstudianteRepository estudianteRepository) {
        super(repository);
        this.datosRepository = datosRepository;
        this.convocatoriaRequisitoRepository = convocatoriaRequisitoRepository;
        this.validaCumpleRequisitoRepository = validaCumpleRequisitoRepository;
        this.requisitosDatosRepository = requisitosDatosRepository;
        this.proDocumentosRepository = proDocumentosRepository;
        this.proDocumentoForRepository = proDocumentoForRepository;
        this.emailService = emailService;
        this.proConvocatoriaRepository = proConvocatoriaRepository;
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public ProInscripcion save(ProInscripcion obj) throws DataException {
        Optional<ProInscripcion> objGuardado = repository.findByCodEstudiante(obj.getCodEstudiante());
        if (objGuardado.isPresent()) {

            ProInscripcion stp = objGuardado.get();
            if (stp.getEstado().compareToIgnoreCase(EstadosConst.ELIMINADO) == 0) {
                stp.setEstado(EstadosConst.ACTIVO);
                return repository.save(stp);
            } else {
                throw new DataException(REGISTRO_YA_EXISTE);
            }
        }
        return super.save(obj);
    }

    @Override
    public Optional<ProInscripcionDto> getProDatosInscripcion(Integer codInscripcion) {
        return datosRepository.getProDatosInscripcion(codInscripcion);
    }

    @Override
    public List<ProInscripcionDto> findByCodConvocatoria(Integer codConvocatoria) {
        return datosRepository.findByCodConvocatoria(codConvocatoria);
    }

    @Override
    public List<ProInscripcionDto> findByAceptado() {
        return datosRepository.findByAceptado();
    }

    public List<ProCumpleRequisitosDto> findRequisitosByInscripcion(Integer codInscripcion) {
        List<ProConvocatoriaRequisitoDto> codigoInscripcion = requisitosDatosRepository.findByCodigoInscripcion(codInscripcion);
        List<ProCumpleRequisitosDto> requisitosByInscripcion = validaCumpleRequisitoRepository.findRequisitosByInscripcion(codInscripcion);
        codigoInscripcion.forEach(dto -> {
            Optional<ProCumpleRequisitosDto> requisitosDto = requisitosByInscripcion.stream().filter(x -> Objects.equals(x.getCodRequisito(), dto.getCodigoRequisito())).findAny();
            if (requisitosDto.isEmpty()) {
                ProCumpleRequisitosDto requisitosDto1 = new ProCumpleRequisitosDto();
                requisitosDto1.setCodRequisito(dto.getCodigoRequisito());
                requisitosDto1.setNombreRequisito(dto.getNombreRequisito());
                requisitosDto1.setCodInscripcion(codInscripcion);
                requisitosDto1.setCumple(false);
                requisitosByInscripcion.add(requisitosDto1);
            }
        });
        return requisitosByInscripcion;
    }

    public ProInscripcion insertarInscripcionConDocumentos(ProInscripcion proInscripcion, List<MultipartFile> docsInscripcion) throws DataException {
        //Si no existe codigo estudiante se debe crear
        if(proInscripcion.getCodEstudiante() == null) {
            setEstudiante(proInscripcion);
        }

        proInscripcion.setEstado("ACTIVO");
        Optional<ProInscripcion> byCodEstudianteAndCodConvocatoria = repository.findByCodEstudianteAndCodConvocatoria(proInscripcion.getCodEstudiante(), proInscripcion.getCodConvocatoria());
        if (byCodEstudianteAndCodConvocatoria.isPresent()) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }

        ProInscripcion proInscripcion1 = repository.save(proInscripcion);
        List<DatosFile> archivosConvocatoria = new ArrayList<>();
        try {
            archivosConvocatoria = guardarArchivo(docsInscripcion, PATH_PROFESIONALIZACION + PATH_PROCESO_INSCRIPCION_FOR, proInscripcion.getCodInscripcion().toString());
        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("errorHeader", e.getMessage());
        }

        Set<DocumentoFor> dPeriodoAcademico = new HashSet<>();

        for (DatosFile datosFile : archivosConvocatoria) {
            DocumentoFor dd1 = new DocumentoFor();
            dd1.setEstado("ACTIVO");
            dd1.setNombre(datosFile.getNombre());
            dd1.setRuta(datosFile.getRuta());
            dPeriodoAcademico.add(dd1);
        }
        proDocumentoForRepository.saveAll(dPeriodoAcademico);

        Set<ProInscripcionDocumento> dPeriodoAcademicoSet = new HashSet<>();
        for (DocumentoFor elemento : dPeriodoAcademico) {
            ProInscripcionDocumento proInscripcionDocumento = new ProInscripcionDocumento();
            proInscripcionDocumento.setCodInscripcion(proInscripcion1.getCodInscripcion());
            proInscripcionDocumento.setCodDocumento(elemento.getCodDocumento());
            dPeriodoAcademicoSet.add(proInscripcionDocumento);
        }
        proDocumentosRepository.saveAll(dPeriodoAcademicoSet);
        var insp = this.getProDatosInscripcion(proInscripcion1.getCodInscripcion()).get();
        var con = proConvocatoriaRepository.findById(proInscripcion.getCodConvocatoria()).get();
        var correoPersonal = insp.getCorreoPersonal();

        emailService.sendInscripcionFormacionEmail(proInscripcion.getEmail(), con, insp);
        return proInscripcion1;
    }

    private void setEstudiante(ProInscripcion proInscripcion) {
        Estudiante estudiante = new Estudiante();
        estudiante.setCodUnicoEstudiante("");
        estudiante.setCodDatosPersonales(proInscripcion.getCodDatosPersonales());
        estudiante.setEstado("ACTIVO");
        estudianteRepository.save(estudiante);
        setCodigoUnicoEstudiante(estudiante);
        proInscripcion.setCodEstudiante(estudiante.getCodEstudiante());

    }

    private void setCodigoUnicoEstudiante(Estudiante estudiante) {
        String numeroComoCadena = String.format("%06d", estudiante.getCodEstudiante());
        String codigo = "P" + numeroComoCadena;
        estudiante.setCodUnicoEstudiante(codigo);
        estudianteRepository.save(estudiante);
    }
}
