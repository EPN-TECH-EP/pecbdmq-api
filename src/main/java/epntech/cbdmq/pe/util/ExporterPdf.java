package epntech.cbdmq.pe.util;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.lowagie.text.Font;

import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class ExporterPdf {

	private void escribirCabeceraDeLaTabla(PdfPTable tabla, String[] columna) {
		PdfPCell celda = new PdfPCell();

		celda.setBackgroundColor(Color.BLUE);
		celda.setPadding(5);

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);
		
		for (String elemento : columna) {
			celda.setPhrase(new Phrase(elemento, fuente));
			tabla.addCell(celda);
		}

	}
	
	
	private void escribirDatosDeLaTabla(PdfPTable tabla,  List<?> lista) {
		
		for (int i = 0; i < lista.size(); i++) {
			
			tabla.addCell(String.valueOf(lista.get(i)));
			
		}
	}
	public void exportar(HttpServletResponse response,  String[] columna,  List<?> lista) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());

		documento.open();

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.BLUE);
		fuente.setSize(18);

		Paragraph titulo = new Paragraph("Lista", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);

		PdfPTable tabla = new PdfPTable(8);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] { 1f, 2.3f, 2.3f, 6f, 2.9f, 3.5f, 2f, 2.2f });
		tabla.setWidthPercentage(110);

		escribirCabeceraDeLaTabla(tabla, columna);
		escribirDatosDeLaTabla(tabla,lista);

		documento.add(tabla);
		documento.close();
	}
	
}
