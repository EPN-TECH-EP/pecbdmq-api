//TODO revisar
package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.Usuario;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.EmailExisteExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.EmailNoEncontradoExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.NoEsArchivoImagenExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.NombreUsuarioExisteExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.UsuarioNoEncontradoExcepcion;
import jakarta.mail.MessagingException;

public interface UsuarioService {

	// Usuario registrar(String firstName, String lastName, String username, String
	// email)
	Usuario registrar(Usuario usuario)
			throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion, EmailExisteExcepcion, MessagingException;

	List<Usuario> getUsuarios();

	Usuario findUserByUsername(String username);

	// Usuario findUserByEmail(String email);

	Usuario nuevoUsuario(String firstName, String lastName, String username, String email, String role,
			boolean isNonLocked,
			boolean isActive, MultipartFile profileImage)
			throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion,
			EmailExisteExcepcion, IOException, NoEsArchivoImagenExcepcion;

	Usuario actualizarUsuario(Usuario usuario)
			throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion, EmailExisteExcepcion, IOException,
			NoEsArchivoImagenExcepcion;

	void eliminarUsuario(String username) throws IOException;

	void resetPassword(String email) throws MessagingException, EmailNoEncontradoExcepcion, UsuarioNoEncontradoExcepcion;

	Usuario actualizarImagenPerfil(String username, MultipartFile profileImage) throws UsuarioNoEncontradoExcepcion,
			NombreUsuarioExisteExcepcion, EmailExisteExcepcion, IOException, NoEsArchivoImagenExcepcion;

	void guardarArchivo(String nombreArchivo, MultipartFile archivo) throws IOException, ArchivoMuyGrandeExcepcion;

	long tamañoMáximoArchivo();
	
	public List<Usuario> findUsuariosByNombreApellido(String nombre, String apellido);
	
	
}
