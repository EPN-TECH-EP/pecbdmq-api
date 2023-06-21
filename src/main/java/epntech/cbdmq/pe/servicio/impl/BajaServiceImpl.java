package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_BAJAS;
import static epntech.cbdmq.pe.constante.FormacionConst.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.Usuario;
import epntech.cbdmq.pe.dominio.admin.Baja;
import epntech.cbdmq.pe.dominio.admin.BajaDocumento;
import epntech.cbdmq.pe.dominio.admin.DatoPersonal;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.fichaPersonal.Estudiante;
import epntech.cbdmq.pe.dominio.admin.UsuarioDatoPersonal;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.UsuarioRepository;
import epntech.cbdmq.pe.repositorio.admin.BajaDocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.BajaRepository;
import epntech.cbdmq.pe.repositorio.admin.DatoPersonalRepository;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.fichaPersonal.EstudianteRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.repositorio.admin.UsuarioDatoPersonalRepository;
import epntech.cbdmq.pe.servicio.BajaService;

@Service
public class BajaServiceImpl implements BajaService {

	 @Autowired
	private BajaRepository repo;
	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;
	@Autowired
	private DocumentoRepository documentoRepository;
	@Autowired
	private BajaDocumentoRepository bajaDocumentoRepository;
	@Autowired
	private EstudianteRepository estudianteRepository;
	@Autowired
	private DatoPersonalRepository datoPersonalRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private UsuarioDatoPersonalRepository usuarioDatoPersonalRepository;
	
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	
	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;
	
	@Override
	public Baja save(Baja obj, @RequestParam List<MultipartFile> archivos) throws DataException, IOException, ArchivoMuyGrandeExcepcion {
		obj.setCodPeriodoAcademico(periodoAcademicoRepository.getPAActive());
		Baja baja = new Baja();
		baja = repo.save(obj);
		
		if (!archivos.isEmpty()) {
			guardarDocumentos(archivos, obj.getCodEstudiante(), baja.getCodBaja());
		}
		
		return baja;
	}

	@Override
	public List<Baja> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Baja> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public Baja update(Baja objActualizado, @RequestParam List<MultipartFile> archivos) throws DataException, IOException, ArchivoMuyGrandeExcepcion {
		if (!archivos.isEmpty()) {
			if(archivos.get(0).getSize() >= 1)
			{
				//System.out.println("archivos.isEmpty(): " + archivos.get(0).getSize());
				guardarDocumentos(archivos, objActualizado.getCodEstudiante(), objActualizado.getCodBaja());
			}
		}
		
			return repo.save(objActualizado);
		}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		repo.deleteById(codigo);
	}

	public void guardarDocumentos(List<MultipartFile> archivos, Integer codEstudiante, Integer codBaja)
			throws IOException, ArchivoMuyGrandeExcepcion {
		String resultado;

		resultado = ruta(codEstudiante);
		Path ruta = Paths.get(resultado).toAbsolutePath().normalize();

		if (!Files.exists(ruta)) {
			Files.createDirectories(ruta);
		}

		for (Iterator iterator = archivos.iterator(); iterator.hasNext();) {
			
			MultipartFile multipartFile = (MultipartFile) iterator.next();
			if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}

			Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			// LOGGER.info("Archivo guardado: " + resultado +
			
			Documento documento = new Documento();
			documento.setEstado("ACTIVO");
			documento.setNombre(multipartFile.getOriginalFilename());
			documento.setRuta(resultado + multipartFile.getOriginalFilename());
			documento = documentoRepository.save(documento);
			
			BajaDocumento bajaDocumento = new BajaDocumento();
			bajaDocumento.setCodBaja(codBaja);
			bajaDocumento.setDocumento(documento);
			bajaDocumentoRepository.save(bajaDocumento);
			
		}

	}
	
	private String ruta(Integer codEstudiante) {

		String resultado = null;
		resultado = ARCHIVOS_RUTA + PATH_BAJAS + codEstudiante + "/";
		return resultado;
	}

	@Override
	public Estudiante darDeBaja(Estudiante obj) throws DataException {
		Optional<Baja> baja;
		baja = repo.findByCodEstudianteAndCodPeriodoAcademicoAndEstado(obj.getCodEstudiante(), periodoAcademicoRepository.getPAActive(), "ACTIVO");
		
		if(baja.isPresent())
			obj.setEstado(ESTADO_BAJA);
		else
			throw new DataException(BAJA_EXISTE);
		Optional<DatoPersonal> datoPersonal = datoPersonalRepository.findById(obj.getCodDatosPersonales());
		DatoPersonal dp = new DatoPersonal();
		dp = datoPersonal.get();
		dp.setEstado(ESTADO_BAJA);
		
		UsuarioDatoPersonal usuarioDatoPersonal = usuarioDatoPersonalRepository.getByCodDatoPersonal(Long.valueOf(obj.getCodDatosPersonales()));
	
		Usuario usuario = usuarioRepository.findUsuarioByNombreUsuario(usuarioDatoPersonal.getNombreUsuario());
		Usuario u = new Usuario();
		u = usuario;

		u.setActive(false);
		u.setNotLocked(false);
		
		usuarioRepository.save(u);
		datoPersonalRepository.save(dp);
		return estudianteRepository.save(obj);
	}
}
