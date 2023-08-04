package epntech.cbdmq.pe.repositorio.admin;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_CONVOCATORIA;
import static epntech.cbdmq.pe.constante.ArchivoConst.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.ConvocatoriaDocumentoFor;
import epntech.cbdmq.pe.dominio.admin.ConvocatoriaFor;
import epntech.cbdmq.pe.dominio.admin.DocumentoFor;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoDocumentoFor;
import epntech.cbdmq.pe.dominio.admin.RequisitoFor;
import epntech.cbdmq.pe.dominio.util.DatosFile;
import epntech.cbdmq.pe.dominio.util.PeriodoAcademicoFor;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.EmailService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;

@Repository
@Transactional
public class ConvocatoriaForRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EmailService emailService;

    @Value("${pecb.archivos.ruta}")
    private String ARCHIVOS_RUTA;

    @Value("${spring.servlet.multipart.max-file-size}")
    public DataSize TAMAÑO_MÁXIMO;
    //cambiar
    @Value("${server.port}")
    public String SERVER_PORT;

    @Value("${hostname}")
    public String HOSTNAME;
    @Value("${url.descarga.archivos}")
    public String URLDESCARGA;


    public PeriodoAcademicoFor insertarConvocatoriaConDocumentos(ConvocatoriaFor convocatoria,
                                                                 Set<RequisitoFor> requisitos, List<MultipartFile> docsPeriodoAcademico, List<MultipartFile> docsConvocatoria)
            throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, DataException {
        String sqlConvocatoria = "INSERT INTO cbdmq.gen_convocatoria (cod_periodo_academico, nombre_convocatoria, estado, fecha_inicio_convocatoria, fecha_fin_convocatoria, fecha_actual,hora_inicio_convocatoria, hora_fin_convocatoria, codigo_unico_convocatoria, cupo_hombres, cupo_mujeres, correo) "
                + "VALUES (:periodo, :nombre, :estado, :fechaInicio, :fechaFin,:fechaActual,:horaInicio, :horaFin, :codigoUnico, :cupoHombres, :cupoMujeres, :correo)";
        String sqlDocumento = "INSERT INTO cbdmq.gen_documento (autorizacion, cod_tipo_documento, descripcion, estado_validacion, nombre_documento, observaciones, ruta, estado) "
                + "VALUES (:autorizacion, :tipo, :descripcion, :estadoValidacion, :nombre, :observaciones, :ruta, :estado)";
        String sqlConvocatoriaDocumento = "INSERT INTO cbdmq.gen_convocatoria_documento (cod_convocatoria, cod_documento) "
                + "VALUES (:cod_convocatoria, :cod_documento)";
        String sqlPeriodoAcademicoDocumento = "INSERT INTO cbdmq.gen_periodo_academico_documento (cod_periodo_academico, cod_documento) "
                + "VALUES (:cod_periodo_academico, :cod_documento)";
        String sqlConvocatoriaRequisito = "INSERT INTO cbdmq.gen_convocatoria_requisito (cod_convocatoria, cod_requisito) "
                + "VALUES (:cod_convocatoria, :cod_requisito)";

        /*
         * String sqlRequisito =
         * "INSERT INTO cbdmq.gen_requisito(cod_convocatoria, cod_funcionario, descripcion_requisito, estado, nombre_requisito, es_documento) "
         * +
         * "VALUES (:cod_convocatoria, :funcionario, :descripcion, :estado, :nombre, :esDocumento)"
         * ;
         */
        /*
         * String sqlRequisitoDocumento =
         * "INSERT INTO cbdmq.gen_requisito_documento (cod_requisito, cod_documento) " +
         * "VALUES (:codRequisito, :codDocumento)";
         */

        // PERIODO ACADEMICO
        String sqlQuery = "INSERT INTO cbdmq.gen_periodo_academico(estado, descripcion) "
                + "VALUES (:estado, :descripcion)";

        PeriodoAcademico periodo = new PeriodoAcademico();
        periodo.setEstado("ACTIVO");
        periodo.setDescripcion("FORMACION");

        entityManager.createNativeQuery(sqlQuery).setParameter("estado", periodo.getEstado())
                .setParameter("descripcion", periodo.getDescripcion());

        entityManager.persist(periodo);

        // CONVOCATORIA
        // convocatoria.setDocumentos(docsConvocatoria);

        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("cbdmq.get_id");
        query.registerStoredProcedureParameter("proceso", String.class, ParameterMode.IN);
        query.setParameter("proceso", "C");
        query.execute();
        Object resultado = query.getSingleResult();
        convocatoria.setCodigoUnico(resultado.toString());

        convocatoria.setCodPeriodoAcademico(periodo.getCodigo());

        entityManager.createNativeQuery(sqlConvocatoria).setParameter("periodo", convocatoria.getCodPeriodoAcademico())
                .setParameter("nombre", convocatoria.getNombre()).setParameter("estado", convocatoria.getEstado())
                .setParameter("fechaInicio", convocatoria.getFechaInicioConvocatoria())
                .setParameter("fechaFin", convocatoria.getFechaFinConvocatoria())
                .setParameter("fechaActual", convocatoria.getFechaActual())
                .setParameter("horaInicio", convocatoria.getHoraInicioConvocatoria())
                .setParameter("horaFin", convocatoria.getHoraFinConvocatoria())
                .setParameter("codigoUnico", convocatoria.getCodigoUnico())
                .setParameter("cupoHombres", convocatoria.getCupoHombres())
                .setParameter("cupoMujeres", convocatoria.getCupoMujeres())
                .setParameter("correo", convocatoria.getCorreo());

        entityManager.persist(convocatoria);

        // ARCHIVOS CONVOCATORIA
        // guardamos archivos de la convocatoria en el servidor

        List<DatosFile> archivosConvocatoria = new ArrayList<>();
            try {
                archivosConvocatoria = guardarArchivo(docsConvocatoria, PATH_PROCESO_CONVOCATORIA, convocatoria.getCodConvocatoria().toString());
            } catch (Exception e) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("errorHeader", e.getMessage());
            }
        Set<DocumentoFor> dConvocatoria = new HashSet<>();

        for (DatosFile datosFile : archivosConvocatoria) {
            DocumentoFor dd = new DocumentoFor();
            dd.setEstado("ACTIVO");
            dd.setNombre(datosFile.getNombre());
            dd.setRuta(datosFile.getRuta());

            dConvocatoria.add(dd);
        }

        //
        Set<DocumentoFor> documentos = new HashSet<>();
        // convocatoria.setDocumentos(dConvocatoria);
        for (DocumentoFor documento : dConvocatoria) {
            DocumentoFor documentoFor = new DocumentoFor();
            entityManager.createNativeQuery(sqlDocumento).setParameter("autorizacion", documento.getAutorizacion())
                    .setParameter("tipo", documento.getTipo()).setParameter("descripcion", documento.getDescripcion())
                    .setParameter("estadoValidacion", documento.getEstadoValidacion())
                    //.setParameter("codigoUnico", documento.getCodigoUnico())
                    .setParameter("nombre", documento.getNombre())
                    .setParameter("observaciones", documento.getObservaciones())
                    .setParameter("ruta", documento.getRuta()).setParameter("estado", documento.getEstado());
            entityManager.persist(documento);
            documentoFor = documento;
            documentos.add(documentoFor);
        }
        for (DocumentoFor elemento : documentos) {
            PeriodoAcademicoDocumentoFor periodoAcademicoDocumentoFor = new PeriodoAcademicoDocumentoFor();

            entityManager.createNativeQuery(sqlPeriodoAcademicoDocumento)
                    .setParameter("cod_periodo_academico", periodo.getCodigo())
                    .setParameter("cod_documento", elemento.getCodDocumento());

            periodoAcademicoDocumentoFor.setCodPeriodoAcademico(periodo.getCodigo());
            periodoAcademicoDocumentoFor.setCodDocumento(elemento.getCodDocumento());
            entityManager.persist(periodoAcademicoDocumentoFor);
        }


        Integer codConvocatoria = convocatoria.getCodConvocatoria();
        Integer codigoDocumento = 0;

        for (DocumentoFor elemento : documentos) {
            ConvocatoriaDocumentoFor convocatoriaDocumentoFor = new ConvocatoriaDocumentoFor();

            entityManager.createNativeQuery(sqlConvocatoriaDocumento).setParameter("cod_convocatoria", codConvocatoria)
                    .setParameter("cod_documento", elemento.getCodDocumento());

            convocatoriaDocumentoFor.setCodConvocatoria(codConvocatoria);
            convocatoriaDocumentoFor.setCodDocumento(elemento.getCodDocumento());
            entityManager.persist(convocatoriaDocumentoFor);
            codigoDocumento = elemento.getCodDocumento();
        }


        // ARCHIVOS PERIODO ACADEMICO
        // guardamos archivos del Periodo Academico en el servidor

        List<DatosFile> archivosPA = new ArrayList<>();
        if (docsPeriodoAcademico != null) {
            try {
                archivosPA = guardarArchivo(docsPeriodoAcademico, PATH_PROCESO_PERIODO_ACADEMICO, periodo.getCodigo().toString());
            } catch (Exception e) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("errorHeader", e.getMessage());
            }

            Set<DocumentoFor> dPeriodoAcademico = new HashSet<>();

            for (DatosFile datosFile : archivosPA) {
                DocumentoFor dd1 = new DocumentoFor();

                dd1.setEstado("ACTIVO");
                dd1.setNombre(datosFile.getNombre());
                dd1.setRuta(datosFile.getRuta());
                dPeriodoAcademico.add(dd1);
            }

            Set<DocumentoFor> docPA = new HashSet<>();
            for (DocumentoFor documento : dPeriodoAcademico) {

                DocumentoFor documentoFor = new DocumentoFor();

                entityManager.createNativeQuery(sqlDocumento).setParameter("autorizacion", documento.getAutorizacion())
                        .setParameter("tipo", documento.getTipo()).setParameter("descripcion", documento.getDescripcion())
                        .setParameter("estadoValidacion", documento.getEstadoValidacion())
                        //.setParameter("codigoUnico", documento.getCodigoUnico())
                        .setParameter("nombre", documento.getNombre())
                        .setParameter("observaciones", documento.getObservaciones())
                        .setParameter("ruta", documento.getRuta()).setParameter("estado", documento.getEstado());
                entityManager.persist(documento);
                documentoFor = documento;
                docPA.add(documentoFor);
            }

            entityManager.persist(periodo);

            for (DocumentoFor elemento : docPA) {
                PeriodoAcademicoDocumentoFor periodoAcademicoDocumentoFor = new PeriodoAcademicoDocumentoFor();

                entityManager.createNativeQuery(sqlPeriodoAcademicoDocumento)
                        .setParameter("cod_periodo_academico", periodo.getCodigo())
                        .setParameter("cod_documento", elemento.getCodDocumento());

                periodoAcademicoDocumentoFor.setCodPeriodoAcademico(periodo.getCodigo());
                periodoAcademicoDocumentoFor.setCodDocumento(elemento.getCodDocumento());
                entityManager.persist(periodoAcademicoDocumentoFor);
            }
        }

        // REQUISITOS

        for (RequisitoFor elemento : requisitos) {
            entityManager.createNativeQuery(sqlConvocatoriaRequisito).setParameter("cod_convocatoria", codConvocatoria)
                    .setParameter("cod_requisito", elemento.getCodigoRequisito());
        }


        /*
         * for (Requisito requisito : convocatoria.getRequisitos()) {
         *
         * entityManager.createNativeQuery(sqlRequisito)
         * .setParameter("cod_convocatoria", codConvocatoria)
         * .setParameter("funcionario", requisito.getCodFuncionario())
         * .setParameter("descripcion", requisito.getDescripcion())
         * .setParameter("nombre", requisito.getNombre()) .setParameter("estado",
         * requisito.getEstado()) .setParameter("esDocumento",
         * requisito.getEsDocumento()); entityManager.persist(requisito); }
         */

        /*
         * Iterator<Requisito> req = requisitos.iterator(); while (req.hasNext()) {
         * Requisito elemento = req.next(); for (DocumentoRequisitoFor documento :
         * elemento.getDocumentosRequisito()) {
         *
         * entityManager.createNativeQuery(sqlDocumento) .setParameter("autorizacion",
         * documento.getAutorizacion()) .setParameter("tipo", documento.getTipo())
         * .setParameter("descripcion", documento.getDescripcion())
         * .setParameter("estadoValidacion", documento.getEstadoValidacion())
         * .setParameter("codigoUnico", documento.getCodigoUnico())
         * .setParameter("nombre", documento.getNombre()) .setParameter("observaciones",
         * documento.getObservaciones()) .setParameter("ruta",
         * documento.getRuta()).setParameter("estado", documento.getEstado());
         * entityManager.persist(documento); }
         * entityManager.createNativeQuery(sqlRequisitoDocumento)
         * .setParameter("codRequisito", elemento.getCodigoRequisito())
         * .setParameter("codDocumento", elemento.getDocumentosRequisito()); }
         */

        String link = URLDESCARGA + "/link/" + codigoDocumento;

        String mensaje = "Se adjunta link de convocatoria \n \n" + "link: http://" + link + " \n \n Plataforma educativa - CBDMQ";

        emailService.enviarEmail(convocatoria.getCorreo(), EMAIL_SUBJECT_CONVOCATORIA, mensaje);

        PeriodoAcademicoFor pa = new PeriodoAcademicoFor();

        pa.setConvocatoria(codConvocatoria);
        pa.setPeriodoAcademico(periodo.getCodigo());

        return pa;
    }

    public List<DatosFile> guardarArchivo(List<MultipartFile> archivo, String proceso, String id)
            throws IOException, ArchivoMuyGrandeExcepcion {
        String resultado;

        resultado = ruta(proceso, id);
        Path ruta = Paths.get(resultado).toAbsolutePath().normalize();

        if (!Files.exists(ruta)) {
            Files.createDirectories(ruta);
        }

        List<DatosFile> lista = new ArrayList<>();

        for (Iterator iterator = archivo.iterator(); iterator.hasNext(); ) {
            DatosFile documentos = new DatosFile();
            MultipartFile multipartFile = (MultipartFile) iterator.next();
            if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
                throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
            }

            Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            // LOGGER.info("Archivo guardado: " + resultado +

            documentos.setRuta(resultado + multipartFile.getOriginalFilename());
            documentos.setNombre(multipartFile.getOriginalFilename());
            lista.add(documentos);
        }

        return lista;
    }

    private String ruta(String proceso, String id) {

        String resultado = null;
        resultado = ARCHIVOS_RUTA + proceso + id + "/";
        return resultado;
    }

}
