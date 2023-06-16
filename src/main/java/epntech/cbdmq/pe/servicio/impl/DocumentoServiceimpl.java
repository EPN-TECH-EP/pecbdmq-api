package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.FORWARD_SLASH;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

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

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.ConvocatoriaDocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.servicio.DocumentoService;

@Service
public class DocumentoServiceimpl implements DocumentoService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private DocumentoRepository repo;

	@Autowired
	private ConvocatoriaDocumentoRepository convocatoriaDocumentoRepository;

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
			//System.out.println("ruta: " +  Paths.get(repo.findById(objActualizado.getCodigo()).get().getRuta()));
			Path ruta = Paths.get(repo.findById(objActualizado.getCodigo()).get().getRuta()).toAbsolutePath()
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

			System.out.println("parent " + ruta.getParent().toString());

			if (!Files.exists(ruta.getParent())) {
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
			lista.add(documentos);

		}

		return lista;
	}

	@Override
	public void eliminarArchivo(int id) throws IOException, ArchivoMuyGrandeExcepcion {
		// TODO Auto-generated method stub
		String resultado = null;
		Documento documentos = new Documento();
		Optional<Documento> documento;

		System.out.println("id: " + id);
		documento = repo.findById(id);
		documentos = documento.get();
		Path ruta = Paths.get(documentos.getRuta()).toAbsolutePath().normalize();

		System.out.println("ruta: " + ruta);
		if (Files.exists(ruta)) {
			try {
				System.out.println("ruta" + ruta);
				Files.delete(ruta);
			} catch (Exception e) {

				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
				// e.printStackTrace();
			}
		}
	}

	@Override
	public void eliminarArchivo(Integer convocatoria, Integer codDocumento)
			throws IOException {
		String resultado = null;
		Documento documentos = new Documento();
		Optional<Documento> documento;

		// System.out.println("id: " + codDocumento);
		documento = repo.findById(codDocumento);
		documentos = documento.get();
		Path ruta = Paths.get(documentos.getRuta()).toAbsolutePath().normalize();

		// System.out.println("ruta: " + ruta);
		if (Files.exists(ruta)) {
			try {
				System.out.println("ruta" + ruta);
				Files.delete(ruta);
				repo.deleteById(codDocumento);
				convocatoriaDocumentoRepository.deleteByCodConvocatoriaAndCodDocumento(convocatoria, codDocumento);
			} catch (Exception e) {

				throw new IOException(e.getMessage());
				// e.printStackTrace();
			}

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
