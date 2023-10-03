package epntech.cbdmq.pe.servicio.especializacion;

import com.lowagie.text.DocumentException;
import epntech.cbdmq.pe.dominio.admin.Documento;
import epntech.cbdmq.pe.dominio.admin.especializacion.Curso;
import epntech.cbdmq.pe.dominio.admin.formacion.MateriaCursoDocumentoDto;
import epntech.cbdmq.pe.excepcion.dominio.ArchivoMuyGrandeExcepcion;
import epntech.cbdmq.pe.excepcion.dominio.DataException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CursoDocumentoService {
    Set<Documento> getDocumentos(Long codCurso) throws IOException;
    List<MateriaCursoDocumentoDto> getDocumentosDtoByCurso(Long codCurso) throws IOException;
    Documento updateDocumento(Long codDocumento, MultipartFile archivo) throws IOException;
    Curso uploadDocumentos(Long codCursoEspecializacion, List<MultipartFile> archivos, String descripcion, Boolean esTarea) throws IOException, ArchivoMuyGrandeExcepcion, DataException;
    void guardarDocumentos(List<MultipartFile> archivos, Long codCursoEspecializacion,String descripcion, Boolean esTarea)
            throws IOException, ArchivoMuyGrandeExcepcion, DataException;
    void deleteDocumento(Long codCursoEspecializacion, Long codDocumento) throws DataException, IOException;
    void generaDocumento(String ruta, String nombre, Long codCursoEspecializacion,String descripcion, Boolean esTarea)throws DataException ;
    Set<Documento> getDocumentosByCurso(Long codigoCurso);
    boolean generarDocumentoInscritos(HttpServletResponse response,Long codigoCurso);
    void generarExcel(String filePath, String nombre, Long codCurso, String estado) throws IOException, DataException;

    void generarPDF(HttpServletResponse response, String filePath, String nombre, Long codCurso, String estado)
            throws DocumentException, IOException, DataException;
    Boolean generarDocListadoGeneral(HttpServletResponse response,  Long codCurso, String estado);
    Boolean generarDocListadoInscripcion(HttpServletResponse response,Long codCurso);
    Boolean generarDocListadoValidacion(HttpServletResponse response,Long codCurso);
    Boolean generarDocListadoPruebas(HttpServletResponse response,Long codCurso);
    Set<Documento> getTareas(Long codCurso) throws IOException;

}
