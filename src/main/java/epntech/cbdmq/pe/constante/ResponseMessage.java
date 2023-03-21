package epntech.cbdmq.pe.constante;

public class ResponseMessage {
  private String message;
  public static final String CARGA_EXITOSA = "Carga de archivo satisfactoriamente";
  public static final String CARGA_NO_EXITOSA = "No se puede cargar el archivo";
  public static final String CARGA_ARCHIVO_EXCEL = "Por favor cargar un archivo excel!";

  public ResponseMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
