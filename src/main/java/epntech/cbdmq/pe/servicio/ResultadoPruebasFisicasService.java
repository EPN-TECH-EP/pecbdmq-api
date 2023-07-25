package epntech.cbdmq.pe.servicio;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.admin.ResultadoPruebasFisicas;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasFisicasDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;

public interface ResultadoPruebasFisicasService {

	void insertAll(List<ResultadoPruebasFisicas> obj);
	
	ResultadoPruebasFisicas update(ResultadoPruebasFisicas objActualizado);
	void uploadFile(MultipartFile file, Integer codPruebaDetalle, Integer codFuncionario, String tipoResultado) throws DataException;
	
	ResultadoPruebasFisicas save(ResultadoPruebasFisicas obj);
	
	ByteArrayInputStream downloadFile();
	
	void generarExcel(String nombre, Integer subtipoPrueba)  throws IOException, DataException;
	
	void generarPDF(HttpServletResponse response, String nombre, Integer subtipoPrueba) throws DocumentException, IOException, DataException;
	
	List<ResultadosPruebasFisicasDatos> getResultados(Integer subtipoPrueba);
	
	void notificar(String mensaje) throws MessagingException;
	
	public Optional<ResultadoPruebasFisicas> getByCodPostulanteAndCodPruebaDetalle(Integer CodPostulante, Integer codPrueba);
}
