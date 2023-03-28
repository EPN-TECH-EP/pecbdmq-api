package epntech.cbdmq.pe.resource;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lowagie.text.DocumentException;

import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.repositorio.admin.MateriaRepository;
import epntech.cbdmq.pe.util.ExporterPdf;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/generarpdf")
public class PdfResource {
	
	@Autowired
	private MateriaRepository repo;

	@GetMapping("/exportarmateria")
	public void exportarListadoDeMateriasEnPDF(HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/pdf");

		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());

		String cabecera = "Cuerpo-Bomberos";
		String valor = "attachment; filename=Materias" + fechaActual + ".pdf";

		response.addHeader(cabecera, valor);

		ExporterPdf exporter = new ExporterPdf();
		String[] columnas = { "codigo", "nombre", "nota", "horas", "observacion", "peso", "tipo", "estado" };
		float[] widths = new float[] { 2f, 6f, 1.5f, 1.8f, 6f, 1.5f, 2.5f, 2.2f };
		
		exporter.exportar(response, columnas, obtenerMaterias(), widths);
	}

	public ArrayList<ArrayList<String>> obtenerMaterias() {
		List<Materia> materias = repo.findAll();
		return materiasToArrayList(materias);
	}

	public static String[] materiaToStringArray(Materia materia) {
		return new String[] { materia.getCodMateria().toString(), materia.getNombreMateria(),
				materia.getNotaMinima().toString(), materia.getNumHoras().toString(), materia.getObservacionMateria(),
				materia.getPesoMateria().toString(), materia.getTipoMateria(), materia.getEstado() };
	}

	public static ArrayList<ArrayList<String>> materiasToArrayList(List<Materia> materias) {
		ArrayList<ArrayList<String>> arrayMulti = new ArrayList<ArrayList<String>>();
		for (Materia materia : materias) {
			
			arrayMulti.add(new ArrayList<String>(Arrays.asList(materiaToStringArray(materia))));
		}
		return arrayMulti;
	}
	
	
}
