package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_CONVOCATORIA;
import static epntech.cbdmq.pe.constante.EspecializacionConst.CONVOCATORIA_CURSO_EXISTE;
import static epntech.cbdmq.pe.constante.EspecializacionConst.CURSO_NO_APROBADO;
import static epntech.cbdmq.pe.constante.EspecializacionConst.FECHA_INVALIDA;
import static epntech.cbdmq.pe.constante.MensajesConst.NO_PARAMETRO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.Parametro;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.especializacion.ConvocatoriaCurso;
import epntech.cbdmq.pe.dominio.admin.especializacion.ConvocatoriaCursoData;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.util.ListaRequisitos;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.ParametroRepository;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaDocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.ConvocatoriaCursoDataRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.ConvocatoriaCursoEspRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.ConvocatoriaCursoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CursoRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.especializacion.ConvocatoriaCursoService;
import jakarta.mail.MessagingException;

@Service
public class ConvocatoriaCursoServiceImpl implements ConvocatoriaCursoService {

    @Autowired
    private ConvocatoriaCursoEspRepository convocatoriaCursoEspRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private ConvocatoriaCursoRepository convocatoriaCursoRepository;
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private ConvocatoriaDocumentoRepository convocatoriaDocumentoRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ConvocatoriaCursoDataRepository convocatoriaCursoDataRepository;
    @Autowired
    private ParametroRepository parametroRepository;
    @Value("${url.proceso.inscripcion}")
    public String URLINCSCRIPCION;

    @Override
    public ConvocatoriaCurso save(ConvocatoriaCurso convocatoriaCurso
                                  //, List<MultipartFile> archivos
    )
            throws DataException, IOException, ArchivoMuyGrandeExcepcion {

        Optional<ConvocatoriaCurso> objGuardado = convocatoriaCursoRepository
                .findByNombreConvocatoriaIgnoreCase(convocatoriaCurso.getNombreConvocatoria());
        if (objGuardado.isPresent()) {
            throw new DataException(REGISTRO_YA_EXISTE);
        }

        Optional<Curso> curso = cursoRepository.getCursoAprobado(convocatoriaCurso.getCodCursoEspecializacion());
        if (curso.isEmpty())
            throw new DataException(CURSO_NO_APROBADO);

        Optional<ConvocatoriaCurso> convocatoriaCursoOptional = convocatoriaCursoRepository
                .getConvocatoriaByCodCursoEspecializacion(convocatoriaCurso.getCodCursoEspecializacion());
        // System.out.println("convocatoriaCursoOptional.isPresent(): " +
        // convocatoriaCursoOptional.isPresent());
        if (convocatoriaCursoOptional.isPresent())
            throw new DataException(CONVOCATORIA_CURSO_EXISTE);

        return convocatoriaCursoEspRepository.insertarConvocatoriaDocumentos(convocatoriaCurso
                //, archivos
        );
    }

    @Override
    public List<ConvocatoriaCurso> listAll() {
        return convocatoriaCursoRepository.getAll();
    }

    @Override
    public Optional<ConvocatoriaCurso> getByID(Long codConvocatoria) throws DataException {
        Optional<ConvocatoriaCurso> convocatoriaCurso = convocatoriaCursoRepository.getConvocatoriaCursoxId(codConvocatoria);
        if (convocatoriaCurso.isEmpty())
            throw new DataException(REGISTRO_NO_EXISTE);

        return convocatoriaCurso;
    }

    @Override
    public Optional<ConvocatoriaCurso> getByCurso(Long codCursoEspecializacion) throws DataException {
        Optional<ConvocatoriaCurso> convocatoriaCurso = convocatoriaCursoRepository
                .getConvocatoriaByCodCursoEspecializacion(codCursoEspecializacion);
        if (convocatoriaCurso.isEmpty())
            throw new DataException(REGISTRO_NO_EXISTE);

        return convocatoriaCurso;
    }

    @Override
    public void delete(Long codConvocatoria) throws DataException {
        Optional<ConvocatoriaCurso> convocatoriaCurso = convocatoriaCursoRepository.findById(codConvocatoria);
        if (convocatoriaCurso.isEmpty())
            throw new DataException(REGISTRO_NO_EXISTE);
        convocatoriaCursoRepository.deleteById(codConvocatoria);
    }

    @Override
    public ConvocatoriaCurso update(ConvocatoriaCurso convocatoriaCursoActualizado) throws DataException {
        LocalDate fechaActual = LocalDate.now();

        Optional<ConvocatoriaCurso> objGuardado = convocatoriaCursoRepository.findById(convocatoriaCursoActualizado.getCodConvocatoria());
        if (objGuardado.isEmpty())
            throw new DataException(REGISTRO_NO_EXISTE);

        if (convocatoriaCursoActualizado.getFechaInicioConvocatoria().isBefore(fechaActual))
            throw new DataException(FECHA_INVALIDA);
        if (convocatoriaCursoActualizado.getFechaFinConvocatoria().isBefore(fechaActual))
            throw new DataException(FECHA_INVALIDA);
        if (convocatoriaCursoActualizado.getFechaFinConvocatoria().isBefore(convocatoriaCursoActualizado.getFechaInicioConvocatoria()))
            throw new DataException(FECHA_INVALIDA);
		
		/*Optional<ConvocatoriaCurso> objGuardado = convocatoriaCursoRepository
				.findByNombreConvocatoriaIgnoreCase(convocatoriaCursoActualizado.getNombreConvocatoria());
		if (objGuardado.isPresent()
				&& !objGuardado.get().getCodConvocatoria().equals(convocatoriaCursoActualizado.getCodConvocatoria())) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}*/

        if (convocatoriaCursoActualizado.getEstado() == null)
            convocatoriaCursoActualizado.setEstado(objGuardado.get().getEstado());

        return convocatoriaCursoRepository.save(convocatoriaCursoActualizado);
    }

    @Transactional
    @Override
    public void deleteDocumento(Long codConvocatoria, Long codDocumento) throws DataException {
        Optional<Documento> documentoOptional;
        Documento documento = new Documento();

        // System.out.println("id: " + codDocumento);
        documentoOptional = documentoRepository.findById(codDocumento.intValue());
        documento = documentoOptional.get();

        convocatoriaDocumentoRepository.deleteByCodConvocatoriaAndCodDocumento(codConvocatoria.intValue(),
                codDocumento.intValue());
        documentoRepository.deleteById(codDocumento.intValue());

        Path ruta = Paths.get(documento.getRuta());

        //System.out.println("ruta: " + ruta);
        if (Files.exists(ruta)) {
            try {
                //System.out.println("entra");
                Files.delete(ruta);

            } catch (Exception e) {

                throw new DataException(e.getMessage());
                // e.printStackTrace();
            }

        }

    }

    @Override
    public void notificar(Long codConvocatoria) throws MessagingException, DataException, IOException {
        Optional<ConvocatoriaCurso> convocatoriaCursoOptional = this.getByID(codConvocatoria);
        if (convocatoriaCursoOptional.isEmpty())
            throw new DataException(REGISTRO_NO_EXISTE);

        ConvocatoriaCurso convocatoriaCurso = new ConvocatoriaCurso();
        convocatoriaCurso = convocatoriaCursoOptional.get();
        Curso curso = cursoRepository.getReferenceById(convocatoriaCurso.getCodCursoEspecializacion());

        String[] destinatarios = convocatoriaCurso.getCorreo().split(",");
        String link = URLINCSCRIPCION +curso.getCodCursoEspecializacion();

        emailService.sendEmailHtmlToList(destinatarios, EMAIL_SUBJECT_CONVOCATORIA, curso.getNombre(),convocatoriaCurso.getFechaInicioConvocatoria().toString(),curso.getFechaInicioCurso().toString(),curso.getFechaFinCurso().toString(),curso.getNumeroCupo().toString(),curso.getRequisitos().toString(),link);

    }

    @Override
    public Boolean validaConvocatoriaCursoActiva(Long codConvocatoria) {
        return convocatoriaCursoRepository.validaConvocatoriaCursoActiva(codConvocatoria);
    }

}
