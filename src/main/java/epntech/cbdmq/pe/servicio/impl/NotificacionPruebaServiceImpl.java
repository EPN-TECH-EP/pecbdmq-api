package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import epntech.cbdmq.pe.repositorio.admin.*;
import epntech.cbdmq.pe.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import epntech.cbdmq.pe.dominio.admin.NotificacionPrueba;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.mail.MessagingException;

@Service
public class NotificacionPruebaServiceImpl implements NotificacionPruebaService {

    @Autowired
    NotificacionPruebaRepository repo;

    @Autowired
    private EmailService emailService;

    @Autowired
    DatoPersonalService dpSvc;
    @Autowired
    ResultadoPruebasDatosRepository repo1;

    @Autowired
    PruebaDetalleService pruebaDetalleService;
    @Autowired
    PeriodoAcademicoService periodoAcademicoService;
    @Autowired
    PostulanteService postulanteService;


    @Override
    public NotificacionPrueba save(NotificacionPrueba obj) throws DataException, MessagingException {
        if (obj.getFechaPrueba() == null || obj.getMensaje().isEmpty())
            throw new DataException(REGISTRO_VACIO);

        emailService.notificacionEmail(obj.getFechaPrueba(), obj.getMensaje(), dpSvc.getDatosPersonalesById(obj.getCodDatosPersonales()).get().getCorreoPersonal());
        return repo.save(obj);
    }

    @Override
    public List<NotificacionPrueba> getAll() {
        return repo.findAll();
    }

    @Override
    public void enviarNotificacion(Integer subTipoPrueba) throws MessagingException, DataException, ParseException {
        Optional<PruebaDetalle> pruebaDetalleOpt = pruebaDetalleService.getBySubtipoAndPA(subTipoPrueba, periodoAcademicoService.getPAActivo());
        if (pruebaDetalleOpt.isEmpty()) {
            throw new DataException("No existe el subtipo de prueba");
        }

        PruebaDetalle pruebaDetalle = pruebaDetalleOpt.get();
        LocalDateTime fechaActual = LocalDateTime.now();
        List<ResultadosPruebasDatos> aprobados = repo1.get_approved_applicants(subTipoPrueba);

        StringBuilder errorMessageBuilder = new StringBuilder();

        for (ResultadosPruebasDatos resultadosPruebasDatos : aprobados) {
            DatoPersonal dato = postulanteService.getById(resultadosPruebasDatos.getCodPostulante().longValue())
                    .map(postulante -> dpSvc.getDatosPersonalesById(postulante.getCodDatoPersonal()).orElse(null))
                    .orElse(null);

            if (dato == null) {
                throw new DataException("No existe un dato personal asociado al postulante");
            }

            NotificacionPrueba noti = new NotificacionPrueba();
            noti.setCodDatosPersonales(dato.getCodDatosPersonales());
            noti.setCodPrueba(pruebaDetalle.getCodPruebaDetalle());
            noti.setFechaPrueba(fechaActual);
            noti.setEstado("ACTIVO");

            try {
                String mensaje = emailService.notificacionAprobadoEmail(pruebaDetalle.getDescripcionPrueba(), dato.getCorreoPersonal());
                noti.setMensaje("mensaje");
                noti.setNotificacionEnviada(true);
                repo.save(noti);
            } catch (Exception e) {
                String errorMessage = e.getMessage();
                errorMessageBuilder.append(errorMessage).append("\n");
                noti.setMensaje(errorMessage);
                noti.setNotificacionEnviada(false);
                repo.save(noti);
            }
        }

        // Enviar el mensaje de error una vez, si es que hay algún error.
        if (errorMessageBuilder.length() > 0) {
            throw new DataException(errorMessageBuilder.toString());
        }
    }

}