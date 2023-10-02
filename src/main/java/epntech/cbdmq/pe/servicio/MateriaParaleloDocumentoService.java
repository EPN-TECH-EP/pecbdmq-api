package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.dominio.util.MateriaParaleloDocumento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import java.util.Optional;


public interface MateriaParaleloDocumentoService {

    List<MateriaParaleloDocumento> getAll();
    Optional<MateriaParaleloDocumento> findById(Integer id);
    MateriaParaleloDocumento saveConArchivo(MateriaParaleloDocumento obj, List<MultipartFile> archivos) throws ArchivoMuyGrandeExcepcion, IOException;
	
	MateriaParaleloDocumento save(MateriaParaleloDocumento obj) throws DataException;
    
    List<DocumentoRuta> guardarArchivo(Integer materia,Boolean esTarea, List<MultipartFile> archivo) throws IOException, ArchivoMuyGrandeExcepcion;
	
    void deleteDocumento(Integer codMateriaParaleloDocumento) throws DataException;

}
