package epntech.cbdmq.pe.excepcion.dominio;

public class NoEsArchivoImagenExcepcion extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 690598431810325860L;

	public NoEsArchivoImagenExcepcion(String mensaje) {
        super(mensaje);
    }
}
