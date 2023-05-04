package epntech.cbdmq.pe.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.ResultadoPruebas;


public class ResultadoPruebasHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "ID", "NOMBRE", "APELLIDO", "CEDULA" };
	static String SHEET = "Hoja1";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	/*public static ByteArrayInputStream datosToExcel(List<Excel> datos) {

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}

			int rowIdx = 1;
			for (Excel dato : datos) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(dato.getId());
				row.createCell(1).setCellValue(dato.getNombre());
				row.createCell(2).setCellValue(dato.getApellido());
				row.createCell(3).setCellValue(dato.getCedula());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}*/

	public static List<ResultadoPruebas> excelToDatos(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();

			List<ResultadoPruebas> datos = new ArrayList<ResultadoPruebas>();
			

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				ResultadoPruebas dato = new ResultadoPruebas();
			
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						
						dato.setCodModulo(Integer.parseInt(currentCell.getStringCellValue()));
						break;
					case 1:
						
						dato.setCodPostulante(Integer.parseInt(currentCell.getStringCellValue()));
						break;
					case 2:
						
						dato.setCodPrueba(Integer.parseInt(currentCell.getStringCellValue()));
						break;
					case 3:
						
						dato.setResultado(Integer.parseInt(currentCell.getStringCellValue()));
						break;
					case 4:
					
						dato.setCumplePrueba(Boolean.parseBoolean(currentCell.getStringCellValue()));
						
						break;
					default:
						break;
					}

					cellIdx++;
				}
				dato.setEstado("ACTIVO");
				datos.add(dato);
			}

			workbook.close();

			return datos;
		} catch (IOException e) {
			throw new RuntimeException("No se puede analizar el archivo de Excel: " + e.getMessage());
		}
	}
}
