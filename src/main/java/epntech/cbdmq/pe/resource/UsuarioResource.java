//TODO: revisar

package epntech.cbdmq.pe.resource;

import static epntech.cbdmq.pe.constante.ArchivoConst.CARPETA_USUARIO;
import static epntech.cbdmq.pe.constante.ArchivoConst.FORWARD_SLASH;
import static epntech.cbdmq.pe.constante.ArchivoConst.URL_IMAGEN_TEMPORAL;
import static epntech.cbdmq.pe.constante.SeguridadConst.CABECERA_TOKEN_JWT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.dominio.UserPrincipal;
import epntech.cbdmq.pe.dominio.Usuario;
import epntech.cbdmq.pe.excepcion.GestorExcepciones;
import epntech.cbdmq.pe.excepcion.dominio.EmailExisteExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.EmailNoEncontradoExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.NoEsArchivoImagenExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.NombreUsuarioExisteExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.UsuarioNoEncontradoExcepcion;
import epntech.cbdmq.pe.servicio.UsuarioService;
import epntech.cbdmq.pe.util.JWTTokenProvider;
import jakarta.mail.MessagingException;

@RestController
@RequestMapping(path = { "/", "/usuario"})
public class UsuarioResource extends GestorExcepciones {
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
    public static final String EMAIL_ENVIADO = "Se envió un email con el nuevo password a: ";
    public static final String USUARIO_ELIMINADO_EXITO = "Usuario eliminado";
    private AuthenticationManager authenticationManager;
    private UsuarioService usuarioService;
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UsuarioResource(AuthenticationManager authenticationManager, UsuarioService userService, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.usuarioService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario user) {
        authenticate(user.getNombreUsuario(), user.getClave());
        
        Usuario loginUser = usuarioService.findUserByUsername(user.getNombreUsuario());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> register(@RequestBody Usuario user) throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion, EmailExisteExcepcion, MessagingException {
        //Usuario newUser = usuarioService.registrar(user.getCodDatosPersonales().getNombre(), user.getCodDatosPersonales().getApellido(), user.getNombreUsuario(), user.getCodDatosPersonales().getCorreo_personal());
        Usuario newUser = usuarioService.registrar(user);
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<Usuario> addNewUser(@RequestParam("firstName") String firstName,
                                           @RequestParam("lastName") String lastName,
                                           @RequestParam("username") String username,
                                           @RequestParam("email") String email,
                                           @RequestParam("role") String role,
                                           @RequestParam("isActive") String isActive,
                                           @RequestParam("isNonLocked") String isNonLocked,
                                           @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion, EmailExisteExcepcion, IOException, NoEsArchivoImagenExcepcion {
        Usuario newUser = usuarioService.nuevoUsuario(firstName, lastName, username,email, role, Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/actualizar")
    public ResponseEntity<Usuario> update(@RequestParam("currentUsername") String currentUsername,
                                       @RequestParam("firstName") String firstName,
                                       @RequestParam("lastName") String lastName,
                                       @RequestParam("username") String username,
                                       @RequestParam("email") String email,
                                       @RequestParam("role") String role,
                                       @RequestParam("isActive") String isActive,
                                       @RequestParam("isNonLocked") String isNonLocked,
                                       @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion, EmailExisteExcepcion, IOException, NoEsArchivoImagenExcepcion {
        Usuario updatedUser = usuarioService.actualizarUsuario(currentUsername, firstName, lastName, username,email, role, Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive), profileImage);
        return new ResponseEntity<>(updatedUser, OK);
    }
    
    @GetMapping("/buscar/{nombreusuario}")
    public ResponseEntity<Usuario> getUser(@PathVariable("nombreusuario") String username) {
        Usuario user = usuarioService.findUserByUsername(username);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/lista")
    public ResponseEntity<List<Usuario>> getAllUsers() {
        List<Usuario> users = usuarioService.getUsuarios();
        return new ResponseEntity<>(users, OK);
    }

    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) throws MessagingException, EmailNoEncontradoExcepcion {
        usuarioService.resetPassword(email);
        return response(OK, EMAIL_ENVIADO + email);
    }

    @DeleteMapping("/eliminar/{username}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("username") String username) throws IOException {
        usuarioService.eliminarUsuario(username);
        return response(OK, USUARIO_ELIMINADO_EXITO);
    }

    @PostMapping("/actualizarImagenPerfil")
    public ResponseEntity<Usuario> updateProfileImage(@RequestParam("username") String username, @RequestParam(value = "profileImage") MultipartFile profileImage) throws UsuarioNoEncontradoExcepcion, NombreUsuarioExisteExcepcion, EmailExisteExcepcion, IOException, NoEsArchivoImagenExcepcion {
        Usuario user = usuarioService.actualizarImagenPerfil(username, profileImage);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping(path = "/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(CARPETA_USUARIO + username + FORWARD_SLASH + fileName));
    }

    @GetMapping(path = "/image/profile/{username}", produces = IMAGE_JPEG_VALUE)
    public byte[] getTempProfileImage(@PathVariable("username") String username) throws IOException {
        URL url = new URL(URL_IMAGEN_TEMPORAL + username);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (InputStream inputStream = url.openStream()) {
            int bytesRead;
            byte[] chunk = new byte[1024];
            while((bytesRead = inputStream.read(chunk)) > 0) {
                byteArrayOutputStream.write(chunk, 0, bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CABECERA_TOKEN_JWT, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}

