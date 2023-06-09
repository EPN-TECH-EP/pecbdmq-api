package epntech.cbdmq.pe.servicio.impl.especializacion;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.ArchivoConst.PATH_PROCESO_ESPECIALIZACION;
import static epntech.cbdmq.pe.constante.MensajesConst.REGISTRO_NO_EXISTE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.especializacion.Cronograma;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.especializacion.CronogramaRepository;
import epntech.cbdmq.pe.servicio.especializacion.CronogramaService;

@Service
public class CronogramaServiceImpl implements CronogramaService {

	@Autowired
	private CronogramaRepository cronogramaRepository;
	@Autowired
	private DocumentoRepository documentoRepository;
	
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;
	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MAXIMO;

	@Override
	public Cronograma save(MultipartFile archivo, Long codTipoDocumento) throws IOException, ArchivoMuyGrandeExcepcion {
		LocalDate fechaInicio = LocalDate.of(2023, 1, 1);
		LocalDate fechaFin = LocalDate.of(2023, 12, 31);
		
		Cronograma cronograma = new Cronograma();
		cronograma.setEstado("ACTIVO");
		cronograma.setFechaInicio(fechaInicio);
		cronograma.setFechaFin(fechaFin);
		
		cronograma = cronogramaRepository.save(cronograma);
		
		Long codDcoumento = guardarDocumentos(archivo,cronograma.getCodCronograma(),codTipoDocumento);
		
		cronograma.setCodDocumento(codDcoumento);
		cronogramaRepository.save(cronograma);
		
		return cronograma;
	}

	@Override
	public Cronograma update(Long codCronograma, MultipartFile archivo, String estado)  throws IOException, ArchivoMuyGrandeExcepcion, DataException {
		Cronograma cronograma = new Cronograma();
		Optional<Cronograma> cronogramaOptional = cronogramaRepository.findById(codCronograma);
		
		if(cronogramaOptional.isEmpty())
			throw new DataException(REGISTRO_NO_EXISTE);

		cronograma = cronogramaOptional.get();
		Integer codDocumento = cronograma.getCodDocumento().intValue();
		//System.out.println("archivo.getSize(): " + archivo.getSize());
		
		if (archivo.getSize() >= 1) {
			Path ruta = Paths.get(documentoRepository.findById(codDocumento).get().getRuta());

			if (Files.exists(ruta)) {
				try {
					Files.delete(ruta);
				} catch (Exception e) {

					throw new DataException(e.getMessage());
					// e.printStackTrace();
				}
			}

			if (archivo.getSize() > TAMAÑO_MAXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}

			ruta = ruta.getParent();
			Files.copy(archivo.getInputStream(), ruta.resolve(archivo.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);

			// System.out.println("ruta: " + ruta);
			Documento documento = new Documento();
			documento = documentoRepository.findById(codDocumento).get();
			documento.setNombre(archivo.getOriginalFilename());
			documento.setRuta(ruta + "/" + archivo.getOriginalFilename());
			
			documento = documentoRepository.save(documento);
		}
		
		
		cronograma.setEstado(estado);
		cronograma = cronogramaRepository.save(cronograma);
				
		return cronograma;
	}

	@Override
	public Optional<Cronograma> getById(Long id) {
		// TODO Auto-generated method stub
		return cronogramaRepository.findById(id);
	}

	@Override
	public List<Cronograma> listAll() {
		// TODO Auto-generated method stub
		return cronogramaRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		cronogramaRepository.deleteById(id);

	}

	public Long guardarDocumentos(MultipartFile archivo, Long codCronograma, Long tipoDocumento)
			throws IOException, ArchivoMuyGrandeExcepcion {
		String resultado;

		resultado = ruta(codCronograma);
		Path ruta = Paths.get(resultado);

		if (!Files.exists(ruta)) {
			Files.createDirectories(ruta);
		}

		MultipartFile multipartFile = archivo;
		if (multipartFile.getSize() > TAMAÑO_MAXIMO.toBytes()) {
			throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
		}

		Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()),
				StandardCopyOption.REPLACE_EXISTING);
		// LOGGER.info("Archivo guardado: " + resultado +

		Documento documento = new Documento();
		documento.setEstado("ACTIVO");
		documento.setTipo(tipoDocumento.intValue());
		documento.setNombre(multipartFile.getOriginalFilename());
		documento.setRuta(resultado + multipartFile.getOriginalFilename());
		documento = documentoRepository.save(documento);
		
		return (long) documento.getCodigo();

	}

	private String ruta(Long codigo) {

		String resultado = null;
		resultado = ARCHIVOS_RUTA + PATH_PROCESO_ESPECIALIZACION + "Cronograma/" + codigo + "/";
		return resultado;
	}
	
}
