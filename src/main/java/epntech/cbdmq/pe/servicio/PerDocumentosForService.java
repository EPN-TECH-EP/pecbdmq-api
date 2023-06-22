package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import epntech.cbdmq.pe.repositorio.admin.PeriodoAcademicoDocRepository;

public interface PerDocumentosForService {

	void eliminarArchivoFormacion( Integer codDocumento) throws IOException,DataException;
	
	void actualizarArchivoFormacion( Integer codDocumento,MultipartFile archivo) throws IOException,DataException, ArchivoMuyGrandeExcepcion;
	
	Set<Documento> getDocumentos();
	
}
