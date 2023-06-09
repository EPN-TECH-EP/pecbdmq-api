//TODO revisar
package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import epntech.cbdmq.pe.dominio.util.UsuarioDtoRead;
import epntech.cbdmq.pe.dominio.util.UsuarioInfoDto;
import epntech.cbdmq.pe.excepcion.dominio.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.Usuario;
import jakarta.mail.MessagingException;

public interface UsuarioService {

	// Usuario registrar(String firstName, String lastName, String username, String
	// email)
	Usuario registrar(Usuario usuario)
			throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion, EmailExisteExcepcion, MessagingException;

	public Optional<Usuario> getById(Long codigo);

	List<Usuario> getUsuarios();

	List<Usuario> getUsuariosPageable(Pageable pageable);

	List<UsuarioDtoRead> getUsuariosPer(Pageable pageable);

	Usuario findUserByUsername(String username);

	// Usuario findUserByEmail(String email);

	Usuario crear(Usuario usuario)
			throws NombreUsuarioExisteExcepcion,
			EmailExisteExcepcion, IOException, UsuarioNoEncontradoExcepcion, MessagingException;

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

	void resetPassword(String email)
			throws MessagingException, EmailNoEncontradoExcepcion, UsuarioNoEncontradoExcepcion;

	Usuario actualizarImagenPerfil(String username, MultipartFile profileImage) throws UsuarioNoEncontradoExcepcion,
			NombreUsuarioExisteExcepcion, EmailExisteExcepcion, IOException, NoEsArchivoImagenExcepcion;

	void guardarArchivo(String nombreArchivo, MultipartFile archivo) throws IOException, ArchivoMuyGrandeExcepcion;

	long tamañoMáximoArchivo();

	public List<Usuario> findUsuariosByNombreApellido(String nombre, String apellido);

	public List<Usuario> findUsuariosByApellido(String apellido);

	public List<Usuario> findUsuariosByNombre(String nombre);

	public List<Usuario> findUsuariosByCorreo(String correo);
	public UsuarioInfoDto getUsuarioInfo(String codUser) throws DataException;
}
