package epntech.cbdmq.pe.servicio.impl;

import epntech.cbdmq.pe.dominio.admin.CatalogoCurso;
import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.dominio.admin.Reporte;
import epntech.cbdmq.pe.dominio.admin.ReporteParametro;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.especializacion.TipoCurso;
import epntech.cbdmq.pe.dominio.admin.formacion.NotaMateriaByEstudiante;
import epntech.cbdmq.pe.dominio.admin.llamamiento.Funcionario;
import epntech.cbdmq.pe.dominio.evaluaciones.PreguntaTipoEvaluacion;
import epntech.cbdmq.pe.dominio.evaluaciones.ReporteEvaluacion;
import epntech.cbdmq.pe.dominio.evaluaciones.RespuestaEstudiante;
import epntech.cbdmq.pe.dominio.util.AntiguedadesFormacion;
import epntech.cbdmq.pe.dominio.util.reportes.CursoDuracionDto;
import epntech.cbdmq.pe.dominio.util.reportes.PeriodoAcademicoDuracionDto;
import epntech.cbdmq.pe.dominio.util.reportes.ProPeriodosDuracionDto;
import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import epntech.cbdmq.pe.repositorio.ReporteParametroRepository;
import epntech.cbdmq.pe.repositorio.admin.ReporteRepository;
import epntech.cbdmq.pe.servicio.*;
import epntech.cbdmq.pe.servicio.especializacion.CursoService;
import epntech.cbdmq.pe.servicio.especializacion.InscripcionEspService;
import epntech.cbdmq.pe.servicio.especializacion.NotasEspecializacionService;
import epntech.cbdmq.pe.servicio.evaluaciones.PreguntaEvaluacionService;
import epntech.cbdmq.pe.servicio.evaluaciones.RespuestaEstudianteService;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProPeriodoService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepository;
    private final ReporteParametroRepository reporteParametroRepository;
    private final DataSource dataSource;

    private final AntiguedadesService service;
    private final NotasFormacionFinalService notasFormacionFinalService;
    private final MateriaService materiaService;
    private final CatalogoCursoService catalogoCursoService;
    private final PeriodoAcademicoService periodoAcademicoSvc;
    private final ProPeriodoService proPeriodoSvc;
    private final CursoService cursoService;
    private final InscripcionEspService inscripcionEspService;
    private final FuncionarioService funcionarioService;
    private final RespuestaEstudianteService respuestaEstudianteService;
    private final PreguntaEvaluacionService preguntaEvaluacionService;
    private final NotasFormacionService notasFormacionService;
    private final NotasEspecializacionService notasEspecializacionService;

    @Override
    public List<Reporte> getByModulo(String modulo) {
        return reporteRepository.findByModulo(modulo);
    }

    @Override
    public byte[] getReportePDF(Long codigo) {
        Reporte reporte = reporteRepository.findById(codigo).orElseThrow(() -> new BusinessException("No existe el reporte"));
        List<ReporteParametro> listaParametros = reporteParametroRepository.findByCodigoReporte(codigo);

        Map<String, Object> parametros = new HashMap<>();
        for (ReporteParametro parametro : listaParametros) {
            parametros.put(parametro.getNombre(), parametro.getValor());
        }

        parametros.put("id", codigo);
        return getReporte(parametros, reporte.getRuta(), reporte.getNombre());
    }

    private byte[] getReporte(Map<String, Object> parametros, String ruta, String nombre) {
        try (Connection con = dataSource.getConnection()) {
            JasperPrint print = JasperFillManager.fillReport(ruta, parametros, con);
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception ex) {
            log.error("Error al generar el reporte [" + nombre + "]: ", ex);
            throw new BusinessException("Error al generar el reporte [" + nombre + "]");
        }
    }

    public void exportAprobadosFormacion(String filename, String filetype, HttpServletResponse response) {
        InputStream sourceJrxmlFile = this.getClass().getResourceAsStream("/ReporteAprobadosReprobados.jrxml");
        List<AntiguedadesFormacion> lista = service.getAntiguedadesFormacion().stream().collect(Collectors.toList());
        AntiguedadesFormacion antiguedad = new AntiguedadesFormacion();
        //TODO no esta imprimiendo el primer valor, por eso agrego uno que no se va a mostrar
        antiguedad.setNombre("Total");
        antiguedad.setApellido("Hola");
        antiguedad.setCorreoPersonal("Jair");
        antiguedad.setNotaFinal(BigDecimal.valueOf(0.0f));
        antiguedad.setCedula("0");
        antiguedad.setCodigoUnicoEstudiante("0");
        InputStream imagen = this.getClass().getResourceAsStream("/logo-bomberos.png");
        try {
            List<AntiguedadesFormacion> aprobados = new ArrayList<>();
            aprobados.add(antiguedad);
            aprobados.addAll(lista);
            JRBeanCollectionDataSource dsAprobados = new JRBeanCollectionDataSource(aprobados);
            JasperReport jasperReport = JasperCompileManager.compileReport(sourceJrxmlFile);
            Map<String, Object> parameters = new HashMap<>();
            Integer numeroEstudiantes = notasFormacionFinalService.getNotasFinalCodPeriodoAcademico().size();
            Integer numeroAprobados = lista.size();
            Integer numeroReprobados = numeroEstudiantes - numeroAprobados;
            Float porcentajeAprobados = 0.0f;
            Float porcentajeReprobados = 0.0f;

            if (numeroEstudiantes != 0) {
                porcentajeAprobados = (float) (numeroAprobados * 100) / numeroEstudiantes;
                porcentajeReprobados = (float) (numeroReprobados * 100) / numeroEstudiantes;
            }
            parameters.put("listaAprobados", dsAprobados);
            parameters.put("numeroEstudiantes", numeroEstudiantes);
            parameters.put("numeroAprobados", numeroAprobados);
            parameters.put("numeroReprobados", numeroReprobados);
            parameters.put("porcentajeAprobados", porcentajeAprobados);
            parameters.put("porcentajeReprobados", porcentajeReprobados);
            parameters.put("academia", "Formación");
            parameters.put("imagen", imagen);
            imprimir(filename, filetype, response, dsAprobados, jasperReport, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportAprobadosEspecializacion(String filename, String filetype, HttpServletResponse response, Integer codCurso) {
        InputStream sourceJrxmlFile = this.getClass().getResourceAsStream("/ReporteAprobadosReprobados.jrxml");
        List<AntiguedadesFormacion> lista = service.getAntiguedadesEspecializacion(codCurso.longValue()).stream().collect(Collectors.toList());
        //TODO no esta imprimiendo el primer valor, por eso agrego uno que no se va a mostrar
        AntiguedadesFormacion aprobado = new AntiguedadesFormacion();
        aprobado.setNombre("Total");
        aprobado.setApellido("Hola");
        aprobado.setCorreoPersonal("Jair");
        InputStream imagen = this.getClass().getResourceAsStream("/logo-bomberos.png");
        try {
            List<AntiguedadesFormacion> aprobados = new ArrayList<>();
            aprobados.add(aprobado);
            aprobados.addAll(lista);

            JRBeanCollectionDataSource dsAprobados = new JRBeanCollectionDataSource(aprobados);
            JasperReport jasperReport = JasperCompileManager.compileReport(sourceJrxmlFile);
            Map<String, Object> parameters = new HashMap<>();
            Integer numeroEstudiantes = inscripcionEspService.getAprobadosPruebas(codCurso).size();
            Integer numeroAprobados = lista.size();
            Integer numeroReprobados = numeroEstudiantes - numeroAprobados;
            Float porcentajeAprobados = 0.0f;
            Float porcentajeReprobados = 0.0f;

            if (numeroEstudiantes != 0) {
                porcentajeAprobados = (float) (numeroAprobados * 100) / numeroEstudiantes;
                porcentajeReprobados = (float) (numeroReprobados * 100) / numeroEstudiantes;
            }
            parameters.put("listaAprobados", dsAprobados);
            parameters.put("numeroEstudiantes", numeroEstudiantes);
            parameters.put("numeroAprobados", numeroAprobados);
            parameters.put("numeroReprobados", numeroReprobados);
            parameters.put("porcentajeAprobados", porcentajeAprobados);
            parameters.put("porcentajeReprobados", porcentajeReprobados);
            parameters.put("academia", "Especialización");
            parameters.put("imagen", imagen);
            imprimir(filename, filetype, response, dsAprobados, jasperReport, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportMallaCurricular(String filename, String filetype, HttpServletResponse response) {
        InputStream sourceJrxmlFile = this.getClass().getResourceAsStream("/MallaCurricular.jrxml");
        List<Materia> materias = materiaService.getAllByPeriodoAcademicoActivo();
        List<CatalogoCurso> cursos = catalogoCursoService.getAll();
        List<Materia> materiaProfesionalizacion = materiaService.getAllByPeriodoProfesionalizacionActivo();
        InputStream imagen = this.getClass().getResourceAsStream("/logo-bomberos.png");
        try {
            JRBeanCollectionDataSource dsMaterias = new JRBeanCollectionDataSource(materias);
            JRBeanCollectionDataSource dsCursos = new JRBeanCollectionDataSource(cursos);
            JRBeanCollectionDataSource dsMateriasPro = new JRBeanCollectionDataSource(materiaProfesionalizacion);
            JasperReport jasperReport = JasperCompileManager.compileReport(sourceJrxmlFile);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("listaMateriasFormacion", dsMaterias);
            parameters.put("listaCursos", dsCursos);
            parameters.put("listaMateriasPro", dsMateriasPro);
            parameters.put("imagen", imagen);
            JREmptyDataSource noExisteFuentePrincipal = new JREmptyDataSource();

            imprimir(filename, filetype, response, noExisteFuentePrincipal, jasperReport, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exporPeriodosAcademicosPeriodosProfesionalesCursosByYear(String filename, String filetype, HttpServletResponse response, int inputYear) {
        LocalDate localStartDate = LocalDate.of(inputYear, 1, 1); // 01-01-2023
        LocalDate localEndDate = LocalDate.of(inputYear, 12, 31); // 31-12-2023
        Date startDate = Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(localEndDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        InputStream sourceJrxmlFile = this.getClass().getResourceAsStream("/ReporteGeneral.jrxml");
        List<PeriodoAcademicoDuracionDto> periodos = periodoAcademicoSvc.findByFechaInicioPeriodoAcadBetween(startDate, endDate).stream().map(periodo -> {
            PeriodoAcademicoDuracionDto dto = new PeriodoAcademicoDuracionDto();
            dto.setCodigo(periodo.getCodigo());
            dto.setDescripcion(periodo.getDescripcion());

            LocalDate fechaInicio = null;
            LocalDate fechaFin = null;
            int duracion = 0;

            if (periodo.getFechaInicio() != null) {
                fechaInicio = periodo.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }

            if (periodo.getFechaFin() != null) {
                fechaFin = periodo.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            } else {
                fechaFin = LocalDate.now();  // Si fechaFin es null, toma la fecha actual
            }

            if (fechaInicio != null) {
                duracion = (int) java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin);
            }

            dto.setDuracion(duracion);

            return dto;
        }).collect(Collectors.toList());
        List<ProPeriodosDuracionDto> periodosPro = proPeriodoSvc.findByFechaInicioBetween(startDate, endDate).stream().map(periodo -> {
            ProPeriodosDuracionDto dto = new ProPeriodosDuracionDto();
            dto.setCodigoPeriodo(periodo.getCodigoPeriodo());
            dto.setNombrePeriodo(periodo.getNombrePeriodo());

            // Convertir java.util.Date a java.time.LocalDate
            LocalDate fechaInicio = periodo.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaFin = periodo.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Calcular duración
            dto.setDuracion((int) java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin));

            return dto;
        }).collect(Collectors.toList());
        List<CursoDuracionDto> cursos = cursoService.findByFechaInicioBetween(localStartDate, localEndDate).stream().map(curso -> {
            CursoDuracionDto dto = new CursoDuracionDto();
            dto.setCodCursoEspecializacion(curso.getCodCursoEspecializacion());
            dto.setNombre(curso.getNombre());

            // Convertir java.util.Date a java.time.LocalDate
            dto.setDuracion((int) java.time.temporal.ChronoUnit.DAYS.between(curso.getFechaInicioCurso(), curso.getFechaFinCurso()));
            return dto;
        }).collect(Collectors.toList());
        InputStream imagen = this.getClass().getResourceAsStream("/logo-bomberos.png");
        try {
            JRBeanCollectionDataSource dsPeriodos = new JRBeanCollectionDataSource(periodos);
            JRBeanCollectionDataSource dsPeriodosPro = new JRBeanCollectionDataSource(periodosPro);
            JRBeanCollectionDataSource dsCursos = new JRBeanCollectionDataSource(cursos);
            JasperReport jasperReport = JasperCompileManager.compileReport(sourceJrxmlFile);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("listaPeriodosFormacion", dsPeriodos);
            parameters.put("listaPeriodosPro", dsPeriodosPro);
            parameters.put("listaCusos", dsCursos);
            parameters.put("numeroPeriodosFormacion", periodos.size());
            parameters.put("numeroPeriodosPro", periodosPro.size());
            parameters.put("numeroCursos", cursos.size());
            parameters.put("imagen", imagen);
            JREmptyDataSource noExisteFuentePrincipal = new JREmptyDataSource();

            imprimir(filename, filetype, response, noExisteFuentePrincipal, jasperReport, parameters);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportAntiguedadesOperativos(String filename, String filetype, HttpServletResponse response) throws Exception {
        InputStream sourceJrxmlFile = this.getClass().getResourceAsStream("/Antiguedades.jrxml");
        List<Funcionario> operativoApiDtoList = funcionarioService.servicioOperativosOrderByAntiguedad();
        InputStream imagen = this.getClass().getResourceAsStream("/logo-bomberos.png");
        try {
            JRBeanCollectionDataSource antiguedades = new JRBeanCollectionDataSource(operativoApiDtoList);
            JasperReport jasperReport = JasperCompileManager.compileReport(sourceJrxmlFile);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("listaAntiguedades", antiguedades);
            parameters.put("imagen", imagen);
            JREmptyDataSource noExisteFuentePrincipal = new JREmptyDataSource();
            imprimir(filename, filetype, response, noExisteFuentePrincipal, jasperReport, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exportReporteEncuestasESP(String filename, String filetype, HttpServletResponse response, Integer codCurso) throws Exception {
        InputStream sourceJrxmlFile = this.getClass().getResourceAsStream("/Encuestas.jrxml");
        List<PreguntaTipoEvaluacion> preguntas = preguntaEvaluacionService.getPreguntasToInstructor();
        List<ReporteEvaluacion> reporteEvaluaciones= new ArrayList<>();
        for(PreguntaTipoEvaluacion pregunta : preguntas){
            ReporteEvaluacion reporteEvaluacion = new ReporteEvaluacion();
            List<RespuestaEstudiante> respuestas = respuestaEstudianteService.findByPreguntaAndCursoAndRespuesta(pregunta.getCodPregunta(),codCurso.longValue(),true);
            List<RespuestaEstudiante> respuestasNo = respuestaEstudianteService.findByPreguntaAndCursoAndRespuesta(pregunta.getCodPregunta(),codCurso.longValue(),false);
            reporteEvaluacion.setPregunta(pregunta.getPregunta());
            reporteEvaluacion.setRespuestaSi(respuestas.size());
            reporteEvaluacion.setRespuestaNo(respuestasNo.size());
            reporteEvaluaciones.add(reporteEvaluacion);
        }
        Integer totalRespuestas = respuestaEstudianteService.findByPreguntaAndCurso(preguntas.get(0).getCodPregunta(),codCurso.longValue()).size();
        InputStream imagen = this.getClass().getResourceAsStream("/logo-bomberos.png");
        Curso cc = cursoService.getById(codCurso.longValue());
        CatalogoCurso catalogoCurso= catalogoCursoService.getById(cc.getCodCatalogoCursos().intValue()).get();
        Integer numeroEstudiantes = inscripcionEspService.getAprobadosPruebas(codCurso).size();
        try {
            JRBeanCollectionDataSource preguntasCollection = new JRBeanCollectionDataSource(reporteEvaluaciones);
            JasperReport jasperReport = JasperCompileManager.compileReport(sourceJrxmlFile);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("preguntas", preguntasCollection);
            parameters.put("academia", "Especialización");
            parameters.put("numeroRespuestas",totalRespuestas);
            parameters.put("curso", cc.getNombre() +"-"+catalogoCurso.getNombreCatalogoCurso());
            parameters.put("numeroEstudiantes", numeroEstudiantes);
            parameters.put("imagen", imagen);
            JREmptyDataSource noExisteFuentePrincipal = new JREmptyDataSource();
            imprimir(filename, filetype, response, noExisteFuentePrincipal, jasperReport, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void exportReporteFichaPersonal(String filename, String filetype, HttpServletResponse response,Integer codEstudianteFor, Integer codEstudianteEsp) throws Exception{
    InputStream sourceJrxmlFile = this.getClass().getResourceAsStream("/NotasEstudiante.jrxml");
    List<NotaMateriaByEstudiante> notasFormacion = notasFormacionService.getNotaMateriasWithCoordinadorByEstudiante(codEstudianteFor);
    List<NotaMateriaByEstudiante> notasEspecializacion = notasEspecializacionService.getHistoricosEstudiante(codEstudianteEsp);
    InputStream imagen = this.getClass().getResourceAsStream("/logo-bomberos.png");
        try {
        JRBeanCollectionDataSource notasFormacionValid = new JRBeanCollectionDataSource(notasFormacion);
        JRBeanCollectionDataSource notasEspecializacionValid = new JRBeanCollectionDataSource(notasEspecializacion);
        JasperReport jasperReport = JasperCompileManager.compileReport(sourceJrxmlFile);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("notasFormacion", notasFormacionValid);
        parameters.put("notasEspecializacion", notasEspecializacionValid);
        parameters.put("imagen", imagen);
        JREmptyDataSource noExisteFuentePrincipal = new JREmptyDataSource();
        imprimir(filename, filetype, response, noExisteFuentePrincipal, jasperReport, parameters);
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    //TODO en profesionalizacion no tenemos aprobados
    public void exportAprobadosProfesionalizacion(String filename, String filetype, HttpServletResponse response) {
        InputStream sourceJrxmlFile = this.getClass().getResourceAsStream("/ReporteAprobadosReprobados.jrxml");
        JasperPrint jasperPrint;
        ServletOutputStream outputStream;
        List<AntiguedadesFormacion> lista = service.getAntiguedadesFormacion().stream().collect(Collectors.toList());
        AntiguedadesFormacion observacionDto1 = new AntiguedadesFormacion();
        observacionDto1.setNombre("Total");
        observacionDto1.setApellido("Hola");
        observacionDto1.setCorreoPersonal("Jair");
        InputStream imagen = this.getClass().getResourceAsStream("/logo-bomberos.png");
        try {
            List<AntiguedadesFormacion> aprobados = new ArrayList<>();
            aprobados.add(observacionDto1);
            aprobados.addAll(lista);

            JRBeanCollectionDataSource dsSubObservaciones = new JRBeanCollectionDataSource(lista);
            JasperReport jasperReport = JasperCompileManager.compileReport(sourceJrxmlFile);
            Map<String, Object> parameters = new HashMap<>();
            Integer numeroEstudiantes = notasFormacionFinalService.getNotasFinalCodPeriodoAcademico().size();
            Integer numeroAprobados = lista.size();
            Integer numeroReprobados = numeroEstudiantes - numeroAprobados;
            Float porcentajeAprobados = 0.0f;
            Float porcentajeReprobados = 0.0f;

            if (numeroEstudiantes != 0) {
                porcentajeAprobados = (float) (numeroAprobados * 100) / numeroEstudiantes;
                porcentajeReprobados = (float) (numeroReprobados * 100) / numeroEstudiantes;
            }


            parameters.put("listaAprobados", dsSubObservaciones);
            parameters.put("numeroEstudiantes", numeroEstudiantes);
            parameters.put("numeroAprobados", numeroAprobados);
            parameters.put("numeroReprobados", numeroReprobados);
            parameters.put("porcentajeAprobados", porcentajeAprobados);
            parameters.put("porcentajeReprobados", porcentajeReprobados);
            parameters.put("imagen", imagen);
            imprimir(filename, filetype, response, dsSubObservaciones, jasperReport, parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void imprimir(String filename, String filetype, HttpServletResponse response, JRDataSource fuenteDatosPrincipal, JasperReport jasperReport, Map<String, Object> parameters) throws JRException, IOException {
        JasperPrint jasperPrint;
        ServletOutputStream outputStream;
        jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, fuenteDatosPrincipal);
        switch (filetype.toUpperCase()) {
            case "PDF":
                response.addHeader("Content-Disposition", "inline; filename=" + filename + ".pdf;");
                response.setContentType("application/octet-stream");
                outputStream = response.getOutputStream();
                JRPdfExporter pdfExporter = new JRPdfExporter();
                pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                pdfExporter.exportReport();
                break;

            case "EXCEL":
                response.addHeader("Content-Disposition", "inline; filename=" + filename + ".xlsx;");
                response.setContentType("application/octet-stream");
                outputStream = response.getOutputStream();
                JRXlsxExporter xlsxExporter = new JRXlsxExporter();
                xlsxExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                xlsxExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                xlsxExporter.exportReport();
                break;
            default:
                throw new IllegalArgumentException("Formato de archivo no soportado: " + filetype);
        }
    }

}
