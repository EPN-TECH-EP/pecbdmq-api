package epntech.cbdmq.pe.servicio;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface DocumentoPruebaService {
    List<DocumentoRuta> guardarArchivo(Integer prueba, List<MultipartFile> archivo) throws IOException, ArchivoMuyGrandeExcepcion;

    void deleteDocumento(Integer codPrueba, Long codDocumento) throws DataException;
    MultipartFile getDocumento(Integer codPrueba, Long codDocumento) throws DataException, FileNotFoundException;
    public Set<Documento> getDocumentos(Integer codPrueba);
}
