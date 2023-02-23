package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.*;
import static epntech.cbdmq.pe.constante.UsuarioImplConst.NOMBRE_USUARIO_ENCONTRADO;
import static epntech.cbdmq.pe.constante.UsuarioImplConst.NOMBRE_USUARIO_YA_EXISTE;
import static epntech.cbdmq.pe.constante.UsuarioImplConst.NO_EXISTE_USUARIO;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import epntech.cbdmq.pe.dominio.UserPrincipal;
import epntech.cbdmq.pe.dominio.Usuario;
import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.enumeracion.Role;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.EmailExisteExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.EmailNoEncontradoExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.NoEsArchivoImagenExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.NombreUsuarioExisteExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.UsuarioNoEncontradoExcepcion;
import epntech.cbdmq.pe.repositorio.UsuarioRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.IntentoLoginService;
import epntech.cbdmq.pe.servicio.UsuarioService;
import jakarta.mail.MessagingException;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	private UsuarioRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;
	private IntentoLoginService loginAttemptService;
	private EmailService emailService;

	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;

	@Autowired
	public UsuarioServiceImpl(UsuarioRepository userRepository, BCryptPasswordEncoder passwordEncoder,
			IntentoLoginService loginAttemptService, EmailService emailService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.loginAttemptService = loginAttemptService;
		this.emailService = emailService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = userRepository.findUsuarioByNombreUsuario(username);
		if (user == null) {
			LOGGER.error(NO_EXISTE_USUARIO + username);
			throw new UsernameNotFoundException(NO_EXISTE_USUARIO + username);
		} else {
			validateLoginAttempt(user);
			user.setFechaUltimoLoginMostrar(user.getFechaUltimoLogin());
			user.setFechaUltimoLogin(new Date());
			userRepository.save(user);
			UserPrincipal userPrincipal = new UserPrincipal(user);
			LOGGER.info(NOMBRE_USUARIO_ENCONTRADO + username);
			return userPrincipal;
		}

	}

	@Override
	// public Usuario registrar(String firstName, String lastName, String username,
	// String email) throws UsuarioNoEncontradoExcepcion,
	// NombreUsuarioExisteExcepcion, EmailExisteExcepcion, MessagingException {
	public Usuario registrar(Usuario usuario) throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion,
			EmailExisteExcepcion, MessagingException {
		validateNewUsernameAndEmail(EMPTY, usuario.getNombreUsuario(),
				usuario.getCodDatosPersonales().getCorreo_personal());

		// datos de usuario
		Usuario user = new Usuario();
		// user.setUserId(generateUserId());
		String password = generatePassword();
		// user.setNombres(firstName);
		// user.setApellidos(lastName);
		user.setNombreUsuario(usuario.getNombreUsuario());
		// user.setEmail(email);
		user.setFechaRegistro(new Date());
		user.setClave(encodePassword(password));
		user.setActive(true);
		user.setNotLocked(true);
		// user.setUrlImagenPerfil(getTemporaryProfileImageUrl(username));

		// datos personales
		DatoPersonal datos = new DatoPersonal();
		datos.setNombre(usuario.getCodDatosPersonales().getNombre());
		datos.setApellido(usuario.getCodDatosPersonales().getApellido());
		datos.setCorreo_personal(usuario.getCodDatosPersonales().getCorreo_personal());

		// asocia datos personales con usuario
		user.setCodDatosPersonales(datos);

		userRepository.save(user);

		userRepository.flush();

		emailService.sendNewPasswordEmail(usuario.getCodDatosPersonales().getNombre(), password,
				usuario.getCodDatosPersonales().getCorreo_personal());
		return user;
	}

	@Override
	public Usuario nuevoUsuario(String firstName, String lastName, String username, String email, String role,
			boolean isNonLocked, boolean isActive, MultipartFile profileImage) throws UsuarioNoEncontradoExcepcion,
			NombreUsuarioExisteExcepcion, EmailExisteExcepcion, IOException, NoEsArchivoImagenExcepcion {
		validateNewUsernameAndEmail(EMPTY, username, email);
		Usuario user = new Usuario();
		String password = generatePassword();
		// user.setUserId(generateUserId());
		// user.setNombres(firstName);
		// user.setApellidos(lastName);
		user.setFechaRegistro(new Date());
		user.setNombreUsuario(username);
		// user.setEmail(email);
		// user.setPassword(encodePassword(password));
		user.setActive(isActive);
		user.setNotLocked(isNonLocked);
		// user.setUrlImagenPerfil(getTemporaryProfileImageUrl(username));
		userRepository.save(user);
		// saveProfileImage(user, profileImage);
		// TODO eliminar línea de log
		LOGGER.info("New user password: " + password);
		return user;
	}

	@Override
	public Usuario actualizarUsuario(String currentUsername, String newFirstName, String newLastName,
			String newUsername, String newEmail, String role, boolean isNonLocked, boolean isActive,
			MultipartFile profileImage) throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion,
			EmailExisteExcepcion, IOException, NoEsArchivoImagenExcepcion {
		Usuario currentUser = validateNewUsernameAndEmail(currentUsername, newUsername, newEmail);
		// currentUser.setNombres(newFirstName);
		// currentUser.setApellidos(newLastName);
		currentUser.setNombreUsuario(newUsername);
		// currentUser.setEmail(newEmail);
		currentUser.setActive(isActive);
		currentUser.setNotLocked(isNonLocked);
		userRepository.save(currentUser);
		// saveProfileImage(currentUser, profileImage);
		return currentUser;
	}

	@Override
	public void resetPassword(String email) throws MessagingException, EmailNoEncontradoExcepcion {
		/*
		 * Usuario user = userRepository.findUsuarioByEmail(email); if (user == null) {
		 * throw new EmailNoEncontradoExcepcion(NO_EXISTE_USUARIO_EMAIL + email); }
		 */
		String password = generatePassword();
		// user.setPassword(encodePassword(password));
		// userRepository.save(user);
		// TODO eliminar línea de log
		LOGGER.info("New user password: " + password);
		// emailService.sendNewPasswordEmail(user.getNombres().concat("
		// ").concat(user.getApellidos()), password, user.getEmail());
	}

	@Override
	public Usuario actualizarImagenPerfil(String username, MultipartFile profileImage)
			throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion, EmailExisteExcepcion, IOException,
			NoEsArchivoImagenExcepcion {
		Usuario user = validateNewUsernameAndEmail(username, null, null);
		// saveProfileImage(user, profileImage);
		return user;
	}

	@Override
	public List<Usuario> getUsuarios() {
		return userRepository.findAll();
	}

	@Override
	public Usuario findUserByUsername(String username) {
		return userRepository.findUsuarioByNombreUsuario(username);
	}

	/*
	 * @Override public Usuario findUserByEmail(String email) { return
	 * userRepository.findUsuarioByEmail(email); }
	 */

	@Override
	public void eliminarUsuario(String username) throws IOException {
		Usuario user = userRepository.findUsuarioByNombreUsuario(username);
		Path userFolder = Paths.get(CARPETA_USUARIO + user.getNombreUsuario()).toAbsolutePath().normalize();
		FileUtils.deleteDirectory(new File(userFolder.toString()));
		userRepository.deleteById(user.getCodUsuario());
	}

	private void saveProfileImage(Usuario user, MultipartFile profileImage)
			throws IOException, NoEsArchivoImagenExcepcion {
		if (profileImage != null) {
			if (!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE,
					IMAGE_GIF_VALUE).contains(profileImage.getContentType())) {
				throw new NoEsArchivoImagenExcepcion(profileImage.getOriginalFilename() +
						NO_ES_ARCHIVO_IMAGEN);
			}
			Path userFolder = Paths.get(CARPETA_USUARIO +
					user.getNombreUsuario()).toAbsolutePath().normalize();
			if (!Files.exists(userFolder)) {
				Files.createDirectories(userFolder);
				LOGGER.info(DIRECTORIO_CREADO + userFolder);
			}
			Files.deleteIfExists(Paths.get(userFolder + user.getNombreUsuario() + PUNTO +
					EXTENSION_JPG));
			Files.copy(profileImage.getInputStream(),
					userFolder.resolve(user.getNombreUsuario() + PUNTO + EXTENSION_JPG),
					REPLACE_EXISTING);
			//user.setUrlImagenPerfil(setProfileImageUrl(user.getNombreUsuario()));
			//userRepository.save(user);
			LOGGER.info(ARCHIVO_GUARDADO +
					profileImage.getOriginalFilename());
		}
	}

	private String setProfileImageUrl(String username) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(PATH_IMAGEN_USUARIO + username + FORWARD_SLASH
				+ username + PUNTO + EXTENSION_JPG).toUriString();
	}

	private Role getRoleEnumName(String role) {
		return Role.valueOf(role.toUpperCase());
	}

	private String getTemporaryProfileImageUrl(String username) {
		return ServletUriComponentsBuilder.fromCurrentContextPath().path(PATH_DEFECTO_IMAGEN_USUARIO + username)
				.toUriString();
	}

	private String encodePassword(String password) {

		String encodedPassword = passwordEncoder.encode(password);
		return encodedPassword;
	}

	private String generatePassword() {
		return RandomStringUtils.randomAlphanumeric(20);
	}

	private String generateUserId() {
		return RandomStringUtils.randomNumeric(10);
	}

	private void validateLoginAttempt(Usuario user) {
		if (user.isNotLocked()) {
			if (loginAttemptService.excedeMaximoIntentos(user.getNombreUsuario())) {
				user.setNotLocked(false);
			} else {
				user.setNotLocked(true);
			}
		} else {
			loginAttemptService.retirarUsuarioDeCache(user.getNombreUsuario());
		}
	}

	private Usuario validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
			throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion, EmailExisteExcepcion {
		Usuario userByNewUsername = findUserByUsername(newUsername);
		// Usuario userByNewEmail = findUserByEmail(newEmail);
		if (StringUtils.isNotBlank(currentUsername)) {
			Usuario currentUser = findUserByUsername(currentUsername);
			if (currentUser == null) {
				throw new UsuarioNoEncontradoExcepcion(NO_EXISTE_USUARIO + currentUsername);
			}
			if (userByNewUsername != null && !currentUser.getCodUsuario().equals(userByNewUsername.getCodUsuario())) {
				throw new NombreUsuarioExisteExcepcion(NOMBRE_USUARIO_YA_EXISTE);
			}
			/*
			 * if(userByNewEmail != null &&
			 * !currentUser.getCodUsuario().equals(userByNewEmail.getCodUsuario())) { throw
			 * new EmailExisteExcepcion(EMAIL_YA_EXISTE); }
			 */
			return currentUser;
		} else {
			if (userByNewUsername != null) {
				throw new NombreUsuarioExisteExcepcion(NOMBRE_USUARIO_YA_EXISTE);
			}
			/*
			 * if(userByNewEmail != null) { throw new EmailExisteExcepcion(EMAIL_YA_EXISTE);
			 * }
			 */
			return null;
		}
	}

	public void guardarArchivo(String nombreArchivo, MultipartFile archivo)
			throws IOException, ArchivoMuyGrandeExcepcion { 
		
		if (archivo.getSize() > Long.parseLong(TAMAÑO_MÁXIMO)) {
			throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
		}

		Path ruta = Paths.get(ARCHIVOS_RUTA).toAbsolutePath().normalize();
		if (!Files.exists(ruta)) {
			Files.createDirectories(ruta);
		}

		Files.copy(archivo.getInputStream(), ruta.resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
		LOGGER.info("Archivo guardado: " + ARCHIVOS_RUTA + nombreArchivo);
	}
	
	public long tamañoMáximoArchivo() {
		return Long.parseLong(TAMAÑO_MÁXIMO);
	}

}
