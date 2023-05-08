package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.MensajesConst.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.Usuario;
import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.UsuarioRepository;
import epntech.cbdmq.pe.repositorio.admin.DatoPersonalRepository;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.servicio.DatoPersonalService;
import epntech.cbdmq.pe.servicio.EmailService;
import jakarta.mail.MessagingException;

@Service
public class DatoPersonalServiceImpl implements DatoPersonalService {

	
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DatoPersonalRepository repo;
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private DocumentoRepository documentoRepository;
	
	@Autowired
	private UsuarioRepository  usuarioRepository;
	
	
	
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;

	@Value("${spring.servlet.multipart.max-file-size}")	
	public DataSize TAMAÑO_MÁXIMO;

	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

	@Override
	public DatoPersonal saveDatosPersonales(DatoPersonal obj) throws DataException, MessagingException {
		String code = "";
		
		if (obj.getNombre() == null || obj.getCedula() == null || obj.getNombre().isEmpty()
				|| obj.getCedula().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<DatoPersonal> objGuardado = repo.findOneByCedula(obj.getCedula());
		if (objGuardado.isPresent()) {
			throw new DataException(CEDULA_YA_EXISTE);
		}

		code = getRandomCode();
		obj.setValidacion_correo(BCrypt.hashpw(code, BCrypt.gensalt()));
		emailService.validateCodeEmail(obj.getNombre(), code, obj.getCorreo_personal());
		return repo.save(obj);
	}

	@Override
	public List<DatoPersonal> getAllDatosPersonales() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Page<DatoPersonal> getAllDatosPersonales(Pageable pageable) throws Exception {
		try {
			return repo.findAll(pageable);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	@Override
	public Optional<DatoPersonal> getDatosPersonalesById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	public Optional<DatoPersonal> getByCedula(String cedula) {
		// TODO Auto-generated method stub
		return repo.findOneByCedula(cedula);
	}

	@Override
	public DatoPersonal updateDatosPersonales(DatoPersonal objActualizado) throws DataException {

		return repo.save(objActualizado);
	}

	@Override
	public Page<DatoPersonal> search(String filtro, Pageable pageable) throws Exception {
		try {
			// Page<DatosPersonales> datos =
			// repo.findByNombreContainingOrApellidoContaining(filtro, filtro, pageable);
			// Page<Persona> datos = personaRepository.search(filtro, pageable);
			Page<DatoPersonal> datos = repo.searchNativo(filtro, pageable);
			return datos;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public void deleteById(int id) throws DataException {
		Optional<?> objGuardado = repo.findById(id);
		if (objGuardado.isEmpty()) {
			throw new DataException(REGISTRO_NO_EXISTE);
		}
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			if (e.getMessage().contains("constraint")) {
				throw new DataException(DATOS_RELACIONADOS);
			}
		}
	}

	public boolean isPasswordMatches(String claveOriginal, String hashPassword) {
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
	
	
	private String ruta(String proceso, Integer codigo) {
		
		String resultado = null;
		
		
			resultado=ARCHIVOS_RUTA+proceso+"/"+ codigo+"/";	


		return resultado;
	}
	
	@Override
	public Documento guardarImagen(String proceso, Integer codigo, MultipartFile archivo)
			throws IOException, ArchivoMuyGrandeExcepcion {
        String resultado;
		

		resultado= ruta(proceso, codigo);
		Path ruta = Paths.get(resultado).toAbsolutePath().normalize();
		
		if (!Files.exists(ruta)) {
			Files.createDirectories(ruta);
		}
		

        
        DocumentoRuta documentos = new DocumentoRuta();
		//Files.copy(archivo.getInputStream(), ruta.resolve(archivo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
		

			if (archivo.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}
			
			Files.copy(archivo.getInputStream(), ruta.resolve(archivo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			LOGGER.info("Archivo guardado: " + resultado + archivo.getOriginalFilename());

			Documento documento = new Documento();
            documento.setEstado("ACTIVO");
            documento.setNombre(archivo.getOriginalFilename());
			documento.setRuta(resultado+archivo.getOriginalFilename());
			documentoRepository.save(documento);
			
			Optional<Usuario> usuario = usuarioRepository.findById(Long.valueOf(codigo));
			
			Optional<DatoPersonal> datopersonal =repo.findById(usuario.get().getCodDatosPersonales().getCod_datos_personales()); 
			DatoPersonal dato=new DatoPersonal();
			dato =datopersonal.get();
			dato.setCod_documento_imagen(documento.getCodigo());
			repo.save(dato);
			
		return documento;
	}


}
