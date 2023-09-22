package epntech.cbdmq.pe.servicio.formacion;

import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasTodoReprobadosAprobados;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ResultadoPruebasTodoService {

    Page<ResultadosPruebasDatos> getResultados(Pageable pageable, Integer prueba);

    // generaExcel de resultados por prueba
    ByteArrayOutputStream generarExcel(Integer prueba) throws IOException;

    // generaPdf de resultados por prueba
    FileOutputStream generarPdf(Integer prueba);

    // lista de todos los registros con paginación CURSO
    Page<ResultadosPruebasDatos> getResultados(Pageable pageable, Integer prueba, Integer codCurso);

    ByteArrayOutputStream generarExcelCurso(Integer prueba, Integer codCurso) throws IOException;

    ArrayList<ArrayList<String>> obtenerDatosTransformados(Integer prueba, Integer codCurso);
    List<ResultadosPruebasTodoReprobadosAprobados> getResultadosReprobadosAprobados(Integer prueba);
}
