//TODO revisar
package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.admin.Paralelo;
import epntech.cbdmq.pe.dominio.util.UsuarioDtoRead;
import org.springframework.data.domain.Pageable;
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


	public Optional<Usuario> getById(Long codigo);
	List<Usuario> getUsuarios();
	List<Usuario> getUsuariosPageable(Pageable pageable);
	List<UsuarioDtoRead> getUsuariosPer();

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
	int actualizarActive(Boolean active, String username)
			throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion, EmailExisteExcepcion, IOException,
			NoEsArchivoImagenExcepcion;
	int actualizarNotLock(Boolean notLock, String username)
			throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion, EmailExisteExcepcion, IOException,
			NoEsArchivoImagenExcepcion;

	void eliminarUsuario(String username) throws IOException;

	void resetPassword(String email) throws MessagingException, EmailNoEncontradoExcepcion;

	Usuario actualizarImagenPerfil(String username, MultipartFile profileImage) throws UsuarioNoEncontradoExcepcion,
			NombreUsuarioExisteExcepcion, EmailExisteExcepcion, IOException, NoEsArchivoImagenExcepcion;

	void guardarArchivo(String nombreArchivo, MultipartFile archivo) throws IOException, ArchivoMuyGrandeExcepcion;

	long tamañoMáximoArchivo();
	
	public List<Usuario> findUsuariosByNombreApellido(String nombre, String apellido);

	public List<Usuario> findUsuariosByCorreo(String correo);
}
