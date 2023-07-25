package epntech.cbdmq.pe.servicio.formacion;

import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public interface ResultadoPruebasTodoService {

    Page<ResultadosPruebasDatos> getResultados(Pageable pageable, Integer prueba);

    // generaExcel de resultados por prueba
    ByteArrayOutputStream generarExcel(Integer prueba) throws IOException;

    // generaPdf de resultados por prueba
    FileOutputStream generarPdf(Integer prueba);
}
