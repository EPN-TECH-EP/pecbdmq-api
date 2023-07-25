package epntech.cbdmq.pe.servicio.impl.formacion;

import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.repositorio.admin.formacion.ResultadoPruebasTodoRepository;
import epntech.cbdmq.pe.servicio.formacion.ResultadoPruebasTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ResultadoPruebasTodoServiceImpl implements ResultadoPruebasTodoService {

    @Autowired
    private ResultadoPruebasTodoRepository resultadoPruebasTodoRepository;

    // lista de todos los registros con paginación
    @Override
    public Page<ResultadosPruebasDatos> getResultados(Pageable pageable, Integer prueba) {
        return resultadoPruebasTodoRepository.getResultados(pageable, prueba);
    }

    // lista completa de resultados registrados por prueba
    private List<ResultadosPruebasDatos> getResultados(Integer prueba) {
        return resultadoPruebasTodoRepository.getResultados(prueba);
    }

    ///////////////////////////////////////////////
    // generaExcel de resultados por prueba
    ///////////////////////////////////////////////
    @Override
    public ByteArrayOutputStream generarExcel(Integer prueba) throws IOException {
        // obtener datos transformados
        ArrayList<ArrayList<String>> datos = this.obtenerDatosTransformados(prueba);

        // generar excel
        return ExcelHelper.generarExcelFOS(datos, "ResultadosRegistrados.xlsx", this.getHeaders());

    }

    ///////////////////////////////////////////////
    // generaPdf de resultados por prueba
    ///////////////////////////////////////////////
    @Override
    public FileOutputStream generarPdf(Integer prueba) {

        // obtener datos transformados
        ArrayList<ArrayList<String>> datos = this.obtenerDatosTransformados(prueba);

        return null;

    }

    ///////////////////////////////////////////////
    // métodos comunes para generar excel y pdf
    ///////////////////////////////////////////////

    private ArrayList<ArrayList<String>> entityToArrayList(List<ResultadosPruebasDatos> datos) {
        ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
        for (ResultadosPruebasDatos dato : datos) {

            arrayMulti.add(new ArrayList<String>(Arrays.asList(entityToStringArray(dato))));
        }
        return arrayMulti;
    }


    // campos: id y resultado registrado
    private String[] entityToStringArray(ResultadosPruebasDatos entity) {
        return new String[]{
                entity.getIdPostulante() != null ? entity.getIdPostulante().toString() : "",
                // selecciona el valor a reportar
                entity.getCumplePrueba() != null ? (entity.getCumplePrueba() ? "CUMPLE" : "NO CUMPLE") :
                        (entity.getResultado() != null || entity.getResultado() != 0) ? entity.getResultado().toString() :
                                entity.getResultadoTiempo() != null ? entity.getResultadoTiempo().toString() :
                                        entity.getNotaPromedioFinal() != null ? entity.getNotaPromedioFinal().toString() : "Error!",
        };
    }

    private String[] getHeaders() {
        return new String[]{"ID Postulante", "Resultado"};
    }

    // llamada a métodos comunes
    private ArrayList<ArrayList<String>> obtenerDatosTransformados(Integer prueba) {

        // lista de resultados por prueba
        List<ResultadosPruebasDatos> resultados = this.getResultados(prueba);

        // lista de resultados transformados
        return this.entityToArrayList(resultados);


    }


}
