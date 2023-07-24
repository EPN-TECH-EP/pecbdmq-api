package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_SANCIONES;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.Sanciones;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.SancionesRepository;
import epntech.cbdmq.pe.servicio.SancionesService;

@Service
public class SancionesServiceImpl implements SancionesService {

	@Autowired
	private SancionesRepository repo;
	@Autowired
	private DocumentoRepository documentoRepository;

	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;

	@Override
	public Sanciones save(Sanciones obj, MultipartFile archivo)
			throws DataException, IOException, ArchivoMuyGrandeExcepcion {

		if (!archivo.isEmpty()) {
			Path ruta = Paths.get(ruta(obj.getCodEstudiante())).toAbsolutePath().normalize();

			if (!Files.exists(ruta)) {
				Files.createDirectories(ruta);
			}

			obj.setCodDocumento(crearDocumento(archivo, ruta));
		}

		return repo.save(obj);
	}

	@Override
	public List<Sanciones> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Optional<Sanciones> getById(Integer codigo) {
		// TODO Auto-generated method stub
		return repo.findById(codigo);
	}

	@Override
	public void delete(Integer codigo) {
		// TODO Auto-generated method stub
		repo.deleteById(codigo);
	}

	@Override
	public List<Sanciones> findAllByCodEstudiante(Integer codEstudiante) {
		return repo.findAllByCodEstudiante(codEstudiante);
	}

	private String ruta(Integer codEstudiante) {

		String resultado = null;

		resultado = ARCHIVOS_RUTA + PATH_SANCIONES + codEstudiante + "/";

		return resultado;
	}

	private Integer crearDocumento(MultipartFile multipartFile, Path ruta)
			throws IOException, ArchivoMuyGrandeExcepcion {

		if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
			throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
		}

		Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()),
				StandardCopyOption.REPLACE_EXISTING);
		// LOGGER.info("Archivo guardado: " + resultado +
		// multipartFile.getOriginalFilename());
		// System.out.println("ruta: " + ruta);

		Documento documento = new Documento();
		documento.setEstado("ACTIVO");
		documento.setNombre(multipartFile.getOriginalFilename());
		documento.setRuta(ruta + "\\" + multipartFile.getOriginalFilename());
		documento = documentoRepository.save(documento);

		return documento.getCodDocumento();
	}

	@Override
	public Sanciones update(Sanciones objActualizado, MultipartFile archivo)
			throws DataException, ArchivoMuyGrandeExcepcion, IOException {
		if (!archivo.isEmpty()) {
			Path ruta = Paths.get(documentoRepository.findById(objActualizado.getCodDocumento()).get().getRuta()).toAbsolutePath()
						.normalize();

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

			Documento documento = new Documento();
			documento = documentoRepository.findById(objActualizado.getCodDocumento()).get();
			documento.setNombre(archivo.getOriginalFilename());
			documento.setRuta(ruta + "\\" + archivo.getOriginalFilename());
			documento = documentoRepository.save(documento);
		}

		return repo.save(objActualizado);
	}

}
