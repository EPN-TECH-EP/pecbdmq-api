package epntech.cbdmq.pe.resource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;

import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.repositorio.admin.MateriaRepository;
import epntech.cbdmq.pe.util.ExporterPdf;

import org.xhtmlrenderer.pdf.ITextRenderer;


import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/generarpdf")
public class PdfResource {
	
	@Autowired
	private MateriaRepository repo;
	
	@GetMapping("/exportar")
	public void exportarListadoDeEmpleadosEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".pdf";
		
		response.setHeader(cabecera, valor);
		
		List<Materia> materias = repo.findAll();
		
		ExporterPdf exporter = new ExporterPdf();
		String[] columnas = {"nombre", "nota", "horas", "observacion","peso","tipo","estado"};
		exporter.exportar(response,columnas, materias);
	}
}
