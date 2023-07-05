package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_CONVOCATORIA;
import static epntech.cbdmq.pe.constante.MensajesConst.EDAD_NO_CUMPLE;
import static epntech.cbdmq.pe.constante.MensajesConst.ERROR_REGISTRO;
import static epntech.cbdmq.pe.constante.MensajesConst.FECHA_INSCRIPCION_INVALIDA;
import static epntech.cbdmq.pe.constante.MensajesConst.PA_INACTIVO;
import static epntech.cbdmq.pe.constante.MensajesConst.PIN_INCORRECTO;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.constante.MensajesConst;
import epntech.cbdmq.pe.dominio.admin.Convocatoria;
import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.InscripcionFor;
import epntech.cbdmq.pe.dominio.admin.Postulante;
import epntech.cbdmq.pe.dominio.util.InscripcionResult;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaRepository;
import epntech.cbdmq.pe.repositorio.admin.InscripcionForRepository;
import epntech.cbdmq.pe.repositorio.admin.InscripcionRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.repositorio.admin.PostulanteRepository;
import epntech.cbdmq.pe.servicio.EmailService;
import epntech.cbdmq.pe.servicio.InscripcionForService;
import jakarta.mail.MessagingException;

@Service
public class InscripcionForServiceImpl implements InscripcionForService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private InscripcionForRepository repo;

	@Autowired
	private InscripcionRepository repo1;

	@Autowired
	private ConvocatoriaRepository convocatoriaRepository;

	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;

	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	@Autowired
	private EmailService emailService;

	@Autowired
	private PostulanteRepository repoPostulante;

	@Override
	public InscripcionResult insertarInscripcionConDocumentos(InscripcionFor inscripcion,
			List<MultipartFile> docsInscripcion)
			throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException, DataException {
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		String fechaFormateada = formato.format(fechaActual);

		SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");
		fechaActual = formatoDate.parse(fechaFormateada);

		LocalTime horaActual = LocalTime.now();

		if (!periodoAcademicoRepository.getActive())
			throw new DataException(PA_INACTIVO);

		Convocatoria convocatoria = convocatoriaRepository.getConvocatoriapaactivo();
		if (convocatoria == null) {
			throw new DataException(PA_INACTIVO);
		}

		Date FechaInicio = convocatoria.getFechaInicioConvocatoria(); // convocatoriaRepository.getConvocatoriapaactivo().getFechaInicioConvocatoria();
		Date FechaFin = convocatoria.getFechaFinConvocatoria(); // convocatoriaRepository.getConvocatoriapaactivo().getFechaFinConvocatoria();
		Date FechaActual = convocatoria.getFechaActual();
		// System.out.println("fechaActual "+fechaActual);

		/*
		 * if (!((fechaActual.after(FechaInicio) && fechaActual.before(FechaFin)) ||
		 * fechaActual.equals(FechaInicio) || fechaActual.equals(FechaFin))) throw new
		 * DataException(FECHA_INSCRIPCION_INVALIDA); else if
		 * (!(horaActual.isAfter(convocatoriaRepository.getConvocatoriapaactivo().
		 * getHoraInicioConvocatoria()) &&
		 * horaActual.isBefore(convocatoriaRepository.getConvocatoriapaactivo().
		 * getHoraFinConvocatoria()))) throw new
		 * DataException(HORA_INSCRIPCION_INVALIDA);
		 */

		if (!this.validarFechasInscripcion(FechaInicio, FechaFin, convocatoria)) {
			throw new DataException(FECHA_INSCRIPCION_INVALIDA);
		}

		if (this.findByCedula(inscripcion.getCedula(), convocatoria))
			throw new DataException(MensajesConst.CEDULA_YA_EXISTE);

		if (this.findByCorreoPersonal(inscripcion.getCorreoPersonal(), convocatoria))
			throw new DataException(MensajesConst.CORREO_YA_EXISTE);

		if (repo1.validaEdad(inscripcion.getFechaNacimiento().toLocalDate()).equals(false))
			throw new DataException(EDAD_NO_CUMPLE);
		else {
			String code = getRandomCode();
			inscripcion.setPinValidacionCorreo(code);

			inscripcion.setPinValidacionCorreo(BCrypt.hashpw(code, BCrypt.gensalt()));

			emailService.validateCodeEmail(inscripcion.getNombre(), code, inscripcion.getCorreoPersonal());

			return repo.insertarInscripcionConDocumentos(inscripcion, docsInscripcion);

		}

	}

	@Override
	public Optional<InscripcionFor> getInscripcionById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo1.findById(codigo);
	}

	public String savePostulante(Postulante obj, String proceso, String claveOriginal, String hashPassword,
			String correo) throws DataException, MessagingException {
		Postulante postulante = new Postulante();

		if (isPasswordMatches(claveOriginal, hashPassword).equals(true)) {
			obj.setIdPostulante(repoPostulante.getIdPostulante(proceso));
			obj.setEstado("PENDIENTE");
			postulante = repoPostulante.save(obj);

			if (postulante != null) {
				String mensaje = "Se ha registrado exitosamente. \n \n Su id de postulante es: " + obj.getIdPostulante()
						+ " \n \n Plataforma educativa - CBDMQ";

				emailService.enviarEmail(correo, EMAIL_SUBJECT_CONVOCATORIA, mensaje);
				return postulante.getIdPostulante();
			} else
				throw new DataException(ERROR_REGISTRO + " [id postulante]");
		} else
			throw new DataException(PIN_INCORRECTO);
	}

	public Boolean isPasswordMatches(String claveOriginal, String hashPassword) {
		return bcrypt.matches(claveOriginal, hashPassword);
	}

	static String getRandomCode() {
		String theAlphaNumericS;
		StringBuilder builder;
		int i = 6;

		theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

		// create the StringBuffer
		builder = new StringBuilder(i);

		for (int m = 0; m < i; m++) {

			// generate numeric
			int myindex = (int) (theAlphaNumericS.length() * Math.random());

			// add the characters
			builder.append(theAlphaNumericS.charAt(myindex));
		}

		System.out.println("codigo: " + builder.toString());
		return builder.toString();
	}

	public InscripcionFor savePin(InscripcionFor obj) throws DataException, MessagingException {
		String code = "";

		/*
		 * code = getRandomCode(); System.out.println("code: " + code);
		 * obj.setPin_validacion_correo(BCrypt.hashpw(code, BCrypt.gensalt()));
		 * emailService.validateCodeEmail(obj.getNombre(), code,
		 * obj.getCorreoPersonal()); return repo1.save(obj);
		 */

		return obj;
	}

	@Override
	public Optional<InscripcionFor> getById(int id) {
		// TODO Auto-generated method stub
		return repo1.findById(id);
	}

	@Override
	public Boolean validaEdad(LocalDate fecha) {
		// TODO Auto-generated method stub
		return repo1.validaEdad(fecha);
	}

	public InscripcionFor reenvioPin(InscripcionFor obj) throws DataException, MessagingException {
		String code = "";

		code = getRandomCode();

		if (this.findByCorreoPersonal(obj))
			throw new DataException(MensajesConst.CORREO_YA_EXISTE);

		obj.setCorreoPersonal(obj.getCorreoPersonal());
		obj.setPinValidacionCorreo(BCrypt.hashpw(code, BCrypt.gensalt()));
		emailService.validateCodeEmail(obj.getNombre(), code, obj.getCorreoPersonal());
		return repo1.save(obj);
	}

	@Override
	public Boolean validaFechas() throws ParseException, DataException {
		/*
		 * Date fechaActual = new Date(); SimpleDateFormat formato = new
		 * SimpleDateFormat("yyyy-MM-dd"); String fechaFormateada =
		 * formato.format(fechaActual);
		 * 
		 * SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");
		 * fechaActual = formatoDate.parse(fechaFormateada);
		 * 
		 * LocalTime horaActual = LocalTime.now(); Date FechaInicio =
		 * convocatoriaRepository.getConvocatoriapaactivo().getFechaInicioConvocatoria()
		 * ; Date FechaFin =
		 * convocatoriaRepository.getConvocatoriapaactivo().getFechaFinConvocatoria();
		 * 
		 * System.out.println("fechaActual " + fechaActual);
		 * 
		 * if (!periodoAcademicoRepository.getActive()) throw new
		 * DataException(PA_INACTIVO); if (!((fechaActual.after(FechaInicio) &&
		 * fechaActual.before(FechaFin)) || fechaActual.equals(FechaInicio) ||
		 * fechaActual.equals(FechaFin))) return false; else if
		 * (!(horaActual.isAfter(convocatoriaRepository.getConvocatoriapaactivo().
		 * getHoraInicioConvocatoria()) &&
		 * horaActual.isBefore(convocatoriaRepository.getConvocatoriapaactivo().
		 * getHoraFinConvocatoria()))) return false; return true;
		 */

		// corrección
		if (!periodoAcademicoRepository.getActive())
			throw new DataException(PA_INACTIVO);

		Convocatoria convocatoria = convocatoriaRepository.getConvocatoriapaactivo();
		Date FechaInicio = convocatoria.getFechaInicioConvocatoria();
		Date FechaFin = convocatoria.getFechaFinConvocatoria();
		Date FechaActual = convocatoria.getFechaActual();

		return this.validarFechasInscripcion(FechaInicio, FechaFin, convocatoria);

	}

	public Boolean findByCedula(String cedula, Convocatoria convocatoria) {

		Optional<InscripcionFor> inscripcion = null;

		inscripcion = this.repo1.findOneByCedula(cedula);

		if (inscripcion.isPresent()) {
			Optional<Postulante> postulante = this.repoPostulante
					.findByCodDatoPersonalAndCodPeriodoAcademico(inscripcion.get().getCodDatoPersonal(),
							convocatoria.getCodPeriodoAcademico());

			return postulante.isPresent();
		} else {
			return false;
		}
	}

	public Boolean findByCedula(String cedula) {

		Optional<InscripcionFor> inscripcion = null;

		inscripcion = this.repo1.findOneByCedula(cedula);

		if (inscripcion.isPresent()) {

			Convocatoria convocatoria = convocatoriaRepository.getConvocatoriapaactivo();

			Optional<Postulante> postulante = this.repoPostulante
					.findByCodDatoPersonalAndCodPeriodoAcademico(inscripcion.get().getCodDatoPersonal(),
							convocatoria.getCodPeriodoAcademico());

			return postulante.isPresent();
		} else {
			return false;
		}
	}

	public Boolean findByCorreoPersonal(String correo, Convocatoria convocatoria) {

		List<InscripcionFor> listaInscripcion = null;

		listaInscripcion = this.repo1.findAllByCorreoPersonalIgnoreCase(correo);

		if (!listaInscripcion.isEmpty()) {

			for (InscripcionFor inscripcionFor : listaInscripcion) {

				Optional<Postulante> postulante = this.repoPostulante
						.findByCodDatoPersonalAndCodPeriodoAcademico(inscripcionFor.getCodDatoPersonal(),
								convocatoria.getCodPeriodoAcademico());

				if (postulante.isPresent()) {
					return true;
				}
			}

			return false;

		} else {
			return false;
		}
	}

	public Boolean findByCorreoPersonal(String correo) {
		
		Convocatoria convocatoria = convocatoriaRepository.getConvocatoriapaactivo();
		
		return this.findByCorreoPersonal(correo, convocatoria);

		/*
		 * List<InscripcionFor> inscripcion = null;
		 * 
		 * inscripcion = this.repo1.findAllByCorreoPersonalIgnoreCase(correo);
		 * 
		 * if (!inscripcion.isEmpty()) {
		 * 
		 * 
		 * 
		 * Optional<Postulante> postulante = this.repoPostulante
		 * .findByCodDatoPersonalAndCodPeriodoAcademico(inscripcion.get(0).
		 * getCodDatoPersonal(), convocatoria.getCodPeriodoAcademico());
		 * 
		 * return postulante.isPresent(); } else { return false; }
		 */
	}

	public Boolean findByCorreoPersonal(InscripcionFor inscripcion) {

		Boolean retval = false;

		List<InscripcionFor> listaInscripcion = null;

		Convocatoria convocatoria = convocatoriaRepository.getConvocatoriapaactivo();

		// busca el correo ingresado y verifica si es distinto
		Optional<Postulante> postulanteOpt = this.repoPostulante
				.findByCodDatoPersonalAndCodPeriodoAcademico(inscripcion.getCodDatoPersonal(),
						convocatoria.getCodPeriodoAcademico());

		if (postulanteOpt.isPresent()) {

			// obtiene dato personal existente
			Optional<InscripcionFor> inscripcionExistenteOpt = this.repo1
					.findById(postulanteOpt.get().getCodDatoPersonal());

			if (inscripcionExistenteOpt.isPresent()) {
				// compara el correo registrado antes con el enviado para el reintento. Si es
				// igual, sale
				if (inscripcionExistenteOpt.get().getCorreoPersonal()
						.compareToIgnoreCase(inscripcion.getCorreoPersonal()) == 0) {
					retval = false;
				} else {

					listaInscripcion = this.repo1.findAllByCorreoPersonalIgnoreCase(inscripcion.getCorreoPersonal());

					if (!listaInscripcion.isEmpty()) {

						for (InscripcionFor inscripcionFor : listaInscripcion) {

							Optional<Postulante> postulante = this.repoPostulante
									.findByCodDatoPersonalAndCodPeriodoAcademico(inscripcionFor.getCodDatoPersonal(),
											convocatoria.getCodPeriodoAcademico());

							if (postulante.isPresent()) {
								retval = true;
							}
						}

					} else {
						retval = false;
					}
				}
			}
		}

		return retval;

	}

	private Boolean validarFechasInscripcion(Date fechaInicio, Date fechaFin, Convocatoria convocatoria) {
		Boolean retVal = false;

		// obtener parámetros
		LocalDate fechaInicioLD, fechaFinLD;
		LocalTime horaInicioLT, horaFinLT;

		horaInicioLT = convocatoria.getHoraInicioConvocatoria();
		horaFinLT = convocatoria.getHoraFinConvocatoria();

		fechaInicioLD = this.convertToLocalDateViaInstant(fechaInicio);
		fechaFinLD = this.convertToLocalDateViaInstant(fechaFin);

		// conformar fecha y hora de inicio y de fin
		LocalDateTime fechaHoraInicio = LocalDateTime.of(fechaInicioLD, horaInicioLT);
		LocalDateTime fechaHoraFin = LocalDateTime.of(fechaFinLD, horaFinLT);

		// fechaActual
		LocalDateTime fechaActual = LocalDateTime.now(ZoneId.of("-5"));

		LOGGER.info("fechaActual = " + fechaActual);
		LOGGER.info("fechaHoraInicio = " + fechaHoraInicio);
		LOGGER.info("fechaHoraFin = " + fechaHoraFin);

		if (fechaActual.isBefore(fechaHoraInicio) || fechaActual.isAfter(fechaHoraFin)) {
			retVal = false;
		} else {
			retVal = true;
		}

		return retVal;
	}

	public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}
}
