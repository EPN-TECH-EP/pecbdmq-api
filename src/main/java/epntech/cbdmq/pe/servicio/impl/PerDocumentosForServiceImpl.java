package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;
import static epntech.cbdmq.pe.constante.MensajesConst.NO_PERIODO_ACTIVO;
import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_NO_EXISTE;

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
		// TODO Auto-generated method stub
		return repo2.getDocumentos();
	}


	@Override
	public void eliminarArchivoFromacion(Integer codDocumento) throws IOException, DataException {
		// TODO Auto-generated method stub
		String resultado = null;
		Documento documentos = new Documento();
		Optional<Documento> documento;

		

		Integer peracademico = periodoAcademicoRepository.getPAActive();

		if (periodoAcademicoRepository.findById(peracademico).isEmpty())
			throw new IOException(NO_PERIODO_ACTIVO);
		

		 //System.out.println("id: " + codDocumento);
		
         

		
		//System.out.println("Estoy entrando");
		
		/*if(documento.isEmpty()) {
			throw new DataException(ARCHIVO_NO_EXISTE);
		}*/
		
	
			try {
				Path ruta = Paths.get(documentos.getRuta()).toAbsolutePath().normalize();
				if (Files.exists(ruta)) {
				documento = repo.findById(codDocumento);
				documentos = documento.get();
				//System.out.println("ruta" + ruta);
				Files.delete(ruta);
				
				System.out.println("Si entre");
				}else {
					LOGGER.error(ARCHIVO_NO_EXISTE +":"+ruta);
				}
				periodoAcademicoDocRepository.deleteByCodDocumento(peracademico, codDocumento);
				repo.deleteById(codDocumento);
				
			} catch (Exception e) {

				throw new DataException(ARCHIVO_NO_EXISTE);
				// e.printStackTrace();
				
				// e.printStackTrace();
			}
	}
		

	

	@Override
	public void actualizarArchivoFromacion(Integer codDocumento, MultipartFile archivo) throws IOException, DataException {
		// TODO Auto-generated method stub
		Optional<Documento> documento;
		Documento documentos = new Documento();

		documento = repo.findById(codDocumento);
		documentos = documento.get();
		Path ruta = Paths.get(documentos.getRuta()).toAbsolutePath().normalize();

		System.out.println("ruta: " + ruta);
		
			try {
				System.out.println("ruta" + ruta);
				if (Files.exists(ruta)) {
					
				Files.delete(ruta);
				}/*else {
			    LOGGER.error(ARCHIVO_NO_EXISTE +":"+ruta);
				}*/
				MultipartFile multipartFile = archivo;
				if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
					throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
				}

				Files.copy(multipartFile.getInputStream(), ruta.getParent().resolve(multipartFile.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
				
				documentos.setNombre(archivo.getOriginalFilename());
				System.out.println("nombre "+ archivo.getOriginalFilename());
				documentos.setRuta(ruta.getParent()+"/"+archivo.getOriginalFilename());
				System.out.println("rutanueva "+ruta.getParent()+"/"+archivo.getOriginalFilename());
				repo.save(documentos);

			} catch (Exception e) {

				throw new IOException(e.getMessage());
				// e.printStackTrace();
				// e.printStackTrace();
			}
		
	}

}
