package epntech.cbdmq.pe.servicio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.admin.ResultadoPruebas;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import jakarta.servlet.http.HttpServletResponse;

public interface ResultadoPruebasService {

	void insertAll(List<ResultadoPruebas> obj);
	
	ResultadoPruebas update(ResultadoPruebas objActualizado);
	
	Optional<ResultadoPruebas> getByCodPostulanteAndPrueba(Integer CodPostulante, Integer codPrueba);
	
	void uploadFile(MultipartFile file);
	
	ResultadoPruebas save(ResultadoPruebas obj);
	
	ByteArrayInputStream downloadFile();
	
	void generarExcel(String filePath, String nombre)  throws IOException;
	
	void generarPDF(HttpServletResponse response, String nombre) throws DocumentException, IOException;
	
	List<ResultadosPruebasDatos> getResultados();
}
