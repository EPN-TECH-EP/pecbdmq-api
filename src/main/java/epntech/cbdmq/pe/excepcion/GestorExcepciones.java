package epntech.cbdmq.pe.excepcion;

import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import epntech.cbdmq.pe.excepcion.dominio.BusinessException;
import jakarta.mail.AuthenticationFailedException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.auth0.jwt.exceptions.TokenExpiredException;

import epntech.cbdmq.pe.constante.ArchivoConst;
import epntech.cbdmq.pe.constante.MensajesConst;
import epntech.cbdmq.pe.dominio.HttpResponse;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.excepcion.dominio.EmailExisteExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.EmailNoEncontradoExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.NoEsArchivoImagenExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.NombreUsuarioExisteExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.UsuarioNoEncontradoExcepcion;
import jakarta.mail.MessagingException;
import jakarta.persistence.NoResultException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GestorExcepciones implements ErrorController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private static final String CUENTA_BLOQUEADA = "Cuenta bloqueada - Contacte al administrador";
	private static final String METODO_NO_PERMITIDO = "Este método no está permitido en el endpoint. Enviar un request: '%s'";
	public static final String ERROR_INTERNO_SERVIDOR = "Un error inesperado se ha producido al procesar el requerimiento";
	private static final String CREDENCIALES_INCORRECTAS = "Nombre de usuario / password incorrecto";
	private static final String CUENTA_DESHABILITADA = "Cuenta deshabilitada - Contacte al administrador";
	private static final String ERROR_PROCESO_ARCHIVO = "Error al procesar el archivo";
	private static final String PERMISOS_INSUFICIENTES = "Permisos insuficientes para esta acción";
	private static final String ERROR_ENVIO_EMAIL = "Error al enviar email, verifique que la dirección ingresada sea válida.";
	private static final String ERROR_ENVIO_EMAIL_CREDENCIALES = "Error al enviar email, verifique las credenciales de envío.";
	public static final String RUTA_ERROR = "/error";

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<HttpResponse> accountDisabledException() {
		return createHttpResponse(BAD_REQUEST, CUENTA_DESHABILITADA);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<HttpResponse> badCredentialsException() {
		return createHttpResponse(BAD_REQUEST, CREDENCIALES_INCORRECTAS);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<HttpResponse> accessDeniedException() {
		return createHttpResponse(FORBIDDEN, PERMISOS_INSUFICIENTES);
	}

	@ExceptionHandler(LockedException.class)
	public ResponseEntity<HttpResponse> lockedException() {
		return createHttpResponse(UNAUTHORIZED, CUENTA_BLOQUEADA);
	}

	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception) {
		return createHttpResponse(UNAUTHORIZED, exception.getMessage());
	}

	@ExceptionHandler(EmailExisteExcepcion.class)
	public ResponseEntity<HttpResponse> emailExistException(EmailExisteExcepcion exception) {
		return createHttpResponse(BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(NombreUsuarioExisteExcepcion.class)
	public ResponseEntity<HttpResponse> usernameExistException(NombreUsuarioExisteExcepcion exception) {
		return createHttpResponse(BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(EmailNoEncontradoExcepcion.class)
	public ResponseEntity<HttpResponse> emailNotFoundException(EmailNoEncontradoExcepcion exception) {
		return createHttpResponse(BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(UsuarioNoEncontradoExcepcion.class)
	public ResponseEntity<HttpResponse> userNotFoundException(UsuarioNoEncontradoExcepcion exception) {
		return createHttpResponse(BAD_REQUEST, exception.getMessage());
	}

//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<HttpResponse> noHandlerFoundException(NoHandlerFoundException e) {
//        return createHttpResponse(BAD_REQUEST, "There is no mapping for this URL");
//    }

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
		HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
		return createHttpResponse(METHOD_NOT_ALLOWED, String.format(METODO_NO_PERMITIDO, supportedMethod));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
		LOGGER.error(exception.getMessage());
		exception.printStackTrace();
		return createHttpResponse(INTERNAL_SERVER_ERROR, exception.getLocalizedMessage());
	}

	@ExceptionHandler(NoEsArchivoImagenExcepcion.class)
	public ResponseEntity<HttpResponse> notAnImageFileException(NoEsArchivoImagenExcepcion exception) {
		LOGGER.error(exception.getMessage());
		return createHttpResponse(BAD_REQUEST, exception.getMessage());
	}
	
	//DateTimeParseException
	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<HttpResponse> dateTimeParseException(DateTimeParseException exception) {
		LOGGER.error(exception.getMessage());
		return createHttpResponse(BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<HttpResponse> archivoMuyGrandeException(MaxUploadSizeExceededException exception) {
		LOGGER.error(exception.getMessage() + " - MaxUploadSize = " + exception.getMaxUploadSize());

		String mensaje = ArchivoConst.ARCHIVO_MUY_GRANDE;

		HttpHeaders headers = new HttpHeaders();
		headers.add("errorHeader", mensaje);

		ResponseEntity<HttpResponse> response = new ResponseEntity<HttpResponse>(
				new HttpResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
						mensaje.toUpperCase(),
						mensaje),
				headers, HttpStatus.BAD_REQUEST);

		return response;

		// return createHttpResponse(BAD_REQUEST, ARCHIVO_MUY_GRANDE);
	}

	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
		LOGGER.error(exception.getMessage());
		return createHttpResponse(NOT_FOUND, exception.getMessage());
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<HttpResponse> iOException(IOException exception) {
		LOGGER.error(exception.getMessage());
		return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESO_ARCHIVO);
	}

	private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
		return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
				httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
	}

	@RequestMapping(RUTA_ERROR)
	public ResponseEntity<HttpResponse> notFound404() {
		return createHttpResponse(NOT_FOUND, "No existe el recurso solicitado");
	}

//    @Override
//    public String getErrorPath() {
//        return ERROR_PATH;
//    }

	@ExceptionHandler(DataException.class)
	public ResponseEntity<HttpResponse> dataException(DataException exception) {
		return createHttpResponse(BAD_REQUEST, exception.getMessage());
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<HttpResponse> dataException(BusinessException exception) {
		return createHttpResponse(BAD_REQUEST, exception.getMessage());
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<HttpResponse> dataIntegrityViolationException(DataIntegrityViolationException exception) {
		
		Throwable cause = exception.getCause();
		
		String constraintName = ((ConstraintViolationException)exception.getCause()).getConstraintName();
		
		if (cause != null && cause instanceof ConstraintViolationException) {
			if (constraintName.contains("_un")) {
				return createHttpResponse(BAD_REQUEST, REGISTRO_YA_EXISTE);
			}
			
			if (constraintName.contains("_fk")) {
				return createHttpResponse(BAD_REQUEST, MensajesConst.DATOS_RELACIONADOS);
			}
		} 
			LOGGER.error(exception.getMessage());
			exception.printStackTrace();
			return createHttpResponse(INTERNAL_SERVER_ERROR, ERROR_INTERNO_SERVIDOR);	
	}
	
	@ExceptionHandler(MessagingException.class)
	public ResponseEntity<HttpResponse> messsagingException(MessagingException exception){
		LOGGER.error(exception.getMessage());
		return createHttpResponse(BAD_REQUEST, ERROR_ENVIO_EMAIL);
	}
	
	@ExceptionHandler(MailSendException.class)
	public ResponseEntity<HttpResponse> mailSendException(MailSendException exception){
		LOGGER.error(exception.getMessage());
		return createHttpResponse(BAD_REQUEST, ERROR_ENVIO_EMAIL);
	}

	@ExceptionHandler(MailAuthenticationException.class)
	public ResponseEntity<HttpResponse> mailAuthenticationException(MailAuthenticationException exception){
		LOGGER.error(exception.getMessage());
		return createHttpResponse(BAD_REQUEST, ERROR_ENVIO_EMAIL_CREDENCIALES);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<HttpResponse> messageNotReadableException(HttpMessageNotReadableException exception){
		LOGGER.error(exception.getMessage());
		return createHttpResponse(BAD_REQUEST, "Dato inválido - Revise el formato");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<HttpResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception){
		LOGGER.error(exception.getMessage());
		Map<String, String> errorsArgument = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach(error -> {
			String errorMessage = error.getDefaultMessage();
			String fieldName = ((FieldError) error).getField();
			errorsArgument.put(fieldName, errorMessage);
		});

		String str = errorsArgument.entrySet().stream()
				.sorted(Map.Entry.comparingByValue())
				.map(err -> "[Atributo '" + err.getKey() + "' con error: " + err.getValue() + "]")
				.collect(Collectors.joining(""));
		return createHttpResponse(BAD_REQUEST, str);
	}

	@ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
	public ResponseEntity<HttpResponse> constraintViolationException(jakarta.validation.ConstraintViolationException exception){
		LOGGER.error(exception.getMessage());
		ArrayList<String> errors = new ArrayList<>();
		exception.getConstraintViolations().forEach(error -> {
			String errorMessage = error.getMessage();
			errors.add(errorMessage);
		});

		String str = errors.stream()
				.map(err -> "[" + err + "]")
				.collect(Collectors.joining(""));
		return createHttpResponse(BAD_REQUEST, str);
	}
	
	/*
	 * catch (DataIntegrityViolationException cve) {
	 * 
	 * LOGGER.warn("No se puede eliminar el usuario: " + username +
	 * " - Usuario tiene dependencias en módulos."); cve.printStackTrace();
	 * 
	 * throw new DataException(UsuarioImplConst.USUARIO_TIENE_DEPENDENCIAS);
	 * 
	 * }
	 */
	
	/*
	 * @ExceptionHandler(DataIntegrityViolationException.class) public
	 * ResponseEntity<HttpResponse>
	 * dataIntegrityViolationException(DataIntegrityViolationException exception){
	 * LOGGER.error(exception.getMessage()); return createHttpResponse(BAD_REQUEST,
	 * "No se puede modificar/eliminar: existen dependencias en los módulos"); }
	 */
	
}

