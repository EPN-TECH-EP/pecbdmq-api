package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface DocumentoService {

	Documento save(Documento obj);

	List<Documento> listAll();

	Optional<Documento> getById(int id);

	Documento update(Documento objActualizado, MultipartFile archivo) throws ArchivoMuyGrandeExcepcion, IOException;

	void delete(int id);

	List<DocumentoRuta> guardarArchivo(String proceso, String id, List<MultipartFile> archivo)
			throws IOException, ArchivoMuyGrandeExcepcion;

	void eliminarArchivo(int codDocumento) throws IOException, DataException;

	void eliminarArchivoConvocatoria(Integer codDocumento) throws IOException, DataException;
	
	public void guardarArchivoConvocatoria(List<MultipartFile> docsConvocatoria)
			throws IOException, DataException, ArchivoMuyGrandeExcepcion;

}
