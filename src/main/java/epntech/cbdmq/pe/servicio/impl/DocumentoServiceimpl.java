package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;

import static epntech.cbdmq.pe.constante.ArchivoConst.FORWARD_SLASH;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import static epntech.cbdmq.pe.constante.MensajesConst.CONVOCATORIA_NO_EXISTE;

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
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.constante.ArchivoConst;
import epntech.cbdmq.pe.dominio.admin.ConvocatoriaDocumentoForDoc;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaDocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaRepository;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.servicio.DocumentoService;

@Service
public class DocumentoServiceimpl implements DocumentoService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private DocumentoRepository repo;

	@Autowired
	private ConvocatoriaDocumentoRepository convocatoriaDocumentoRepository;

	@Autowired
	private ConvocatoriaRepository convocatoriaRepository;

	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;

	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;

	@Override
	public Documento save(Documento obj) {
		// TODO Auto-generated method stub

		return repo.save(obj);
	}

	@Override
	public List<Documento> listAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Documento> getById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id);
	}

	@Override
	public Documento update(Documento objActualizado, MultipartFile archivo)
			throws ArchivoMuyGrandeExcepcion, IOException {
		// TODO Auto-generated method stub

		if (!archivo.isEmpty()) {
			Path ruta = Paths.get(repo.findById(objActualizado.getCodDocumento()).get().getRuta()).toAbsolutePath()
					.normalize();
			// ruta =Path.of( );

			if (Files.exists(ruta)) {
				try {
					Files.delete(ruta);
				} catch (Exception e) {

					throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
					// e.printStackTrace();
				}
			}

			// Cargar documento
			System.out.println("ruta" + ruta);

			System.out.println("parent " + ruta.getParent().toString());

			if (!Files.exists(ruta.getParent())) {
				System.out.println("ruta" + ruta);
				Files.createDirectories(ruta.getParent());
			}

			List<DocumentoRuta> lista = new ArrayList<>();
			DocumentoRuta documentos = new DocumentoRuta();

			MultipartFile multipartFile = archivo;
			if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}

			Files.copy(multipartFile.getInputStream(), ruta.getParent().resolve(multipartFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			// LOGGER.info("Archivo guardado: " + resultado +
			// multipartFile.getOriginalFilename());
			// documentos.setRuta(ruta.getParent() + "\\" +
			// multipartFile.getOriginalFilename());
			// slista.add(documentos);
			String r = ruta.getParent() + "/" + multipartFile.getOriginalFilename();
			objActualizado.setRuta(r);
			objActualizado.setNombre(multipartFile.getOriginalFilename());
		}

		return repo.save(objActualizado);

	}

	@Override
	public void delete(int id) {
		repo.deleteById(id);
	}

	private String ruta(String proceso, String id) {

		String resultado = null;
		resultado = ARCHIVOS_RUTA + proceso + "/" + id + "/";

		return resultado;
	}

	@Override
	public List<DocumentoRuta> guardarArchivo(String proceso, String id, List<MultipartFile> archivo)
			throws IOException, ArchivoMuyGrandeExcepcion {
		String resultado;

		resultado = ruta(proceso, id);
		Path ruta = Paths.get(resultado).toAbsolutePath().normalize();
		System.out.println("ruta" + ruta);

		if (!Files.exists(ruta)) {
			Files.createDirectories(ruta);
		}

		List<DocumentoRuta> lista = new ArrayList<>();
		DocumentoRuta documentos = new DocumentoRuta();
		// Files.copy(archivo.getInputStream(),
		// ruta.resolve(archivo.getOriginalFilename()),
		// StandardCopyOption.REPLACE_EXISTING);

		for (Iterator iterator = archivo.iterator(); iterator.hasNext();) {
			MultipartFile multipartFile = (MultipartFile) iterator.next();
			if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}

			Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			LOGGER.info("Archivo guardado: " + resultado + multipartFile.getOriginalFilename());
			documentos.setRuta(resultado + multipartFile.getOriginalFilename());
			documentos.setNombre(multipartFile.getOriginalFilename());
			lista.add(documentos);

		}

		return lista;
	}

	@Override
	public void eliminarArchivo(int codDocumento) throws IOException {

		Documento documento = new Documento();
		Optional<Documento> documentoOpt;

		// busca documento y elimina de tablas
		documentoOpt = repo.findById(codDocumento);
		documento = documentoOpt.get();
		repo.deleteById(codDocumento);

		// elimina de FS
		Path ruta = null;

		try {
			ruta = Paths.get(documento.getRuta()).toAbsolutePath().normalize();
			Files.delete(ruta);
		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	@Override
	public void eliminarArchivoConvocatoria(Integer codDocumento)
			throws IOException, DataException {

		Documento documento = new Documento();
		Optional<Documento> documentoOpt;

		// obtiene convocatoria
		Integer convocatoria = convocatoriaRepository.getConvocatoriaActivaFormacion();

		if (convocatoriaRepository.findById(convocatoria).isEmpty())
			throw new IOException(CONVOCATORIA_NO_EXISTE);

		// busca documento y elimina de tablas
		
		try {
			documentoOpt = repo.findById(codDocumento);
			documento = documentoOpt.get();
			List<ConvocatoriaDocumentoForDoc> listaConvDoc = convocatoriaDocumentoRepository
					.findAllByCodConvocatoriaAndCodDocumento(convocatoria, codDocumento);
			if (listaConvDoc != null && !listaConvDoc.isEmpty()) {
				convocatoriaDocumentoRepository.deleteAllByCodConvocatoria(convocatoria);
			}
			repo.deleteById(codDocumento);
		} catch (Exception e) {
			LOGGER.error("No se puede eliminar le documento de las tablas: " + e.getMessage());
		}
		// elimina de FS
		Path ruta = null;

		try {
			ruta = Paths.get(documento.getRuta()).toAbsolutePath().normalize();
			Files.delete(ruta);
		} catch (Exception e) {
			//throw new IOException(e.getMessage());
			LOGGER.error("No se puede eliminar le documento en la ruta: " + ruta);
		}

	}
	
	@Override
	public void guardarArchivoConvocatoria(List<MultipartFile> docsConvocatoria)
			throws IOException, DataException, ArchivoMuyGrandeExcepcion {

		Documento documento = null;
		Optional<Documento> documentoOpt;

		// obtiene convocatoria
		Integer convocatoria = convocatoriaRepository.getConvocatoriaActivaFormacion();

		if (convocatoriaRepository.findById(convocatoria).isEmpty())
			throw new IOException(CONVOCATORIA_NO_EXISTE);

	
		// guarda los archivos en FS 
		List<DocumentoRuta> listaDocRuta = this.guardarArchivo(ArchivoConst.PATH_PROCESO_CONVOCATORIA, convocatoria.toString(), docsConvocatoria);
		
		
		// registra archivos en gen_documento
		
		List<Integer> listaIdsDocs = new ArrayList<Integer>();
		
		for (DocumentoRuta documentoRuta : listaDocRuta) {
			documento = new Documento();
			
			documento.setEstado("ACTIVO");
			documento.setRuta(documentoRuta.getRuta());
			documento.setNombre(documentoRuta.getNombre());
			
			Documento newDocumento = this.repo.save(documento);
			
			listaIdsDocs.add(newDocumento.getCodDocumento());			
		}
		
		// registra documentos en tabla intermedia
		ConvocatoriaDocumentoForDoc convocatoriaDocumento = null;
		
		for (Integer codDocumento : listaIdsDocs) {
			convocatoriaDocumento = new ConvocatoriaDocumentoForDoc();
			convocatoriaDocumento.setCodConvocatoria(convocatoria);
			convocatoriaDocumento.setCodDocumento(codDocumento);
			
			this.convocatoriaDocumentoRepository.save(convocatoriaDocumento);
		}
		
	}

	@Override
	public Documento updateDoc(Long codDocumento, MultipartFile archivo) throws ArchivoMuyGrandeExcepcion, IOException, DataException {
		
		Optional<Documento> doc = repo.findById(codDocumento.intValue());
		if(doc.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);
				
		if (archivo.getSize() >= 1) {
			Path ruta = Paths.get(repo.findById(codDocumento.intValue()).get().getRuta());

			if (Files.exists(ruta)) {
				try {
					Files.delete(ruta);
				} catch (Exception e) {

					throw new DataException(e.getMessage());
					// e.printStackTrace();
				}
			}

			if (archivo.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}

			ruta = ruta.getParent();
			Files.copy(archivo.getInputStream(), ruta.resolve(archivo.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);

			// System.out.println("ruta: " + ruta);
			Documento documento = new Documento();
			documento = repo.findById(codDocumento.intValue()).get();
			documento.setNombre(archivo.getOriginalFilename());
			documento.setRuta(ruta + "/" + archivo.getOriginalFilename());
			
			documento = repo.save(documento);
		}
		return repo.findById(codDocumento.intValue()).get();
	}
}
