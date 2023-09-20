package epntech.cbdmq.pe.servicio.impl.profesionalizacion;

import epntech.cbdmq.pe.dominio.Parametro;
import epntech.cbdmq.pe.dominio.admin.profesionalizacion.ProConvocatoria;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodos;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.ProConvocatoriaRequisitoDto;
import epntech.cbdmq.pe.dominio.util.profesionalizacion.repository.ProConvocatoriaRequisitosDatosRepository;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.ParametroRepository;
import epntech.cbdmq.pe.repositorio.admin.profesionalizacion.ProConvocatoriaRepository;
import epntech.cbdmq.pe.repositorio.profesionalizacion.ProPeriodosRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProConvocatoriaService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_CONVOCATORIA_PRO;
import static epntech.cbdmq.pe.constante.MensajesConst.*;

@Service
public class ProConvocatoriaServiceImpl extends ProfesionalizacionServiceImpl<ProConvocatoria, Integer, ProConvocatoriaRepository> implements ProConvocatoriaService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProPeriodosRepository proPeriodosRepository;

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private ProConvocatoriaRequisitosDatosRepository convocatoriaRequisitoDatosRepository;

    public ProConvocatoriaServiceImpl(ProConvocatoriaRepository repository) {
        super(repository);
    }

    @Value("${url.profesionalizacion.inscripcion}")
    public String URL_INSCRIPCION;

    @Override
    public Optional<ProConvocatoria> getByCodigoUnicoConvocatoria(String codigoUnicoConvocatoria) {
        return repository.findByCodigoUnicoConvocatoria(codigoUnicoConvocatoria);
    }

    @Override
    public ProConvocatoria save(ProConvocatoria obj) throws DataException {
        Optional<ProConvocatoria> proConvocatoria = repository.findByCodigoUnicoConvocatoria(obj.getCodigoUnicoConvocatoria());
        if (proConvocatoria.isPresent())
            throw new DataException(REGISTRO_YA_EXISTE);
        proConvocatoria = repository.findByCodPeriodo(obj.getCodPeriodo());
        if (proConvocatoria.isPresent())
            throw new DataException(CONVOCATORIA_YA_EXISTE);
        return super.save(obj);
    }

    @Override
    public ProConvocatoria update(ProConvocatoria datosGuardados) throws DataException {
        Optional<ProConvocatoria> proConvocatoria = repository.findByCodigoUnicoConvocatoria(datosGuardados.getCodigoUnicoConvocatoria());
        if (proConvocatoria.isPresent() && !Objects.equals(proConvocatoria.get().getCodigo(), datosGuardados.getCodigo()))
            throw new DataException(REGISTRO_YA_EXISTE);
        return super.update(datosGuardados);
    }

    @Override
    public String getCodConvocatoriaCreacion() {
        return repository.findNextLastCodigo();
    }

    public Optional<ProConvocatoria> getByPeriodo(Integer id) {
        return repository.findByCodPeriodo(id);
    }

    @Transactional
    public ProConvocatoria updateEstadoConvocatoria(Integer id, String estado) throws DataException {
        ProConvocatoria byId = repository.findById(id)
                .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

        if (byId.getEstado().equals("FINALIZADO")) {
            ProConvocatoria convocatoria = repository.findFirstByEstadoNot("FINALIZADO");
            if (convocatoria != null) {
                throw new BusinessException("La convocatoria [" + convocatoria.getNombre() + "] está en estado [" + convocatoria.getEstado() + "] por lo que no se puede actualizar la convocatoria actual");
            }
        }

        if (estado.equals("INSCRIPCION")) {
            Optional<ProConvocatoria> notificacion = repository.findByEstado("INSCRIPCION");
            if (notificacion.isPresent()) {
                throw new DataException("Ya existe una convocatoria en estado INSCRIPCIÓN.");
            }
        }

        if (estado.equals("FINALIZADO")) {
            ProPeriodos periodo = proPeriodosRepository.findByConvocatoria(byId.getCodigo());
            inactivarPeriodo(periodo);
        }

        if (byId.getEstado().equals("FINALIZADO") && estado.equals("GRADUACION")) {
            ProPeriodos periodo = proPeriodosRepository.findByConvocatoria(byId.getCodigo());
            activarPeriodo(periodo);
        }

        ProConvocatoria proConvocatoria = byId;
        proConvocatoria.setEstado(estado);
        return repository.save(proConvocatoria);

    }

    private void inactivarPeriodo(ProPeriodos periodo) {
        periodo.setEstado("INACTIVO");
        proPeriodosRepository.save(periodo);
    }

    private void activarPeriodo(ProPeriodos periodo) {
        periodo.setEstado("ACTIVO");
        proPeriodosRepository.save(periodo);
    }

    @Override
    public String getEstado() {
        return repository.getEstado();
    }

    @Override
    @Async
    public void notificar(Integer codConvocatoria) {
        try {
            ProConvocatoria proConvocatoria = repository.findById(codConvocatoria)
                    .orElseThrow(() -> new BusinessException(REGISTRO_NO_EXISTE));

            List<ProConvocatoriaRequisitoDto> requisitos = convocatoriaRequisitoDatosRepository
                    .findByProConvocatoria(codConvocatoria);

            String[] destinatarios = proConvocatoria.getCorreo().split(",");
            System.out.println(destinatarios);

            String textoRequisitos = "";
            for (ProConvocatoriaRequisitoDto requisito : requisitos) {
                textoRequisitos += requisito.getNombreRequisito() + ": " + requisito.getDescripcionRequisito() + "<br>";
            }

            String mensajes = "";
            Parametro parametro1 = parametroRepository.findById(proConvocatoria.getCodigoParametro().longValue()).orElseThrow(() -> new BusinessException(""));
            mensajes += parametro1.getNombreParametro() + ": " + parametro1.getValor() + "<br>";
            Parametro parametro2 = parametroRepository.findById(proConvocatoria.getCodigoParametro2().longValue()).orElseThrow(() -> new BusinessException(""));
            mensajes += parametro2.getNombreParametro() + ": " + parametro2.getValor() + "<br>";

            emailService.sendConvocatoriaProEmail(destinatarios, EMAIL_SUBJECT_CONVOCATORIA_PRO, proConvocatoria.getNombre(),
                    formatDate(proConvocatoria.getFechaActual()), formatDate(proConvocatoria.getFechaInicio()),
                    formatDate(proConvocatoria.getFechaFin()), requisitos, mensajes, URL_INSCRIPCION);
        } catch (MessagingException | IOException e) {
            throw new BusinessException("Error al enviar correo");
        }
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

}
