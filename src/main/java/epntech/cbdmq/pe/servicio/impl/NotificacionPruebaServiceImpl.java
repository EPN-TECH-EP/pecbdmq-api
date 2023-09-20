package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.EstadosConst.ACTIVO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.PruebaDetalle;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import epntech.cbdmq.pe.repositorio.admin.*;
import epntech.cbdmq.pe.repositorio.admin.especializacion.PruebasRepository;
import epntech.cbdmq.pe.repositorio.admin.formacion.ResultadoPruebasTodoRepository;
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
    ResultadoPruebasTodoRepository repo1;

    @Autowired
    PruebaDetalleService pruebaDetalleService;
    @Autowired
    PeriodoAcademicoService periodoAcademicoService;
    @Autowired
    PostulanteService postulanteService;

    // cursos
    @Autowired
    private PruebasRepository pruebasRepository;
    @Autowired
    private EstudianteService estudianteService;


    @Override
    public NotificacionPrueba save(NotificacionPrueba obj) throws DataException, MessagingException {
        if (obj.getFechaPrueba() == null || obj.getMensaje().isEmpty())
            throw new DataException(REGISTRO_VACIO);

        return repo.save(obj);
    }

    @Override
    public List<NotificacionPrueba> getAll() {
        return repo.findAll();
    }

    @Override
    public void enviarNotificacion(Integer subTipoPrueba, Integer codCurso, Boolean esUltimo) throws MessagingException, DataException, ParseException {

        Optional<PruebaDetalle> pruebaDetalleOpt = Optional.empty();

        // funcionalidad para curso
        if (codCurso != null) {
            pruebaDetalleOpt = pruebaDetalleService.findByCodCursoEspecializacionAndCodSubtipoPrueba(codCurso, subTipoPrueba);
            if (pruebaDetalleOpt.isEmpty()) {
                throw new DataException("No existe el subtipo de prueba");
            }
        } else {
            pruebaDetalleOpt = pruebaDetalleService.getBySubtipoAndPA(subTipoPrueba, periodoAcademicoService.getPAActivo());
            if (pruebaDetalleOpt.isEmpty()) {
                throw new DataException("No existe el subtipo de prueba");
            }
        }

        PruebaDetalle pruebaDetalle = pruebaDetalleOpt.get();
        LocalDateTime fechaActual = LocalDateTime.now();


        List<ResultadosPruebasDatos> aprobados;
        List<ResultadosPruebasDatos> reprobados;
        if (codCurso != null) {
            // llama a procedimiento cbdmq.get_approved_by_test_esp(p_sub_tipo_prueba bigint, p_cod_curso bigint)
            aprobados = pruebasRepository.get_approved_by_test_esp(subTipoPrueba.longValue(), codCurso.longValue());
            //TODO poner para que devuelva los reprobados
            reprobados=null;
        } else {
            aprobados = repo1.get_approved_applicants(subTipoPrueba);
            reprobados= repo1.get_desapproved_applicants(subTipoPrueba);
        }

        StringBuilder errorMessageBuilder = new StringBuilder();

        for (ResultadosPruebasDatos resultadosPruebasDatos : aprobados) {
            DatoPersonal dato;

            // si es curso, obtiene dato personal asociado a estudiante
            if (codCurso != null) {
                dato = estudianteService.getByIdEstudiante(resultadosPruebasDatos.getIdPostulante())
                        .map(estudiante -> dpSvc.getDatosPersonalesById(estudiante.getCodDatosPersonales()).orElse(null))
                        .orElse(null);
            } else {

                dato = postulanteService.getById(resultadosPruebasDatos.getCodPostulante().longValue())
                        .map(postulante -> dpSvc.getDatosPersonalesById(postulante.getCodDatoPersonal()).orElse(null))
                        .orElse(null);
            }

            if (dato == null) {
                throw new DataException("No existe un dato personal asociado");
            }

            NotificacionPrueba noti = new NotificacionPrueba();
            noti.setCodDatosPersonales(dato.getCodDatosPersonales());
            noti.setCodPrueba(pruebaDetalle.getCodPruebaDetalle());
            noti.setFechaPrueba(fechaActual);
            noti.setEstado(ACTIVO);

            try {
                String mensaje;
                if (esUltimo) {
                    mensaje = emailService.notificacionAprobadoPruebaFinalSendEmail(pruebaDetalle.getDescripcionPrueba(), dato);
                } else {
                    mensaje = emailService.notificacionAprobadoPruebaSendEmail(pruebaDetalle.getDescripcionPrueba(), dato);
                }
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
        if(reprobados!=null) {
        for (ResultadosPruebasDatos resultadosPruebasDatos : reprobados) {
            DatoPersonal dato;

                // si es curso, obtiene dato personal asociado a estudiante
                if (codCurso != null) {
                    dato = estudianteService.getByIdEstudiante(resultadosPruebasDatos.getIdPostulante())
                            .map(estudiante -> dpSvc.getDatosPersonalesById(estudiante.getCodDatosPersonales()).orElse(null))
                            .orElse(null);
                } else {

                    dato = postulanteService.getById(resultadosPruebasDatos.getCodPostulante().longValue())
                            .map(postulante -> dpSvc.getDatosPersonalesById(postulante.getCodDatoPersonal()).orElse(null))
                            .orElse(null);
                }

                if (dato == null) {
                    throw new DataException("No existe un dato personal asociado");
                }

                NotificacionPrueba noti = new NotificacionPrueba();
                noti.setCodDatosPersonales(dato.getCodDatosPersonales());
                noti.setCodPrueba(pruebaDetalle.getCodPruebaDetalle());
                noti.setFechaPrueba(fechaActual);
                noti.setEstado(ACTIVO);

            try {
                    String mensaje;
                    if (esUltimo) {
                        mensaje = emailService.notificacionNoAprobadoPruebaFinalSendEmail(pruebaDetalle.getDescripcionPrueba(), dato);
                    } else {
                        mensaje = emailService.notificacionNoAprobadosPruebaSendEmail(pruebaDetalle.getDescripcionPrueba(), dato);
                    }
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
        }
        // Enviar el mensaje de error una vez, si es que hay algún error.
        if (!errorMessageBuilder.isEmpty()) {
            throw new DataException(errorMessageBuilder.toString());
        }
    }

}