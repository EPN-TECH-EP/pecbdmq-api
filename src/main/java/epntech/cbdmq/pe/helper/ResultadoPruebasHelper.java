package epntech.cbdmq.pe.helper;

import static epntech.cbdmq.pe.constante.ArchivoConst.DOCUMENTO_NO_CUMPLE_FORMATO;
import static epntech.cbdmq.pe.constante.ArchivoConst.FALLA_PROCESAR_EXCEL;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.ResultadoPruebas;
import epntech.cbdmq.pe.dominio.util.ResultadoPruebaFisicaUtil;
import epntech.cbdmq.pe.dominio.util.ResultadoPruebasUtil;
import epntech.cbdmq.pe.dominio.util.ResultadosPruebasDatos;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.servicio.impl.ResultadoPruebasFisicasServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultadoPruebasHelper {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"Codigo", "id", "Cedula", "Nombre", "Apellido"};
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
                row.createCell(4).setCellValue(dato.getCodPostulante());
                row.createCell(6).setCellValue(dato.getCodPruebaDetalle());
                row.createCell(9).setCellValue(dato.getEstado());
                row.createCell(10).setCellValue(dato.getCumplePrueba());
                row.createCell(11).setCellValue(dato.getNotaPromedioFinal());
                row.createCell(12).setCellValue(dato.getSeleccionado());

            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(FALLA_PROCESAR_EXCEL + " " + e.getMessage());
        }
    }

    private static String getCellValueAsString(Cell cell) throws DataException {
        if (cell == null) {
            throw new DataException(DOCUMENTO_NO_CUMPLE_FORMATO);
        }

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                // Manejar celdas con formato de fecha
                DateFormat dateFormat = new SimpleDateFormat("h:mm:ss");
                return dateFormat.format(cell.getDateCellValue());
            } else {
                // Formatear el valor num�rico como una cadena
                return String.valueOf(cell.getNumericCellValue());
            }
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.BLANK) {
            throw new DataException(DOCUMENTO_NO_CUMPLE_FORMATO);
        } else {
            throw new DataException(DOCUMENTO_NO_CUMPLE_FORMATO);
        }
    }

    public static List<ResultadoPruebasUtil> excelToDatos(InputStream is, String tipoResultado) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<ResultadoPruebasUtil> datos = new ArrayList<ResultadoPruebasUtil>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                ResultadoPruebasUtil dato = new ResultadoPruebasUtil();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {

                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            String codPruebaDetalleStr = getCellValueAsString(currentCell);
                            if (codPruebaDetalleStr != null && !codPruebaDetalleStr.isEmpty()) {
                                dato.setIdPostulante(codPruebaDetalleStr);
                            }
                            break;
                        case 1:

                            codPruebaDetalleStr = getCellValueAsString(currentCell);
                            if (codPruebaDetalleStr != null && !codPruebaDetalleStr.isEmpty()) {

                                if (tipoResultado.equals("CUMPLE|NO-CUMPLE")) {
                                    if (codPruebaDetalleStr.equals("CUMPLE"))
                                        dato.setCumplePrueba(true);
                                    else
                                        dato.setCumplePrueba(false);
                                }
                                if (tipoResultado.equals("NOTA")) {
                                    Double nota = Double.parseDouble(getCellValueAsString(currentCell));
                                    dato.setNotaPromedioFinal(nota);

                                }
                            }


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
            throw new RuntimeException(FALLA_PROCESAR_EXCEL + " " + e.getMessage());
        } catch (DataException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<ResultadoPruebaFisicaUtil> excelToDatosPruebasFisicasI(InputStream contenidoBytes, String tipoResultado) throws DataException {
        try {
            Workbook workbook = new XSSFWorkbook(contenidoBytes);

            Sheet sheet = workbook.getSheet(SHEET);
            int numRows = sheet.getPhysicalNumberOfRows();

            Iterator<Row> rows = sheet.iterator();

            List<ResultadoPruebaFisicaUtil> datos = new ArrayList<ResultadoPruebaFisicaUtil>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                ResultadoPruebaFisicaUtil dato = new ResultadoPruebaFisicaUtil();

                int cellIdx = 0;
                while (cellsInRow.hasNext()) {

                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:

                            String codPruebaDetalleStr = getCellValueAsString(currentCell);
                            if (codPruebaDetalleStr != null && !codPruebaDetalleStr.isEmpty()) {
                                dato.setIdPostulante(codPruebaDetalleStr);
                            }
                            break;
                        case 1:
                            codPruebaDetalleStr = getCellValueAsString(currentCell);
                            if (codPruebaDetalleStr != null && !codPruebaDetalleStr.isEmpty()) {
                                if (tipoResultado.equals("RESULTADO TIEMPO"))
                                    dato.setResultadoTiempo(Time.valueOf(codPruebaDetalleStr));
                                else if (tipoResultado.equals("RESULTADO")) {
                                    double valorNumerico = Double.parseDouble(codPruebaDetalleStr);
                                    int resultado = (int) Math.round(valorNumerico);
                                    dato.setResultado(resultado);
                                } else {
                                    throw new RuntimeException("No se reconoce el tipo de resultado");
                                }
                            }
                            break;
                        default:
                            break;
                    }
                    System.out.println("datos" + dato.toString());

                    cellIdx++;
                }
                datos.add(dato);
            }

            workbook.close();

            return datos;
        } catch (IOException | DataException e) {
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

            row.createCell(0).setCellValue(dato.getCodPostulante());
            row.createCell(1).setCellValue(dato.getIdPostulante());
            row.createCell(2).setCellValue(dato.getCedula());
            row.createCell(3).setCellValue(dato.getNombre());
            row.createCell(4).setCellValue(dato.getApellido());

        }

        File file = new File(filePath);
        file.getParentFile().mkdirs();
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        workbook.close();
    }

    public static void generarExcel(ArrayList<ArrayList<String>> lista, String filePath) throws IOException {
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
