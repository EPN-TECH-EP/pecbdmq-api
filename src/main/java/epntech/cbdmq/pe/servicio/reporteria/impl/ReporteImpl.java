package epntech.cbdmq.pe.servicio.reporteria.impl;

import epntech.cbdmq.pe.dominio.admin.CatalogoCurso;
import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.profesionalizacion.ProPeriodos;
import epntech.cbdmq.pe.dominio.util.AntiguedadesFormacion;
import epntech.cbdmq.pe.dominio.util.reportes.CursoDuracionDto;
import epntech.cbdmq.pe.dominio.util.reportes.PeriodoAcademicoDuracionDto;
import epntech.cbdmq.pe.dominio.util.reportes.ProPeriodosDuracionDto;
import epntech.cbdmq.pe.servicio.*;
import epntech.cbdmq.pe.servicio.especializacion.CursoService;
import epntech.cbdmq.pe.servicio.especializacion.InscripcionEspService;
import epntech.cbdmq.pe.servicio.profesionalizacion.ProPeriodoService;
import epntech.cbdmq.pe.servicio.reporteria.ReporteService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReporteImpl implements ReporteService {
    @Autowired
    private AntiguedadesService service;
    @Autowired
    private NotasFormacionFinalService notasFormacionFinalService;
    @Autowired
    private MateriaService materiaService;
    @Autowired
    private CatalogoCursoService catalogoCursoService;
    @Autowired
    private PeriodoAcademicoService periodoAcademicoSvc;
    @Autowired
    private ProPeriodoService proPeriodoSvc;
    @Autowired
    private CursoService cursoService;
    @Autowired
    private InscripcionEspService inscripcionEspService;

    public void exportAprobadosFormacion(String filename, String filetype, HttpServletResponse response) {
        InputStream sourceJrxmlFile = this.getClass().getResourceAsStream("/ReporteAprobadosReprobados.jrxml");
        JasperPrint jasperPrint;
        ServletOutputStream outputStream;
        List<AntiguedadesFormacion> lista = service.getAntiguedadesFormacion().stream().collect(Collectors.toList());
        AntiguedadesFormacion observacionDto1 = new AntiguedadesFormacion();
        observacionDto1.setNombre("Total");
        observacionDto1.setApellido("Hola");
        observacionDto1.setCorreoPersonal("Jair");
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
            parameters.put("academia", "Formacion");
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dsSubObservaciones);
            if (filetype.equalsIgnoreCase("PDF")) {
                response.addHeader("Content-Disposition", "inline; filename=" + filename + ".pdf;");
                response.setContentType("application/octet-stream");
                outputStream = response.getOutputStream();
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                exporter.exportReport();
            }
            if (filetype.equalsIgnoreCase("EXCEL")) {
                response.addHeader("Content-Disposition", "inline; filename=" + filename + ".xlsx;");
                response.setContentType("application/octet-stream");
                outputStream = response.getOutputStream();
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                exporter.exportReport();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportAprobadosEspecializacion(String filename, String filetype, HttpServletResponse response, Integer codCurso) {
        InputStream sourceJrxmlFile = this.getClass().getResourceAsStream("/ReporteAprobadosReprobados.jrxml");
        JasperPrint jasperPrint;
        ServletOutputStream outputStream;
        List<AntiguedadesFormacion> lista = service.getAntiguedadesEspecializacion(codCurso.longValue()).stream().collect(Collectors.toList());
        AntiguedadesFormacion observacionDto1 = new AntiguedadesFormacion();
        observacionDto1.setNombre("Total");
        observacionDto1.setApellido("Hola");
        observacionDto1.setCorreoPersonal("Jair");
        try {
            List<AntiguedadesFormacion> aprobados = new ArrayList<>();
            aprobados.add(observacionDto1);
            aprobados.addAll(lista);

            JRBeanCollectionDataSource dsSubObservaciones = new JRBeanCollectionDataSource(lista);
            JasperReport jasperReport = JasperCompileManager.compileReport(sourceJrxmlFile);
            Map<String, Object> parameters = new HashMap<>();
            Integer numeroEstudiantes = inscripcionEspService.getByCurso(codCurso.longValue()).size();
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
            parameters.put("academia", "Especializacion");
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dsSubObservaciones);
            if (filetype.equalsIgnoreCase("PDF")) {
                response.addHeader("Content-Disposition", "inline; filename=" + filename + ".pdf;");
                response.setContentType("application/octet-stream");
                outputStream = response.getOutputStream();
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                exporter.exportReport();
            }
            if (filetype.equalsIgnoreCase("EXCEL")) {
                response.addHeader("Content-Disposition", "inline; filename=" + filename + ".xlsx;");
                response.setContentType("application/octet-stream");
                outputStream = response.getOutputStream();
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                exporter.exportReport();
            }
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
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dsSubObservaciones);
            if (filetype.equalsIgnoreCase("PDF")) {
                response.addHeader("Content-Disposition", "inline; filename=" + filename + ".pdf;");
                response.setContentType("application/octet-stream");
                outputStream = response.getOutputStream();
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                exporter.exportReport();
            }
            if (filetype.equalsIgnoreCase("EXCEL")) {
                response.addHeader("Content-Disposition", "inline; filename=" + filename + ".xlsx;");
                response.setContentType("application/octet-stream");
                outputStream = response.getOutputStream();
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                exporter.exportReport();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportMallaCurricular(String filename, String filetype, HttpServletResponse response) {
        InputStream sourceJrxmlFile = this.getClass().getResourceAsStream("/MallaCurricular.jrxml");
        JasperPrint jasperPrint;
        ServletOutputStream outputStream;
        List<Materia> materias = materiaService.getAllByPeriodoAcademicoActivo();
        List<CatalogoCurso> cursos = catalogoCursoService.getAll();
        List<Materia> materiaProfesionalizacion = materiaService.getAllByPeriodoProfesionalizacionActivo();
        try {


            JasperReport jasperReport = JasperCompileManager.compileReport(sourceJrxmlFile);
            Map<String, Object> parameters = new HashMap<>();
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            if (filetype.equalsIgnoreCase("PDF")) {
                response.addHeader("Content-Disposition", "inline; filename=" + filename + ".pdf;");
                response.setContentType("application/octet-stream");
                outputStream = response.getOutputStream();
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                exporter.exportReport();
            }
            if (filetype.equalsIgnoreCase("EXCEL")) {
                response.addHeader("Content-Disposition", "inline; filename=" + filename + ".xlsx;");
                response.setContentType("application/octet-stream");
                outputStream = response.getOutputStream();
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                exporter.exportReport();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exporPeriodosAcademicosPeriodosProfesionalesCursosByYear(String filename, String filetype, HttpServletResponse response, Date starDate, Date endDate) {
        InputStream sourceJrxmlFile = this.getClass().getResourceAsStream("/MallaCurricular.jrxml");
        JasperPrint jasperPrint;
        ServletOutputStream outputStream;
        List<PeriodoAcademicoDuracionDto> periodos = periodoAcademicoSvc.findByFechaInicioPeriodoAcadBetween(starDate, endDate).stream().map(periodo -> {
            PeriodoAcademicoDuracionDto dto = new PeriodoAcademicoDuracionDto();
            dto.setCodigo(periodo.getCodigo());
            dto.setDescripcion(periodo.getDescripcion());

            // Convertir java.util.Date a java.time.LocalDate
            LocalDate fechaInicio = periodo.getFechaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate fechaFin = periodo.getFechaFin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Calcular duración
            dto.setDuracion((int) java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin));

            return dto;
        }).collect(Collectors.toList());
        //TODO poner cuantos periodos y restar cuanto duro cuanto uno
        List<ProPeriodosDuracionDto> periodosPro = proPeriodoSvc.findByFechaInicioBetween(starDate, endDate).stream().map(periodo -> {
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
        //TODO poner cuantos periodos de profesioanlizacion y cuanto duro cada uno
        List<CursoDuracionDto> cursos = cursoService.findByFechaInicioBetween(starDate, endDate).stream().map(curso -> {
            CursoDuracionDto dto = new CursoDuracionDto();
            dto.setCodCursoEspecializacion(curso.getCodCursoEspecializacion());
            dto.setNombre(curso.getNombre());

            // Convertir java.util.Date a java.time.LocalDate
            dto.setDuracion((int) java.time.temporal.ChronoUnit.DAYS.between(curso.getFechaInicioCurso(), curso.getFechaFinCurso()));
            return dto;
        }).collect(Collectors.toList());
        //TODO restar curso fecha inicio y fecha fin
        try {


            JasperReport jasperReport = JasperCompileManager.compileReport(sourceJrxmlFile);
            Map<String, Object> parameters = new HashMap<>();
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            if (filetype.equalsIgnoreCase("PDF")) {
                response.addHeader("Content-Disposition", "inline; filename=" + filename + ".pdf;");
                response.setContentType("application/octet-stream");
                outputStream = response.getOutputStream();
                JRPdfExporter exporter = new JRPdfExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                exporter.exportReport();
            }
            if (filetype.equalsIgnoreCase("EXCEL")) {
                response.addHeader("Content-Disposition", "inline; filename=" + filename + ".xlsx;");
                response.setContentType("application/octet-stream");
                outputStream = response.getOutputStream();
                JRXlsxExporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
                exporter.exportReport();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
