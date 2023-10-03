package epntech.cbdmq.pe.servicio;

import java.io.IOException;
import java.util.List;

import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.MateriaParalelo;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.formacion.MateriaDocumentoDto;
import org.springframework.web.multipart.MultipartFile;

import epntech.cbdmq.pe.dominio.admin.DocumentoRuta;
import epntech.cbdmq.pe.dominio.util.MateriaParaleloDocumento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;

import java.util.Optional;
import java.util.Set;


public interface MateriaParaleloDocumentoService {

    List<MateriaParaleloDocumento> getAll();

    List<MateriaDocumentoDto> getAllByCodMateriaParalelo(Integer codMateriaParalelo);

    Optional<MateriaParaleloDocumento> findById(Integer id);


    MateriaParaleloDocumento save(MateriaParaleloDocumento obj) throws DataException;

    List<DocumentoRuta> guardarArchivo(Integer materia, Boolean esTarea, List<MultipartFile> archivo) throws IOException, ArchivoMuyGrandeExcepcion;

    void deleteDocumento(Integer codMateriaParaleloDocumento) throws DataException;

    Set<Documento> getDocumentosByMateriaParalelo(Long codMateriaParalelo) throws IOException;
    Set<Documento> getTareasByMateriaParalelo(Long codMateriaParalelo) throws IOException;
    void deleteDocumentoI(Long codMateriaParalelo, Long codDocumento) throws DataException, IOException;



}
