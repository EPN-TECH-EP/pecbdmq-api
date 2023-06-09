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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.dominio.admin.Materia;
import epntech.cbdmq.pe.dominio.admin.PeriodoAcademico;
import epntech.cbdmq.pe.dominio.util.MateriaDocumento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.DocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.MateriaDocumentoRepository;
import epntech.cbdmq.pe.repositorio.admin.MateriaRepository;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoRepository;
import epntech.cbdmq.pe.servicio.MateriaDocumentoService;

@Service
public class MateriaDocumentoServiceImpl implements MateriaDocumentoService {

	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	@Value("${pecb.archivos.ruta}")
	private String ARCHIVOS_RUTA;

	@Value("${spring.servlet.multipart.max-file-size}")
	public DataSize TAMAÑO_MÁXIMO;
	@Autowired
	MateriaDocumentoRepository repo;

	@Autowired
	DocumentoRepository documentoRepository;

	@Autowired
	PeriodoAcademicoRepository periodoAcademicoRepository;
	
	@Autowired
	MateriaRepository repo3;

	@Override
	public MateriaDocumento save(MateriaDocumento obj) throws DataException {

		Materia materia = new Materia();
		MateriaDocumento matdoc = new MateriaDocumento();

		/*
		 * Documento documento = new Documento(); documento.setEstado("ACTIVO");
		 * documento.setNombre(archivo.getOriginalFilename());
		 * documento.setRuta(resultado+archivo.getOriginalFilename());
		 * documentoRepository.save(documento);
		 */

		return null;
	}

	private String ruta(String proceso, String codigo) {

		String resultado = null;
		PeriodoAcademico periodo=periodoAcademicoRepository.getPeriodoAcademicoActivo();

		resultado = ARCHIVOS_RUTA + proceso + "/Materia/" +periodo.getCodigo() +"/" + codigo + "/";

		return resultado;
	}

	@Override
	public List<DocumentoRuta> guardarArchivo(String proceso, Integer materia, List<MultipartFile> archivo)
			throws IOException, ArchivoMuyGrandeExcepcion {
		String resultado;

		resultado = ruta(proceso, materia.toString());
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
			
			Documento documento = new Documento();
			documento.setEstado("ACTIVO");
			documento.setNombre(multipartFile.getOriginalFilename());
			documento.setRuta(resultado + multipartFile.getOriginalFilename());
			documento = documentoRepository.save(documento);
			System.out.println("documento.getCodigo() " + documento.getCodigo());
			System.out.println("materia " + materia);
			
			MateriaDocumento matdoc = new MateriaDocumento(); 
			matdoc.setCodDocumento(documento.getCodigo());
			matdoc.setCodMateria(materia);
			repo.save(matdoc);

		}

		/*

		
		 * 
		 */
		return lista;
	}

}
