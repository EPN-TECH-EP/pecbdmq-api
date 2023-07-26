package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.MensajesConst.CONVOCATORIA_NO_EXISTE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoDocRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.PerDocumentosForService;
import jakarta.mail.MessagingException;

@Service
public class PerDocumentosForServiceImpl implements PerDocumentosForService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private PeriodoAcademicoRepository periodoAcademicoRepository;

	@Autowired
	private PeriodoAcademicoDocRepository periodoAcademicoDocRepository;

	@Autowired
	private DocumentoRepository repo;

	@Autowired
	private PeriodoAcademicoDocRepository repo2;

	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;

	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;

	@Override
	public Set<Documento> getDocumentos() {
		return repo2.getDocumentos(periodoAcademicoRepository.getPAActive());
	}

	@Override
	public void eliminarArchivoFormacion(Integer codDocumento) throws IOException, DataException {

		Documento documento = new Documento();
		Optional<Documento> documentoOpt;

		// obtiene periodo académico vigente
		Integer peracademico = periodoAcademicoRepository.getPAActive();

		if (periodoAcademicoRepository.findById(peracademico).isEmpty())
			throw new IOException(CONVOCATORIA_NO_EXISTE);

		// busca documento y elimina de tablas
		documentoOpt = repo.findById(codDocumento);
		documento = documentoOpt.get();
		periodoAcademicoDocRepository.deleteByCodDocumento(peracademico, codDocumento);
		repo.deleteById(codDocumento);

		// elimina de FS
		Path ruta = null;
		try {
			ruta = Paths.get(documento.getRuta()).toAbsolutePath().normalize();
			Files.delete(ruta);
		} catch (Exception e) {
			LOGGER.error("No se puede eliminar el archivo: " + ruta + ". ERROR=" + e.getMessage());
		}
	}

	@Override
	public void actualizarArchivoFormacion(Integer codDocumento, MultipartFile archivo)
			throws IOException, DataException, ArchivoMuyGrandeExcepcion {

		if (archivo != null && archivo.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
			throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
		}

		Documento documento = new Documento();
		Optional<Documento> documentoOpt;

		// busca documento
		documentoOpt = repo.findById(codDocumento);
		documento = documentoOpt.get();

		// actualiza en FS y en tabla
		Path ruta = null;

		ruta = Paths.get(documento.getRuta()).toAbsolutePath().normalize();

		try {
			Files.delete(ruta);

			Files.copy(archivo.getInputStream(),
					ruta.getParent().resolve(archivo.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);

			documento.setNombre(archivo.getOriginalFilename());
			documento.setRuta(ruta.getParent() + "/" + archivo.getOriginalFilename());
			repo.save(documento);

		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

}
