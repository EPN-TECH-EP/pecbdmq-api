
package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_GUARDADO;
import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.CARPETA_USUARIO;
import static epntech.cbdmq.pe.constante.ArchivoConst.DIRECTORIO_CREADO;
import static epntech.cbdmq.pe.constante.ArchivoConst.EXTENSION_JPG;
import static epntech.cbdmq.pe.constante.ArchivoConst.FORWARD_SLASH;
import static epntech.cbdmq.pe.constante.ArchivoConst.NO_ES_ARCHIVO_IMAGEN;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_DEFECTO_IMAGEN_USUARIO;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_IMAGEN_USUARIO;
import static epntech.cbdmq.pe.constante.ArchivoConst.PUNTO;
import static epntech.cbdmq.pe.constante.MensajesConst.NO_ENCUENTRA;
import static epntech.cbdmq.pe.constante.UsuarioImplConst.EMAIL_YA_EXISTE;
import static epntech.cbdmq.pe.constante.UsuarioImplConst.NOMBRE_USUARIO_ENCONTRADO;
import static epntech.cbdmq.pe.constante.UsuarioImplConst.NOMBRE_USUARIO_YA_EXISTE;
import static epntech.cbdmq.pe.constante.UsuarioImplConst.NO_EXISTE_USUARIO;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.MediaType.IMAGE_GIF_VALUE;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import epntech.cbdmq.pe.dominio.UserPrincipal;
import epntech.cbdmq.pe.dominio.Usuario;
import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.dominio.fichaPersonal.Instructor;
import epntech.cbdmq.pe.dominio.util.UsuarioDtoRead;
import epntech.cbdmq.pe.dominio.util.UsuarioInfoDto;
import epntech.cbdmq.pe.enumeracion.Role;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.excepcion.dominio.EmailExisteExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.NoEsArchivoImagenExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.NombreUsuarioExisteExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.UsuarioNoEncontradoExcepcion;
import epntech.cbdmq.pe.repositorio.UsuarioRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.EstudianteService;
import epntech.cbdmq.pe.servicio.InstructorService;
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
	private InstructorService instructorService;
	private EstudianteService estudianteService;

	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;

	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;

	@Autowired
	public UsuarioServiceImpl(UsuarioRepository userRepository, BCryptPasswordEncoder passwordEncoder,
			IntentoLoginService loginAttemptService, EmailService emailService, EstudianteService estudianteService,
			InstructorService instructorService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.loginAttemptService = loginAttemptService;
		this.emailService = emailService;
		this.estudianteService = estudianteService;
		this.instructorService = instructorService;
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
			EmailExisteExcepcion, MessagingException, IOException {
		validateNewUsernameAndEmail(EMPTY, usuario.getNombreUsuario(),
				usuario.getCodDatosPersonales().getCorreoPersonal());

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
		datos.setCorreoPersonal(usuario.getCodDatosPersonales().getCorreoPersonal());

		// asocia datos personales con usuario
		user.setCodDatosPersonales(datos);

		userRepository.save(user);

		userRepository.flush();

		emailService.sendNewPasswordEmail(usuario.getCodDatosPersonales().getNombre(), password,
				usuario.getCodDatosPersonales().getCorreoPersonal());
		return user;
	}

	@Override
	public Optional<Usuario> getById(Long codigo) {

		// TODO Auto-generated method stub
		return userRepository.findById(codigo);

	}

	@Override
	public Usuario crear(Usuario usuario)
			throws NombreUsuarioExisteExcepcion,
			EmailExisteExcepcion, IOException, UsuarioNoEncontradoExcepcion, MessagingException {

		validateNewUsernameAndEmail(EMPTY, usuario.getNombreUsuario(),
				usuario.getCodDatosPersonales().getCorreoPersonal());

		String password = generatePassword();

		emailService.sendNewPasswordEmail(usuario.getCodDatosPersonales().getNombre(), password,
				usuario.getCodDatosPersonales().getCorreoPersonal());

		// datos de usuario
		Usuario user = new Usuario();

		user.setNombreUsuario(usuario.getNombreUsuario());
		user.setFechaRegistro(new Date());
		user.setClave(encodePassword(password));
		user.setActive(true);
		user.setNotLocked(true);

		// datos personales
		//DatoPersonal datosRecibidos = usuario.getCodDatosPersonales();
		DatoPersonal datos = usuario.getCodDatosPersonales();
		/*
		datos.setCodDatosPersonales(datosRecibidos.getCodDatosPersonales());
		datos.setApellido(datosRecibidos.getApellido());
		datos.setCedula(datosRecibidos.getCedula());
		datos.setCodEstacion(datosRecibidos.getCodEstacion());
		datos.setCorreoPersonal(datosRecibidos.getCorreoPersonal());
		datos.setEstado(datosRecibidos.getEstado());
		datos.setFechaNacimiento(datosRecibidos.getFechaNacimiento());
		datos.setNombre(datosRecibidos.getNombre());
		datos.setNumTelefConvencional(datosRecibidos.getNumTelefConvencional());
		datos.setTipoSangre(datosRecibidos.getTipoSangre());
		datos.setValidacionCorreo(datosRecibidos.getValidacionCorreo());
		datos.setCodProvinciaNacimiento(datosRecibidos.getCodProvinciaNacimiento());
		datos.setCodUnidadGestion(datosRecibidos.getCodUnidadGestion());
		datos.setSexo(datosRecibidos.getSexo());
		datos.setNumTelefCelular(datosRecibidos.getNumTelefCelular());
		datos.setResidePais(datosRecibidos.getResidePais());
		datos.setCodProvinciaResidencia(datosRecibidos.getCodProvinciaResidencia());
		datos.setCallePrincipalResidencia(datosRecibidos.getCallePrincipalResidencia());
		datos.setCalleSecundariaResidencia(datosRecibidos.getCalleSecundariaResidencia());
		datos.setNumeroCasa(datosRecibidos.getNumeroCasa());
		datos.setColegio(datosRecibidos.getColegio());
		datos.setTipoNacionalidad(datosRecibidos.getTipoNacionalidad());
		datos.setTieneMeritoDeportivo(datosRecibidos.getTieneMeritoDeportivo());
		datos.setTieneMeritoAcademico(datosRecibidos.getTieneMeritoAcademico());
		datos.setNombreTituloSegundoNivel(datosRecibidos.getNombreTituloSegundoNivel());
		datos.setPaisTituloSegundoNivel(datosRecibidos.getPaisTituloSegundoNivel());
		datos.setCiudadTituloSegundoNivel(datosRecibidos.getCiudadTituloSegundoNivel());
		datos.setMeritoDeportivoDescripcion(datosRecibidos.getMeritoDeportivoDescripcion());
		datos.setMeritoAcademicoDescripcion(datosRecibidos.getMeritoAcademicoDescripcion());
		datos.setPinValidacionCorreo(datosRecibidos.getPinValidacionCorreo());
		datos.setCorreoInstitucional(datosRecibidos.getCorreoInstitucional());
		datos.setCodCargo(datosRecibidos.getCodCargo());
		datos.setCodRango(datosRecibidos.getCodRango());
		datos.setCodGrado(datosRecibidos.getCodGrado());
		datos.setCodDocumentoImagen(datosRecibidos.getCodDocumentoImagen());
		datos.setCodCantonNacimiento(datosRecibidos.getCodCantonNacimiento());
		datos.setCodCantonResidencia(datosRecibidos.getCodCantonResidencia());
		datos.setFechaSalidaInstitucion(datosRecibidos.getFechaSalidaInstitucion());
		datos.setNivelInstruccion(datosRecibidos.getNivelInstruccion());
		datos.setNombreTituloTercerNivel(datosRecibidos.getNombreTituloTercerNivel());
		datos.setNombreTituloCuartoNivel(datosRecibidos.getNombreTituloCuartoNivel());
		datos.setEsVulnerable(datosRecibidos.getEsVulnerable());
		datos.setPaisTituloCuartoNivel(datosRecibidos.getPaisTituloCuartoNivel());
		datos.setPaisTituloTercerNivel(datosRecibidos.getPaisTituloTercerNivel());


		 */
		// asocia datos personales con usuario
		user.setCodDatosPersonales(datos);

		userRepository.save(user);

		userRepository.flush();

		return user;
	}

	@Override
	public Usuario actualizarUsuario(Usuario usuario) throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion,
			EmailExisteExcepcion, IOException, NoEsArchivoImagenExcepcion {
		Usuario currentUser = validateNewUsernameAndEmail(usuario.getNombreUsuario(), usuario.getNombreUsuario(),
				usuario.getCodDatosPersonales().getCorreoPersonal());

		currentUser.setActive(usuario.isActive());
		currentUser.setNotLocked(usuario.isNotLocked());

		// TODO: validar campos actualizables

		// currentUser.setNombres(newFirstName);
		// currentUser.setApellidos(newLastName);
//		currentUser.setNombreUsuario(usuario);
		// currentUser.setEmail(newEmail);
//		currentUser.setActive(isActive);
//		currentUser.setNotLocked(isNonLocked);
		userRepository.save(currentUser);
		// saveProfileImage(currentUser, profileImage);

		// TODO: eliminar log
		// LOGGER.info("Actualizar usuario ejecutado");

		return currentUser;
	}

	@Override
	public int actualizarActive(Boolean active, String username) throws UsuarioNoEncontradoExcepcion,
			NombreUsuarioExisteExcepcion, EmailExisteExcepcion, IOException, NoEsArchivoImagenExcepcion {
		return userRepository.actualizarIsActive(active, username);
	}

	@Override
	public int actualizarNotLock(Boolean notLock, String username) throws UsuarioNoEncontradoExcepcion,
			NombreUsuarioExisteExcepcion, EmailExisteExcepcion, IOException, NoEsArchivoImagenExcepcion {
		return userRepository.actualizarNotLocked(notLock, username);
	}

	@Override
	public void resetPassword(String nombreUsuario) throws MessagingException, UsuarioNoEncontradoExcepcion, IOException {

		Usuario usuario = userRepository.findUsuarioByNombreUsuario(nombreUsuario);
		if (usuario == null) {
			throw new UsuarioNoEncontradoExcepcion(NO_EXISTE_USUARIO + nombreUsuario);
		}

		String password = generatePassword();
		usuario.setClave(encodePassword(password));
		userRepository.save(usuario);

		// datos personales
		DatoPersonal datos = usuario.getCodDatosPersonales();

		// asocia datos personales con usuario
		usuario.setCodDatosPersonales(datos);

		userRepository.save(usuario);

		userRepository.flush();

		emailService.sendNewPasswordEmail(usuario.getCodDatosPersonales().getNombre(), password,
				usuario.getCodDatosPersonales().getCorreoPersonal());

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
	public List<Usuario> getUsuariosPageable(Pageable pageable) {

		return userRepository.findAllPageable(pageable);

	}

	@Override
	public List<UsuarioDtoRead> getUsuariosPer(Pageable pageable) {
		return userRepository.buscarUsuarioPersonalizado(pageable);
	}

	@Override
	public Usuario findUserByUsername(String username) {
		return userRepository.findUsuarioByNombreUsuario(username);
	}

	@Override
	public Usuario findUserByEmail(String email) {

		List<Usuario> lista = userRepository.findUsuariosByCorreo(email);

		if (lista != null && !lista.isEmpty()) {
			return lista.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void eliminarUsuario(String username) throws Exception {
		Usuario user = userRepository.findUsuarioByNombreUsuario(username);
		/*
		 * try { Path userFolder = Paths.get(CARPETA_USUARIO +
		 * user.getNombreUsuario()).toAbsolutePath().normalize();
		 * FileUtils.deleteDirectory(new File(userFolder.toString())); } catch
		 * (IOException e1) { LOGGER.
		 * error("Se ha producido un error al eliminar los archivos del usuario: " +
		 * user.getNombreUsuario()); e1.printStackTrace(); }
		 */

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
			// user.setUrlImagenPerfil(setProfileImageUrl(user.getNombreUsuario()));
			// userRepository.save(user);
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
		Usuario userByNewEmail = findUserByEmail(newEmail);

		// si valida un usuario registrado
		if (StringUtils.isNotBlank(currentUsername)) {
			Usuario currentUser = findUserByUsername(currentUsername);

			// si no encuentra datos para el usuario registrado
			if (currentUser == null) {
				throw new UsuarioNoEncontradoExcepcion(NO_EXISTE_USUARIO + currentUsername);
			}

			// sale si ya existe ese nombre de usuario para otro usuario registrado
			if (userByNewUsername != null && !currentUser.getCodUsuario().equals(userByNewUsername.getCodUsuario())) {
				throw new NombreUsuarioExisteExcepcion(NOMBRE_USUARIO_YA_EXISTE);
			}

			// sale si ya existe ese email para un usuario registrado
			if (currentUser.getCodDatosPersonales().getCorreoPersonal().compareToIgnoreCase(newEmail) != 0) {
				if (userByNewEmail != null /* && !currentUser.getCodUsuario().equals(userByNewEmail.getCodUsuario()) */) {
					throw new EmailExisteExcepcion(EMAIL_YA_EXISTE);
				}
			}

			return currentUser;
		}
		// cuando no es un usuario registrado
		else {
			// sale si ya existe ese nombre de usuario
			if (userByNewUsername != null) {
				throw new NombreUsuarioExisteExcepcion(NOMBRE_USUARIO_YA_EXISTE);
			}

			// sale si ya existe ese email para un usuario
			if (userByNewEmail != null) {
				throw new EmailExisteExcepcion(EMAIL_YA_EXISTE);
			}

			return null;
		}
	}

	public void guardarArchivo(String nombreArchivo, MultipartFile archivo)
			throws IOException, ArchivoMuyGrandeExcepcion {

		if (archivo.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
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
		return TAMAÑO_MÁXIMO.toBytes();
	}

	public List<Usuario> findUsuariosByNombreApellido(String nombre, String apellido) {
		return this.userRepository.findUsuariosByNombreApellido(nombre, apellido);
	}

	@Override
	public List<Usuario> findUsuariosByApellido(String apellido) {
		return this.userRepository.findUsuariosByApellido(apellido);
	}

	@Override
	public List<Usuario> findUsuariosByNombre(String nombre) {
		return this.userRepository.findUsuariosByNombre(nombre);
	}

	@Override
	public List<Usuario> findUsuariosByCorreo(String correo) {
		return this.userRepository.findUsuariosByCorreo(correo);
	}

	@Override
	public UsuarioInfoDto getUsuarioInfo(String codUser) throws DataException {
		Instructor instructor = instructorService.getInstructorByUser(codUser);
		Estudiante estudianteDto = estudianteService.getEstudianteByUsuario(codUser);

		if (instructor == null && estudianteDto == null) {
			throw new DataException(NO_ENCUENTRA);
		}
		UsuarioInfoDto usuarioInfo = new UsuarioInfoDto();
		if (instructor != null) {
			usuarioInfo.setCodInstructor(instructor.getCodInstructor());
		}
		if (estudianteDto != null) {
			usuarioInfo.setCodUnicoEstudiante(estudianteDto.getCodUnicoEstudiante());
		}

		return usuarioInfo;

	}

	@Override
	public Optional<Usuario> getUsuarioByCodDatoPersonal(Integer codDatoPersonal) {
		return userRepository.findByCodDatosPersonales_CodDatosPersonales(codDatoPersonal);
	}

}
