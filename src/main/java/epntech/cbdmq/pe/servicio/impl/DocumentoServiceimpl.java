package epntech.cbdmq.pe.servicio.impl;

import static epntech.cbdmq.pe.constante.ArchivoConst.ARCHIVO_MUY_GRANDE;

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
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.servicio.DocumentoService;

@Service
public class DocumentoServiceimpl implements DocumentoService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DocumentoRepository repo;
	
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
	public Documento update(Documento objActualizado) {
		// TODO Auto-generated method stub
		return repo.save(objActualizado);
	}

	@Override
	public void delete(int id) {
		repo.deleteById(id);
	}

	private String ruta(String proceso, String id) {
		
		String resultado = null;
		
		
			resultado=ARCHIVOS_RUTA+proceso+"/"+ id+"/";	


		return resultado;
	}
	
	@Override
	public List<DocumentoRuta> guardarArchivo(String proceso, String id, List<MultipartFile> archivo)
			throws IOException, ArchivoMuyGrandeExcepcion {
        String resultado;
		

		resultado= ruta(proceso, id);
		Path ruta = Paths.get(resultado).toAbsolutePath().normalize();
		
		if (!Files.exists(ruta)) {
			Files.createDirectories(ruta);
		}
		
        List<DocumentoRuta> lista= new ArrayList<>();
        DocumentoRuta documentos = new DocumentoRuta();
		//Files.copy(archivo.getInputStream(), ruta.resolve(archivo.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
		for (Iterator iterator = archivo.iterator(); iterator.hasNext();) {
			MultipartFile multipartFile = (MultipartFile) iterator.next();
			if (multipartFile.getSize() > TAMAÑO_MÁXIMO.toBytes()) {
				throw new ArchivoMuyGrandeExcepcion(ARCHIVO_MUY_GRANDE);
			}
			
			Files.copy(multipartFile.getInputStream(), ruta.resolve(multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			LOGGER.info("Archivo guardado: " + resultado + multipartFile.getOriginalFilename());
			documentos.setRuta(resultado + multipartFile.getOriginalFilename());
			lista.add(documentos);
			
		}					
		
		return lista;
	}
	
}
