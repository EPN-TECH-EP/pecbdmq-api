package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_PERIODO_ACADEMICO;
import static epntech.cbdmq.pe.constante.MensajesConst.DATOS_RELACIONADOS;
import static epntech.cbdmq.pe.constante.MensajesConst.FECHAS_YA_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.NO_PERIODO_ACTIVO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_VACIO;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_YA_EXISTE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.constante.EstadosConst;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.dominio.admin.PADocumento;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademicoSemestreModulo;
import epntech.cbdmq.pe.dominio.util.DocsUtil;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.PADocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoDocRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoMSRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.PeriodoAcademicoService;

@Service
public class PeriodoAcademicoServiceimpl implements PeriodoAcademicoService {

	@Autowired
	private PeriodoAcademicoRepository repo;
	@Autowired
	private PeriodoAcademicoMSRepository repo1;
	@Autowired
	private PeriodoAcademicoDocRepository repo2;
	@Autowired
	private PADocumentoRepository pADocumentoRepository;
	
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;
	@Autowired
	private DocumentoRepository documentoRepository;
	
	@Override
	public PeriodoAcademico save(PeriodoAcademico obj) throws DataException {
		if(obj.getDescripcion().trim().isEmpty())
			throw new DataException(REGISTRO_VACIO);
		Optional<PeriodoAcademico> objGuardado = repo.findByDescripcion(obj.getDescripcion());
		if (objGuardado.isPresent()) {
			throw new DataException(REGISTRO_YA_EXISTE);
		}
		Optional<PeriodoAcademico> objGuardado1 = repo.findByFechaInicioAndFechaFin(obj.getFechaInicio(), obj.getFechaFin());
		if (objGuardado1.isPresent()) {
			throw new DataException(FECHAS_YA_EXISTE);
		}
		return repo.save(obj);
	}

	@Override
	public List<PeriodoAcademico> getAll() {
		return repo.findAll();
	}
	
	
	public List<PeriodoAcademico> getAllActive() {
		return repo.findAllByEstado(EstadosConst.ACTIVO);
	}
	

	@Override
	public Optional<PeriodoAcademico> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public PeriodoAcademico update(PeriodoAcademico objActualizado) throws DataException {
		
		return repo.save(objActualizado);
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

	@Override
	public List<PeriodoAcademicoSemestreModulo> getAllPeriodoAcademico() {
		// TODO Auto-generated method stub
		return repo1.getPeriodoAcademico();
	}

	@Override
	public String getEstado() {
		// TODO Auto-generated method stub
		return repo.getEstado();
	}

	@Override
	public Integer updateNextState(Integer id, String proceso) {
		// TODO Auto-generated method stub
		return repo.updateNextState(id, proceso);
	}

	@Override
	public Integer validState(Integer id, String proceso) {
		// TODO Auto-generated method stub
		return repo.validState(id, proceso);
	}

	@Override
	public Set<Documento> getDocumentosPActive() {
		return repo2.getDocumentos(this.getPAActivo());
	}

	@Override
	public Optional<PeriodoAcademico> getActive() {
		// TODO Auto-generated method stub
		return repo.getPeriodoActivo();
	}

	@Override
	public Integer getPAActivo() {
		// TODO Auto-generated method stub
		return repo.getPAActive();
	}

	@Override
	public void cargarDocs(List<MultipartFile> archivos, String descripcion, String observacion) throws IOException, ArchivoMuyGrandeExcepcion, DataException {

		// TODO Auto-generated method stub
		String resultado;
		Integer periodo = repo.getPAActive();
		
		if(periodo == null)
			throw new DataException(NO_PERIODO_ACTIVO);

		resultado = ruta();
		Path ruta = Paths.get(resultado).toAbsolutePath().normalize();

		if (!Files.exists(ruta)) {
			Files.createDirectories(ruta);
		}

		List<DocumentoRuta> lista = new ArrayList<>();
		DocumentoRuta documentos = new DocumentoRuta();
		// Files.copy(archivo.getInputStream(),
		// ruta.resolve(archivo.getOriginalFilename()),
		// StandardCopyOption.REPLACE_EXISTING);
		for (Iterator iterator = archivos.iterator(); iterator.hasNext();) {
			MultipartFile multipartFile = (MultipartFile) iterator.next();
			if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}

			Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()),
					StandardCopyOption
					.REPLACE_EXISTING);
			//LOGGER.info("Archivo guardado: " + resultado + multipartFile.getOriginalFilename());
			documentos.setRuta(resultado + multipartFile.getOriginalFilename());
			lista.add(documentos);
			
			Documento documento = new Documento();
			documento.setEstado("ACTIVO");
			documento.setDescripcion(descripcion);
			documento.setObservaciones(observacion);
			documento.setNombre(multipartFile.getOriginalFilename());
			documento.setRuta(resultado + multipartFile.getOriginalFilename());
			documento = documentoRepository.save(documento);

			
			//System.out.println("documento.getCodigo(): " + documento.getCodigo());
			//System.out.println("periodo: " + periodo);
			PADocumento docsPA = new PADocumento(); 
			docsPA.setCodDocumento(documento.getCodDocumento());
			docsPA.setCodPeriodoAcademico(periodo); 
			pADocumentoRepository.save(docsPA);

		}
		
	}

	private String ruta() {

		String resultado = null;
		PeriodoAcademico periodo = repo.getPeriodoAcademicoActivo();

		resultado = ARCHIVOS_RUTA + PATH_PROCESO_PERIODO_ACADEMICO + periodo.getCodigo() + "/";

		return resultado;
	}

	@Override
	public void eliminar(List<DocsUtil> docs) {
		// TODO Auto-generated method stub
		for (DocsUtil docsUtil : docs) {
			documentoRepository.deleteById(docsUtil.getId());
			
			PADocumento docsPA = new PADocumento();
			docsPA = pADocumentoRepository.findByCodDocumentoAndCodPeriodoAcademico(docsUtil.getId(), repo.getPAActive());

			pADocumentoRepository.deleteById(docsPA.getCodPeriodoAcademicoDocumento());
		}
	}

	@Override
	@Transactional
	public Boolean cerrarPeriodoAcademico() throws ParseException {
		Date fechaActual = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String fechaFormateada = sdf.format(fechaActual);
		Date date = sdf.parse(fechaFormateada);
		int rowsUpdated=repo.cerrarPeriodoAndUpdateFecha(this.getPAActivo(),date);
		return rowsUpdated>0;
	}
	@Override
	public Set<Documento> getDocumentosByPeriodo(Integer codPA) {
		return repo2.getDocumentos(codPA);
	}

	@Override
	public List<PeriodoAcademico> getAllPeriodosFormacion() {
		return repo.getAllPeriodosFormacion();
	}

}
