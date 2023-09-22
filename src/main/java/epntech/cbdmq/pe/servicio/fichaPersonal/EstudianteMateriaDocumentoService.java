package epntech.cbdmq.pe.servicio.fichaPersonal;

import epntech.cbdmq.pe.dominio.admin.formacion.EstudianteMateriaDocumento;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface EstudianteMateriaDocumentoService {
    List <EstudianteMateriaDocumento> getAll();
    List <EstudianteMateriaDocumento> getAllByEstudianteAndMateriaParalelo(Integer codEstudiante, Integer codMateriaParalelo);
    Optional<EstudianteMateriaDocumento> getEstudianteMateriaDocumentoById(Integer id);
    EstudianteMateriaDocumento saveOrUpdate(EstudianteMateriaDocumento estudianteMateriaDocumento);
    EstudianteMateriaDocumento saveConArchivo(EstudianteMateriaDocumento estudianteMateriaDocumento, List<MultipartFile> archivos) throws ArchivoMuyGrandeExcepcion, IOException;
    void deleteEstudianteMateriaDocumento(Integer id);

}
