package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.Set;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.util.AntiguedadesDatos;
import epntech.cbdmq.pe.dominio.util.AntiguedadesFormacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.servlet.http.HttpServletResponse;

public interface AntiguedadesService {

	Set<AntiguedadesDatos> getAntiguedadesMasculino();

	Set<AntiguedadesDatos> getAntiguedadesFemenino();

	void generarExcel(String filePath, String nombre, int genero, Integer codTipoDocumento) throws IOException, DataException;

	void generarPDF(HttpServletResponse response, String filePath, String nombre, int gener, Integer codTipoDocumento)
			throws DocumentException, IOException, DataException;

	Set<AntiguedadesFormacion> getAntiguedadesFormacion();
	Set<AntiguedadesFormacion> getAntiguedadesEspecializacion(Long codCurso);
	
	void generarExcel(String filePath, String nombre) throws IOException, DataException;
	
	void generarPDF(HttpServletResponse response, String filePath, String nombre)
			throws DocumentException, IOException, DataException;
	void generarExcelEsp(String filePath, String nombre, Long codCurso) throws IOException, DataException;

	void generarPDFEsp(HttpServletResponse response, String filePath, String nombre, Long codCurso)
			throws DocumentException, IOException, DataException;
	void generarExcelReprobadosEsp(String filePath, String nombre, Long codCurso) throws IOException, DataException;

	void generarPDFReprobadosEsp(HttpServletResponse response, String filePath, String nombre, Long codCurso)
			throws DocumentException, IOException, DataException;
	void generarExcelEspGeneral(String filePath, String nombre, Long codCurso, Boolean aprobados) throws IOException, DataException;

	void generarPDFEspGeneral(HttpServletResponse response, String filePath, String nombre, Long codCurso, Boolean aprobados)
			throws DocumentException, IOException, DataException;
}

