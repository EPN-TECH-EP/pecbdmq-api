
package epntech.cbdmq.pe.util;

import java.awt.Color;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.lowagie.text.*;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import jakarta.servlet.http.HttpServletResponse;



@Component


public class ExporterPdf {

	private XSSFWorkbook libro;
	private XSSFSheet hoja;


	private String ARCHIVOS_RUTA;

	public void setArchivosRuta(String archivosRuta) {
		this.ARCHIVOS_RUTA = archivosRuta;
	}

	private void escribirCabeceraDeLaTabla(PdfPTable tabla, String[] columna) {
		PdfPCell celda = new PdfPCell();

		
		celda.setBackgroundColor(Color.RED);
		celda.setPadding(5);

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.WHITE);

		for (String elemento : columna) {
			celda.setPhrase(new Phrase(elemento, fuente));
			tabla.addCell(celda);
		}

	}

	private void escribirDatosDeLaTabla(PdfPTable tabla, ArrayList<ArrayList<String>> lista) {
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("lista");
		int nueroFilas = 1;

		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);

		for (int i = 0; i < lista.size(); i++) {
			//System.out.println("valor " + lista.get(i).get(i));
			Row fila = hoja.createRow(nueroFilas++);

			for (int j = 0; j < lista.get(i).size(); j++) {
				//Cell celda = fila.createCell(i);
				tabla.addCell(String.valueOf(lista.get(i).get(j)));
			}

		}
	}

	public void exportar(HttpServletResponse response, String[] columnas, ArrayList<ArrayList<String>> lista, float[] widths)
			throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, response.getOutputStream());

		documento.open();

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.WHITE);
		fuente.setSize(18);

		Paragraph titulo = new Paragraph("Lista", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);

		PdfPTable tabla = new PdfPTable(columnas.length);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(widths);
		tabla.setWidthPercentage(110);

		escribirCabeceraDeLaTabla(tabla, columnas);
		escribirDatosDeLaTabla(tabla, lista);

		documento.add(tabla);
		documento.close();
	}
	
	public void exportar(HttpServletResponse response, String[] columnas, ArrayList<ArrayList<String>> lista, float[] widths, String filePath)
			throws DocumentException, IOException {

		File file = new File(filePath);
		file.getParentFile().mkdirs();

		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, new FileOutputStream(filePath));

		documento.open();

		// añade imagen de cabecera
		Image imagen = Image.getInstance(this.ARCHIVOS_RUTA +"logo-bomberos.png");
		//imagen.scaleAbsolute(100, 100);
		imagen.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(imagen);

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.BLACK);
		fuente.setSize(16);
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());

		Paragraph titulo = new Paragraph("Resultados generado el: "+ fechaActual, fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);

		PdfPTable tabla = new PdfPTable(columnas.length);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(widths);
		tabla.setWidthPercentage(110);

		escribirCabeceraDeLaTabla(tabla, columnas);
		escribirDatosDeLaTabla(tabla, lista);

		documento.add(tabla);
		documento.close();
	}


}
