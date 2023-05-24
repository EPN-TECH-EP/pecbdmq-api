package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.dominio.admin.MateriaPeriodo;
import epntech.cbdmq.pe.dominio.util.MateriaDocumento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

public interface MateriaDocumentoService {

	
	MateriaDocumento save(MateriaDocumento obj) throws DataException;

    /*List<MateriaDocumento> getAll();

    Optional<MateriaDocumento> getById(Integer codigo);

    MateriaDocumento update(MateriaDocumento objActualizado) throws DataException;

    void delete(Integer codigo);*/
    
    
    List<DocumentoRuta> guardarArchivo(String proceso,Integer materia, List<MultipartFile> archivo) throws IOException, ArchivoMuyGrandeExcepcion;
	
}
