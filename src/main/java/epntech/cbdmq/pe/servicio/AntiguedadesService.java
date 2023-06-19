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
	
	void generarExcel(String filePath, String nombre, Integer codTipoDocumento) throws IOException, DataException;
	
	void generarPDF(HttpServletResponse response, String filePath, String nombre, Integer codTipoDocumento)
			throws DocumentException, IOException, DataException;
}

