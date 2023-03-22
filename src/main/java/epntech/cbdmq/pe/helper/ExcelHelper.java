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

import epntech.cbdmq.pe.dominio.admin.Excel;

public class ExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "ID", "NOMBRE", "APELLIDO", "CEDULA" };
  static String SHEET = "Hoja1";

  public static boolean hasExcelFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static ByteArrayInputStream datosToExcel(List<Excel> datos) {

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
  }

  public static List<Excel> excelToDatos(InputStream is) {
    try {
      Workbook workbook = new XSSFWorkbook(is);

      Sheet sheet = workbook.getSheet(SHEET);
      Iterator<Row> rows = sheet.iterator();

      List<Excel> datos = new ArrayList<Excel>();

      int rowNumber = 0;
      while (rows.hasNext()) {
        Row currentRow = rows.next();

        // skip header
        if (rowNumber == 0) {
          rowNumber++;
          continue;
        }

        Iterator<Cell> cellsInRow = currentRow.iterator();

        Excel dato = new Excel();

        int cellIdx = 0;
        while (cellsInRow.hasNext()) {
          Cell currentCell = cellsInRow.next();
          
          switch (cellIdx) {
          case 0:
            //dato.setId((int) currentCell.getNumericCellValue());
            break;

          case 1:
            dato.setNombre(currentCell.getStringCellValue());
            break;

          case 2:
            dato.setApellido(currentCell.getStringCellValue());
            break;

          case 3:
            dato.setCedula(currentCell.getStringCellValue());
            break;

          default:
            break;
          }

          cellIdx++;
        }

        datos.add(dato);
      }

      workbook.close();

      return datos;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
    }
  }
}
