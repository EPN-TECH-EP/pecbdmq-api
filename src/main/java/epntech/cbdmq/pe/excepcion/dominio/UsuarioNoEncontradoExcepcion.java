package epntech.cbdmq.pe.excepcion.dominio;

public class UsuarioNoEncontradoExcepcion extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3011869622475572504L;

	public UsuarioNoEncontradoExcepcion(String mensaje) {
        super(mensaje);
    }
}
