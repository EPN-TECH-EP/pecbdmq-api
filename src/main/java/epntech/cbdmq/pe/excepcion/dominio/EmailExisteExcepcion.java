package epntech.cbdmq.pe.excepcion.dominio;

public class EmailExisteExcepcion extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 96472732694508762L;

	public EmailExisteExcepcion(String mensaje) {
        super(mensaje);
    }
}
