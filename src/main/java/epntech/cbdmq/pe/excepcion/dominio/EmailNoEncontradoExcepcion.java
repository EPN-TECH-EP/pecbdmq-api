package epntech.cbdmq.pe.excepcion.dominio;

public class EmailNoEncontradoExcepcion extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 927905031782230541L;

	public EmailNoEncontradoExcepcion(String mensaje) {
        super(mensaje);
    }
}
