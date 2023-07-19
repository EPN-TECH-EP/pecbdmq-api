package epntech.cbdmq.pe.servicio.impl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoDocumentoFor;
import epntech.cbdmq.pe.dominio.util.AntiguedadesDatos;
import epntech.cbdmq.pe.dominio.util.AntiguedadesFormacion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.helper.ExcelHelper;
import epntech.cbdmq.pe.repositorio.admin.AntiguedadesFormacionRepository;
import epntech.cbdmq.pe.repositorio.admin.AntiguedadesRepository;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoDocForRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoDocRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.AntiguedadesService;
import epntech.cbdmq.pe.util.ExporterPdf;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AntiguedadesServiceImpl implements AntiguedadesService {

	@Autowired
	private AntiguedadesRepository antiguedadesRepository;
	@Autowired
	private DocumentoRepository documentoRepo;
	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;
	@Autowired
	private AntiguedadesFormacionRepository antiguedadesFormacionRepository;
	@Autowired
	private PeriodoAcademicoDocForRepository periodoAcademicoDocForRepository;

	@Override
	public Set<AntiguedadesDatos> getAntiguedadesMasculino() {

		return antiguedadesRepository.getAntiguedadesMasculino();
	}

	@Override
	public Set<AntiguedadesDatos> getAntiguedadesFemenino() {
		return antiguedadesRepository.getAntiguedadesFemenino();
	}

	@Override
	public void generarExcel(String filePath, String nombre, int genero, Integer codTipoDocumento) throws IOException, DataException {
		String[] HEADERs = { "Codigo", "id", "Cedula", "Nombre", "Apellido" };
		try {
			ExcelHelper.generarExcel(obtenerDatos(genero), filePath, HEADERs);

			generaDocumento(filePath, nombre, codTipoDocumento);

		} catch (IOException ex) {
			System.out.println("error: " + ex.getMessage());
		}

	}

	@Override
	public void generarPDF(HttpServletResponse response, String filePath, String nombre, int genero, Integer codTipoDocumento)
			throws DocumentException, IOException, DataException {
		response.setContentType("application/pdf");

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());

		String cabecera = "Cuerpo-Bomberos";
		String valor = "attachment; filename=Datos" + fechaActual + ".pdf";

		response.addHeader(cabecera, valor);

		ExporterPdf exporter = new ExporterPdf();
		String[] columnas = { "Codigo", "id", "Cedula", "Nombre", "Apellido", "Nota" };
		float[] widths = new float[] { 2f, 3f, 6f, 6f, 2.5f, 2.5f };

		exporter.exportar(response, columnas, obtenerDatos(genero), widths, filePath);

		generaDocumento(filePath, nombre, codTipoDocumento);

	}

	public ArrayList<ArrayList<String>> obtenerDatos(int genero) {
		Set<AntiguedadesDatos> datos = new HashSet<>();
		if(genero == 1) {
			datos = antiguedadesRepository.getAntiguedadesFemenino();
		}else if(genero == 0) {
			datos = antiguedadesRepository.getAntiguedadesMasculino();
		}
		
		return entityToArrayList(datos);
	}

	public ArrayList<ArrayList<String>> obtenerDatos() {
		Set<AntiguedadesFormacion> datos = new HashSet<>();
		
		datos = antiguedadesFormacionRepository.getAntiguedadesFormacion();
		
		return entityToArrayListFormacion(datos);
	}

	public static String[] entityToStringArray(AntiguedadesDatos entity) {
		return new String[] { entity.getCodPostulante().toString(), entity.getIdPostulante().toString(),
				entity.getCedula(), entity.getNombre(), entity.getApellido(),
				entity.getNotaPromedioFinal().toString() };
	}

	public static ArrayList<ArrayList<String>> entityToArrayList(Set<AntiguedadesDatos> datos) {
		ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
		for (AntiguedadesDatos dato : datos) {

			arrayMulti.add(new ArrayList<String>(Arrays.asList(entityToStringArray(dato))));
		}
		return arrayMulti;
	}

	public static String[] entityToStringArrayFormacion(AntiguedadesFormacion entity) {
		return new String[] { entity.getCodigoUnicoEstudiante(), entity.getCedula(),
				entity.getNombre(), entity.getApellido(), entity.getCorreoPersonal(), 
				entity.getNotaFinal().toString() };
	}

	public static ArrayList<ArrayList<String>> entityToArrayListFormacion(Set<AntiguedadesFormacion> datos) {
		ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
		for (AntiguedadesFormacion dato : datos) {

			arrayMulti.add(new ArrayList<String>(Arrays.asList(entityToStringArrayFormacion(dato))));
		}
		return arrayMulti;
	}

	private void generaDocumento(String ruta, String nombre, Integer codTipoDocumento) {
		Documento documento = new Documento();
		Optional<Documento> documento2= documentoRepo.findByNombre(nombre);
		if(documento2.isPresent()) {
			documento = documento2.get();
		}
		documento.setEstado("ACTIVO");
		documento.setNombre(nombre);
		documento.setRuta(ruta);
		documento.setTipo(codTipoDocumento);

		documento = documentoRepo.save(documento);

		PeriodoAcademicoDocumentoFor doc = new PeriodoAcademicoDocumentoFor();
		doc.setCodPeriodoAcademico(periodoAcademicoRepository.getPAActive());;
		doc.setCodDocumento(documento.getCodDocumento());		
		periodoAcademicoDocForRepository.save(doc);

		// System.out.println("documento.getCodigo(): " + documento.getCodigo());
	}

	@Override
	public Set<AntiguedadesFormacion> getAntiguedadesFormacion() {
		// TODO Auto-generated method stub
		return antiguedadesFormacionRepository.getAntiguedadesFormacion();
	}

	@Override
	public void generarExcel(String filePath, String nombre, Integer codTipoDocumento)
			throws IOException, DataException {
		String[] HEADERs = { "Codigo Unico", "Cedula", "Nombre", "Apellido", "Correo", "Nota" };
		try {
			ExcelHelper.generarExcel(obtenerDatos(), filePath, HEADERs);

			generaDocumento(filePath, nombre, codTipoDocumento);

		} catch (IOException ex) {
			System.out.println("error: " + ex.getMessage());
		}
		
	}

	@Override
	public void generarPDF(HttpServletResponse response, String filePath, String nombre, Integer codTipoDocumento)
			throws DocumentException, IOException, DataException {
		response.setContentType("application/pdf");

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());

		String cabecera = "Cuerpo-Bomberos";
		String valor = "attachment; filename=Datos" + fechaActual + ".pdf";

		response.addHeader(cabecera, valor);

		ExporterPdf exporter = new ExporterPdf();
		String[] columnas = { "Codigo Unico", "Cedula", "Nombre", "Apellido", "Correo", "Nota" };
		float[] widths = new float[] { 2f, 2f, 6f, 6f, 2.5f, 2f };

		exporter.exportar(response, columnas, obtenerDatos(), widths, filePath);

		generaDocumento(filePath, nombre, codTipoDocumento);
		
	}

}

