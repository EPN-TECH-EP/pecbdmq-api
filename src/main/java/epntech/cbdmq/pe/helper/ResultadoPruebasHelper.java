package epntech.cbdmq.pe.helper;

import static epntech.cbdmq.pe.constante.ArchivoConst.FALLA_PROCESAR_EXCEL;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;

public class ResultadoPruebasHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Codigo", "Cedula", "Nombre", "Apellido", "Resultado", "Aprueba" };
	static String SHEET = "Hoja1";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static ByteArrayInputStream datosToExcel(List<ResultadoPruebas> datos) {

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}

			int rowIdx = 1;
			for (ResultadoPruebas dato : datos) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(dato.getCodResulPrueba());
				row.createCell(1).setCellValue(dato.getCodFuncionario());
				row.createCell(2).setCellValue(dato.getCodEstudiante());
				row.createCell(3).setCellValue(dato.getCodModulo());
				row.createCell(4).setCellValue(dato.getCodPostulante());
				row.createCell(5).setCellValue(dato.getCodPeriodoEvaluacion());
				row.createCell(6).setCellValue(dato.getCodPrueba());
				row.createCell(7).setCellValue(dato.getCodParametrizaFisica());
				row.createCell(8).setCellValue(dato.getResultado());
				row.createCell(9).setCellValue(dato.getEstado());
				row.createCell(10).setCellValue(dato.getCumplePrueba());
				row.createCell(11).setCellValue(dato.getNotaPromedioFinal());
				row.createCell(12).setCellValue(dato.getSeleccionadoFormacion());

			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(FALLA_PROCESAR_EXCEL + " " + e.getMessage());
		}
	}

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
			throw new RuntimeException(FALLA_PROCESAR_EXCEL + " " + e.getMessage());
		}
	}

	public static void generateExcel(List<ResultadosPruebasDatos> datos, String filePath) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Datos");

        // Header
		Row headerRow = sheet.createRow(0);

		for (int col = 0; col < HEADERs.length; col++) {
			Cell cell = headerRow.createCell(col);
			cell.setCellValue(HEADERs[col]);
		}
		
        int rowIndex = 1;
        
        for (ResultadosPruebasDatos dato : datos) {
			Row row = sheet.createRow(rowIndex++);

			row.createCell(0).setCellValue(dato.getIdPostulante());
			row.createCell(1).setCellValue(dato.getCedula());
			row.createCell(2).setCellValue(dato.getNombre());
			row.createCell(3).setCellValue(dato.getApellido());
			row.createCell(4).setCellValue(dato.getResultado());
			row.createCell(5).setCellValue(dato.getCumplePrueba());
			
		}    

        File file = new File(filePath);
        file.getParentFile().mkdirs();
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
    }
	
	public static void generarExcel( ArrayList<ArrayList<String>> lista, String filePath) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Datos");

        // Header
		Row headerRow = sheet.createRow(0);

		for (int col = 0; col < HEADERs.length; col++) {
			Cell cell = headerRow.createCell(col);
			cell.setCellValue(HEADERs[col]);
		}
		
        int rowIndex = 1;        
        for (int i = 0; i < lista.size(); i++) {
			//System.out.println("valor " + lista.get(i).get(i));
			Row row = sheet.createRow(rowIndex++);

			for (int j = 0; j < lista.get(i).size(); j++) {
				row.createCell(j).setCellValue(String.valueOf(lista.get(i).get(j)));
				//System.out.println("fila: " + String.valueOf(lista.get(i).get(j)));
			}

		}

        File file = new File(filePath);
        file.getParentFile().mkdirs();
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
    }
}
