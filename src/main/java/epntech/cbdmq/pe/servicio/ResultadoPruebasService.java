package epntech.cbdmq.pe.servicio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.admin.ResultadoPruebas;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.servlet.http.HttpServletResponse;

public interface ResultadoPruebasService {

	void insertAll(List<ResultadoPruebas> obj);
	
	ResultadoPruebas update(ResultadoPruebas objActualizado);
	
	Optional<ResultadoPruebas> getByCodPostulanteAndCodPruebaDetalle(Integer CodPostulante, Integer codPrueba);
	
	void uploadFile(MultipartFile file, Integer codPruebaDetalle, Integer codFuncionario, String tipoResultado);
	
	ResultadoPruebas save(ResultadoPruebas obj);
	
	ByteArrayInputStream downloadFile();
	
	void generarExcel(String filePath, String nombre, Integer subtipoPrueba)  throws IOException, DataException;
	
	void generarPDF(HttpServletResponse response, String filePath, String nombre, Integer subtipoPrueba) throws DocumentException, IOException, DataException;
	
	List<ResultadosPruebasDatos> getResultados(Integer subtipoPrueba);
}
