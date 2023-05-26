package epntech.cbdmq.pe.servicio.impl;


import static epntech.cbdmq.pe.constante.EmailConst.EMAIL_SUBJECT_CONVOCATORIA;
import static epntech.cbdmq.pe.constante.MensajesConst.CEDULA_YA_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.CORREO_YA_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.EDAD_NO_CUMPLE;
import static epntech.cbdmq.pe.constante.MensajesConst.ERROR_REGISTRO;
import static epntech.cbdmq.pe.constante.MensajesConst.FECHA_INSCRIPCION_INVALIDA;
import static epntech.cbdmq.pe.constante.MensajesConst.HORA_INSCRIPCION_INVALIDA;
import static epntech.cbdmq.pe.constante.MensajesConst.PA_INACTIVO;
import static epntech.cbdmq.pe.constante.MensajesConst.PIN_INCORRECTO;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
			List<MultipartFile> docsInscripcion) throws IOException, ArchivoMuyGrandeExcepcion, MessagingException, ParseException, DataException {
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = formato.format(fechaActual);
        
        SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");
        fechaActual=formatoDate.parse(fechaFormateada);

		LocalTime horaActual = LocalTime.now();
		Date FechaInicio=convocatoriaRepository.getConvocatoriapaactivo().getFechaInicioConvocatoria();
		Date FechaFin=convocatoriaRepository.getConvocatoriapaactivo().getFechaFinConvocatoria();
		
		System.out.println("fechaActual "+fechaActual);
		
		if(!periodoAcademicoRepository.getActive())
			throw new DataException(PA_INACTIVO);
		if(!((fechaActual.after(FechaInicio) && fechaActual.before(FechaFin)) || fechaActual.equals(FechaInicio) || fechaActual.equals(FechaFin)))
			throw new DataException(FECHA_INSCRIPCION_INVALIDA);
		else if(!(horaActual.isAfter(convocatoriaRepository.getConvocatoriapaactivo().getHoraInicioConvocatoria()) && horaActual.isBefore(convocatoriaRepository.getConvocatoriapaactivo().getHoraFinConvocatoria())))
			throw new DataException(HORA_INSCRIPCION_INVALIDA);
		if(repo1.findByCorreoPersonalIgnoreCase(inscripcion.getCorreoPersonal()).isPresent())
			throw new DataException(CORREO_YA_EXISTE);
		if(repo1.findOneByCedula(inscripcion.getCedula()).isPresent())
			throw new DataException(CEDULA_YA_EXISTE);
		if(repo1.validaEdad(inscripcion.getFecha_nacimiento()).equals(false))
			throw new DataException(EDAD_NO_CUMPLE);
		else
			return repo.insertarInscripcionConDocumentos(inscripcion, docsInscripcion);
	}

	@Override
	public Optional<InscripcionFor> getInscripcionById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo1.findById(codigo);
	}
	
	public String savePostulante(Postulante obj, String proceso, String claveOriginal, String hashPassword, String correo) throws DataException, MessagingException {
		Postulante postulante = new Postulante();
		
		if(isPasswordMatches(claveOriginal, hashPassword).equals(true)) {
			obj.setIdPostulante(repoPostulante.getIdPostulante(proceso));
			postulante = repoPostulante.save(obj);
			
			if(postulante != null) {
				String mensaje = "Se ha registrado exitosamente. \n \n Su id de postulante es: " + obj.getIdPostulante() + " \n \n Plataforma educativa - CBDMQ";
				
				emailService.enviarEmail(correo, EMAIL_SUBJECT_CONVOCATORIA, mensaje);
				return postulante.getIdPostulante();
			}
			else
				throw new DataException(ERROR_REGISTRO + " [id postulante]");
		}
		else 
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

		System.out.println("codigo: " +  builder.toString());
		return builder.toString();
	}
	
	public InscripcionFor savePin(InscripcionFor obj) throws DataException, MessagingException {
		String code = "";

		code = getRandomCode();
		System.out.println("code: " + code);
		obj.setPin_validacion_correo(BCrypt.hashpw(code, BCrypt.gensalt()));
		emailService.validateCodeEmail(obj.getNombre(), code, obj.getCorreoPersonal());
		return repo1.save(obj);
	}

	@Override
	public Optional<InscripcionFor> getById(int id) {
		// TODO Auto-generated method stub
		return repo1.findById(id);
	}

	@Override
	public Boolean validaEdad(LocalDateTime fecha) {
		// TODO Auto-generated method stub
		return repo1.validaEdad(fecha);
	}

	public InscripcionFor reenvioPin(InscripcionFor obj) throws DataException, MessagingException {
		String code = "";

		code = getRandomCode();
		System.out.println("code: " + code);
		obj.setCorreoPersonal(obj.getCorreoPersonal());
		obj.setPin_validacion_correo(BCrypt.hashpw(code, BCrypt.gensalt()));
		emailService.validateCodeEmail(obj.getNombre(), code, obj.getCorreoPersonal());
		return repo1.save(obj);
	}

	@Override
	public Boolean validaFechas() throws ParseException, DataException {
		Date fechaActual = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = formato.format(fechaActual);
        
        SimpleDateFormat formatoDate = new SimpleDateFormat("yyyy-MM-dd");
        fechaActual=formatoDate.parse(fechaFormateada);

		LocalTime horaActual = LocalTime.now();
		Date FechaInicio=convocatoriaRepository.getConvocatoriapaactivo().getFechaInicioConvocatoria();
		Date FechaFin=convocatoriaRepository.getConvocatoriapaactivo().getFechaFinConvocatoria();
		
		System.out.println("fechaActual "+fechaActual);
		
		if(!periodoAcademicoRepository.getActive())
			throw new DataException(PA_INACTIVO);
		if(!((fechaActual.after(FechaInicio) && fechaActual.before(FechaFin)) || fechaActual.equals(FechaInicio) || fechaActual.equals(FechaFin)))
			return false;
		else if(!(horaActual.isAfter(convocatoriaRepository.getConvocatoriapaactivo().getHoraInicioConvocatoria()) && horaActual.isBefore(convocatoriaRepository.getConvocatoriapaactivo().getHoraFinConvocatoria())))
			return false;
		return true;
	}
}
