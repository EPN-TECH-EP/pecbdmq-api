package epntech.cbdmq.pe.servicio.fichaPersonal;

import epntech.cbdmq.pe.dominio.fichaPersonal.especializacion.CursoDocumentoEstudiante;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CursoDocumentoEstudianteService {
    List<CursoDocumentoEstudiante> getAll();
    List <CursoDocumentoEstudiante> getAllByEstudianteAndCurso(Integer codEstudiante, Integer codMateriaParalelo);
    Optional<CursoDocumentoEstudiante> getEstudianteCursoDocumentoById(Integer id);
    CursoDocumentoEstudiante saveOrUpdate(CursoDocumentoEstudiante estudianteMateriaDocumento);
    CursoDocumentoEstudiante saveConArchivo(CursoDocumentoEstudiante estudianteMateriaDocumento, List<MultipartFile> archivos) throws ArchivoMuyGrandeExcepcion, IOException;
    void deleteEstudianteMateriaDocumento(Integer id);
}
